package com.teami.banham.service;

import com.teami.banham.dto.ProudBoardDTO;
import com.teami.banham.entity.ProudBoardEntity;
import com.teami.banham.entity.ProudBoardFileEntity;
import com.teami.banham.repository.ProudBoardFileRepository;
import com.teami.banham.repository.ProudBoardRepository;
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
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final ProudBoardRepository proudBoardRepository;
    private final ProudBoardFileRepository proudBoardFileRepository;

    //자랑 게시판 게시글 작성시 저장 메소드
    public Long proudSave(ProudBoardDTO proudBoardDTO) throws IOException {
        System.out.println("serviceDTO = " + proudBoardDTO);

        if (proudBoardDTO.getFileList().stream().anyMatch(MultipartFile::isEmpty)) {
            // 파일 첨부 없을시
            ProudBoardEntity proudBoardEntity = ProudBoardEntity.toSaveEntity(proudBoardDTO);
            proudBoardRepository.save(proudBoardEntity);
            return proudBoardRepository.save(proudBoardEntity).getBno();
        } else {
            // 파일 첨부 있을시
            ProudBoardEntity proudBoardEntity = ProudBoardEntity.toSaveFileEntity(proudBoardDTO);
            Long saveId = proudBoardRepository.save(proudBoardEntity).getBno();
            ProudBoardEntity proudBoard = proudBoardRepository.findById(saveId).get();
            for (MultipartFile proudBoardFileList : proudBoardDTO.getFileList()) {
                String originalFileName = proudBoardFileList.getOriginalFilename();
                String repositoryFileName = System.currentTimeMillis() + "" + ((int) (Math.random() * 1000)) + "_" + originalFileName;
                String savePath = "C:/banham_img/" + repositoryFileName;
                proudBoardFileList.transferTo(new File(savePath)); // 경로에 이름변경한 파일을 저장

                ProudBoardFileEntity proudBoardFileEntity = ProudBoardFileEntity.toBoardFileEntity(proudBoard, originalFileName, repositoryFileName);
                proudBoardFileRepository.save(proudBoardFileEntity);
            }
            return saveId;
        }
    }

    @Transactional
    public Page<ProudBoardDTO> proudFindAll(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //spring JPA에서 page는 0부터 시작하기때문
        int pageLimit = 2; // 한페이지에 보여줄 글 갯수
        // 한 페이지 당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<ProudBoardEntity> proudBoardEntities =                                                   //Entity에 들어있는 값 기준
                proudBoardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bno")));
        Page<ProudBoardDTO> boardDTOS = proudBoardEntities.map(board -> new ProudBoardDTO(board.getBno(), board.getTitle(), board.getContents(), board.getHits(), board.getWriter(), board.getMemberId(), board.getCreateDate(), board.getUpdateDate(), board.getHasFile(), board.getProudBoardFileEntityList(),board.getProudCommentEntityList()));
        return boardDTOS;
    }

    @Transactional
    public void proudUpdateHits(Long bno) {
        proudBoardRepository.updateHits(bno);
    }

    //게시글 상세 조회
    @Transactional
    public ProudBoardDTO proudFindById(Long bno) {
        Optional<ProudBoardEntity> optionalProudBoardEntity = proudBoardRepository.findById(bno);
        if (optionalProudBoardEntity.isPresent()) {
            ProudBoardEntity proudBoardEntity = optionalProudBoardEntity.get();
            ProudBoardDTO proudBoardDTO = ProudBoardDTO.toBoardDTO(proudBoardEntity);
            System.out.println("findById======>>>>>    " + proudBoardDTO);
            return proudBoardDTO;
        } else {
            return null;
        }
    }


    //게시글 수정
    @Transactional
    public ProudBoardDTO proudUpdate(ProudBoardDTO proudBoardDTO) throws IOException {
        System.out.println("service update DTO =====>  " + proudBoardDTO);
        ProudBoardDTO board = proudFindById(proudBoardDTO.getBno());
        if (board.getHasFile()==0) { // 수정하려는 게시글에 기존 첨부파일이 없을시
            if (proudBoardDTO.getFileList().stream().anyMatch(MultipartFile::isEmpty)) {
                // 수정할때 파일 첨부 없을시
                ProudBoardEntity proudBoardEntity = ProudBoardEntity.toSaveEntity(proudBoardDTO);
                proudBoardRepository.save(proudBoardEntity);
                return proudFindById(proudBoardDTO.getBno());
            } else {
                // 수정할때 파일 첨부 있을시
                ProudBoardEntity proudBoardEntity = ProudBoardEntity.toSaveFileEntity(proudBoardDTO);
                Long saveId = proudBoardRepository.save(proudBoardEntity).getBno();
                ProudBoardEntity proudBoard = proudBoardRepository.findById(saveId).get();
                for (MultipartFile proudBoardFileList : proudBoardDTO.getFileList()) {
                    String originalFileName = proudBoardFileList.getOriginalFilename();
                    String repositoryFileName = System.currentTimeMillis() + "" + ((int) (Math.random() * 1000)) + "_" + originalFileName;
                    String savePath = "C:/banham_img/" + repositoryFileName;
                    proudBoardFileList.transferTo(new File(savePath)); // 경로에 이름변경한 파일을 저장

                    ProudBoardFileEntity proudBoardFileEntity = ProudBoardFileEntity.toBoardFileEntity(proudBoard, originalFileName, repositoryFileName);
                    proudBoardFileRepository.save(proudBoardFileEntity);
                }
                return proudFindById(saveId);
            }
        } else { // 수정하려는 게시글에 기존 첨부파일이 있었을시
            proudBoardFileRepository.deleteProudBoardFileEntitiesByBno(proudBoardDTO.getBno());
            String path="C:/banham_img/";
            if (proudBoardDTO.getFileList().stream().anyMatch(MultipartFile::isEmpty)) {
                // 수정할때 파일 첨부 없을시
                ProudBoardEntity proudBoardEntity = ProudBoardEntity.toSaveEntity(proudBoardDTO);
                proudBoardRepository.save(proudBoardEntity);
                return proudFindById(proudBoardDTO.getBno());
            } else {
                // 수정할때 파일 첨부 있을시
                ProudBoardEntity proudBoardEntity = ProudBoardEntity.toSaveFileEntity(proudBoardDTO);
                Long saveId = proudBoardRepository.save(proudBoardEntity).getBno();
                ProudBoardEntity proudBoard = proudBoardRepository.findById(saveId).get();
                for (MultipartFile proudBoardFileList : proudBoardDTO.getFileList()) {
                    String originalFileName = proudBoardFileList.getOriginalFilename();
                    String repositoryFileName = System.currentTimeMillis() + "" + ((int) (Math.random() * 1000)) + "_" + originalFileName;
                    String savePath = "C:/banham_img/" + repositoryFileName;
                    proudBoardFileList.transferTo(new File(savePath)); // 경로에 이름변경한 파일을 저장

                    ProudBoardFileEntity proudBoardFileEntity = ProudBoardFileEntity.toBoardFileEntity(proudBoard, originalFileName, repositoryFileName);
                    proudBoardFileRepository.save(proudBoardFileEntity);
                }
                return proudFindById(saveId);
            }
        }
    }

    @Transactional
    public void proudDelete(Long bno){
        proudBoardRepository.deleteById(bno);
    }
}