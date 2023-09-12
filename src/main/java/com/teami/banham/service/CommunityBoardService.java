package com.teami.banham.service;

import com.teami.banham.dto.CommunityBoardDTO;
import com.teami.banham.dto.ProudBoardDTO;
import com.teami.banham.entity.CommunityBoardEntity;
import com.teami.banham.entity.CommunityBoardFileEntity;
import com.teami.banham.entity.ProudBoardEntity;
import com.teami.banham.repository.CommunityBoardFileRepository;
import com.teami.banham.repository.CommunityBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommunityBoardService {

    private final CommunityBoardRepository communityBoardRepository;
    private final CommunityBoardFileRepository communityBoardFileRepository;

    //자랑 게시판 게시글 작성시 저장 메소드
    public Long communitySave(CommunityBoardDTO communityBoardDTO) throws IOException {
        System.out.println("serviceDTO = " + communityBoardDTO);

        if (communityBoardDTO.getFileList().stream().anyMatch(MultipartFile::isEmpty)) {
            // 파일 첨부 없을시
            CommunityBoardEntity communityBoardEntity = CommunityBoardEntity.toSaveEntity(communityBoardDTO);
            communityBoardRepository.save(communityBoardEntity);
            return communityBoardRepository.save(communityBoardEntity).getBno();
        } else {
            // 파일 첨부 있을시
            CommunityBoardEntity communityBoardEntity = CommunityBoardEntity.toSaveFileEntity(communityBoardDTO);
            Long saveId = communityBoardRepository.save(communityBoardEntity).getBno();
            CommunityBoardEntity communityBoard = communityBoardRepository.findById(saveId).get();
            for (MultipartFile communityBoardFileList : communityBoardDTO.getFileList()) {
                String originalFileName = communityBoardFileList.getOriginalFilename();
                String repositoryFileName = System.currentTimeMillis() + "" + ((int) (Math.random() * 1000)) + "_" + originalFileName;
                String savePath = "C:/banham_files/" + repositoryFileName;
                communityBoardFileList.transferTo(new File(savePath)); // 경로에 이름변경한 파일을 저장

                CommunityBoardFileEntity communityBoardFileEntity = CommunityBoardFileEntity.toCommunityBoardFileEntity(communityBoard, originalFileName, repositoryFileName);
                communityBoardFileRepository.save(communityBoardFileEntity);
            }
            return saveId;
        }
    }

    @Transactional
    public Page<CommunityBoardDTO> communityFindAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //spring JPA에서 page는 0부터 시작하기때문
        int pageLimit = 2; // 한페이지에 보여줄 글 갯수
        // 한 페이지 당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<CommunityBoardEntity> communityBoardEntities =                                                   //Entity에 들어있는 값 기준
                communityBoardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bno")));
        System.out.println("communityBoard"+communityBoardEntities);
        Page<CommunityBoardDTO> boardDTOS = communityBoardEntities.map(board -> new CommunityBoardDTO(board.getBno(), board.getTitle(), board.getContents(), board.getHits(), board.getWriter(), board.getMemberId(), board.getCreatedTime(), board.getUpdatedTime(), board.getHasFile(), board.getCommunityBoardFileEntityList(),board.getCommunityCommentEntityList()));
        return boardDTOS;
    }

    @Transactional
    public void communityUpdateHits(Long bno) {
        communityBoardRepository.updateHits(bno);
    }

    //게시글 상세 조회
    @Transactional
    public CommunityBoardDTO communityFindById(Long bno) {
        Optional<CommunityBoardEntity> optionalCommunityBoardEntity = communityBoardRepository.findById(bno);
        if (optionalCommunityBoardEntity.isPresent()) {
            CommunityBoardEntity communityBoardEntity = optionalCommunityBoardEntity.get();
            CommunityBoardDTO communityBoardDTO = CommunityBoardDTO.toBoardDTO(communityBoardEntity);
            System.out.println("findById======>>>>>    " + communityBoardDTO);
            return communityBoardDTO;
        } else {
            return null;
        }
    }



    //게시글 수정
    @Transactional
    public CommunityBoardDTO communityUpdate(CommunityBoardDTO communityBoardDTO) throws IOException {
        System.out.println("service update DTO =====>  " + communityBoardDTO);
        CommunityBoardDTO board = communityFindById(communityBoardDTO.getBno());
        if (board.getHasFile()==0) { // 수정하려는 게시글에 기존 첨부파일이 없을시
            if (communityBoardDTO.getFileList().stream().anyMatch(MultipartFile::isEmpty)) {
                // 수정할때 파일 첨부 없을시
                CommunityBoardEntity communityBoardEntity = CommunityBoardEntity.toSaveEntity(communityBoardDTO);
                communityBoardRepository.save(communityBoardEntity);
                return communityFindById(communityBoardDTO.getBno());
            } else {
                // 수정할때 파일 첨부 있을시
                CommunityBoardEntity communityBoardEntity = CommunityBoardEntity.toSaveFileEntity(communityBoardDTO);
                Long saveId = communityBoardRepository.save(communityBoardEntity).getBno();
                CommunityBoardEntity communityBoard = communityBoardRepository.findById(saveId).get();
                for (MultipartFile communityBoardFileList : communityBoardDTO.getFileList()) {
                    String originalFileName = communityBoardFileList.getOriginalFilename();
                    String repositoryFileName = System.currentTimeMillis() + "" + ((int) (Math.random() * 1000)) + "_" + originalFileName;
                    String savePath = "C:/banham_img/" + repositoryFileName;
                    communityBoardFileList.transferTo(new File(savePath)); // 경로에 이름변경한 파일을 저장

                    CommunityBoardFileEntity communityBoardFileEntity = CommunityBoardFileEntity.toCommunityBoardFileEntity(communityBoard, originalFileName, repositoryFileName);
                    communityBoardFileRepository.save(communityBoardFileEntity);
                }
                return communityFindById(saveId);
            }
        } else { // 수정하려는 게시글에 기존 첨부파일이 있었을시
            communityBoardFileRepository.deleteCommunityBoardFileEntitiesByByBno(communityBoardDTO.getBno());
            String path="C:/banham_img/";
            if (communityBoardDTO.getFileList().stream().anyMatch(MultipartFile::isEmpty)) {
                // 수정할때 파일 첨부 없을시
                CommunityBoardEntity communityBoardEntity = CommunityBoardEntity.toSaveEntity(communityBoardDTO);
                communityBoardRepository.save(communityBoardEntity);
                return communityFindById(communityBoardDTO.getBno());
            } else {
                // 수정할때 파일 첨부 있을시
                CommunityBoardEntity communityBoardEntity = CommunityBoardEntity.toSaveFileEntity(communityBoardDTO);
                Long saveId = communityBoardRepository.save(communityBoardEntity).getBno();
                CommunityBoardEntity communityBoard = communityBoardRepository.findById(saveId).get();
                for (MultipartFile communityBoardFileList : communityBoardDTO.getFileList()) {
                    String originalFileName = communityBoardFileList.getOriginalFilename();
                    String repositoryFileName = System.currentTimeMillis() + "" + ((int) (Math.random() * 1000)) + "_" + originalFileName;
                    String savePath = "C:/banham_img/" + repositoryFileName;
                    communityBoardFileList.transferTo(new File(savePath)); // 경로에 이름변경한 파일을 저장

                    CommunityBoardFileEntity communityBoardFileEntity = CommunityBoardFileEntity.toCommunityBoardFileEntity(communityBoard, originalFileName, repositoryFileName);
                    communityBoardFileRepository.save(communityBoardFileEntity);
                }
                return communityFindById(saveId);
            }
        }
    }

    @Transactional
    public void communityDelete(Long bno){
        communityBoardRepository.deleteById(bno);
    }

    public List<CommunityBoardDTO> communityFindAllList(String memberId) {
        List<CommunityBoardEntity> communityBoardEntities =
                communityBoardRepository.findAllList(memberId);
        List<CommunityBoardDTO> communityBoardDTOList = new ArrayList<>();
        for(CommunityBoardEntity entits : communityBoardEntities){
            communityBoardDTOList.add(CommunityBoardDTO.toBoardDTO(entits));
        }

        return communityBoardDTOList;
    }


}
