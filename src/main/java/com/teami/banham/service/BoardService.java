package com.teami.banham.service;

import com.teami.banham.dto.ProudBoardDTO;
import com.teami.banham.dto.ProudCommentDTO;
import com.teami.banham.dto.ProudLikeDTO;
import com.teami.banham.entity.ProudBoardEntity;
import com.teami.banham.entity.ProudBoardFileEntity;
import com.teami.banham.entity.ProudCommentEntity;
import com.teami.banham.entity.ProudLikeEntity;
import com.teami.banham.repository.ProudBoardFileRepository;
import com.teami.banham.repository.ProudBoardRepository;
import com.teami.banham.repository.ProudCommentRepository;
import com.teami.banham.repository.ProudLikeRepository;
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
public class BoardService {

    private final ProudBoardRepository proudBoardRepository;
    private final ProudBoardFileRepository proudBoardFileRepository;
    private final ProudLikeRepository proudLikeRepository;
    private final ProudCommentRepository proudCommentRepository;

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
                String savePath = "C:/banham_files/" + repositoryFileName;
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
        int pageLimit = 15; // 한페이지에 보여줄 글 갯수
        // 한 페이지 당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        Page<ProudBoardEntity> proudBoardEntities =                                                   //Entity에 들어있는 값 기준
                proudBoardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bno")));
        Page<ProudBoardDTO> boardDTOS = proudBoardEntities.map(board -> new ProudBoardDTO(board.getBno(), board.getTitle(), board.getContents(), board.getHits(), board.getWriter(), board.getMemberId(), board.getCreatedTime(), board.getUpdatedTime(), board.getHasFile(), board.getProudBoardFileEntityList(),board.getProudCommentEntityList(),board.getProudLikeEntityList()));
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

    // 로그인 한 회원이 해당 게시글에 좋아요를 눌렀는지 안눌렀는지
    public boolean proudMemberIdisLiked(Long bno, String memberId){
        Optional<ProudLikeEntity> optionalProudLikeEntity = proudLikeRepository.findByMemberId(bno,memberId);
        if(optionalProudLikeEntity.isPresent()){
            return true;
        } else {
            return false;
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
                    String savePath = "C:/banham_files/" + repositoryFileName;
                    proudBoardFileList.transferTo(new File(savePath)); // 경로에 이름변경한 파일을 저장

                    ProudBoardFileEntity proudBoardFileEntity = ProudBoardFileEntity.toBoardFileEntity(proudBoard, originalFileName, repositoryFileName);
                    proudBoardFileRepository.save(proudBoardFileEntity);
                }
                return proudFindById(saveId);
            }
        } else { // 수정하려는 게시글에 기존 첨부파일이 있었을시
            proudBoardFileRepository.deleteProudBoardFileEntitiesByBno(proudBoardDTO.getBno());
            String path="C:/banham_files/";
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
                    String savePath = "C:/banham_files/" + repositoryFileName;
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
        proudCommentRepository.deleteByDelete_ck(bno);
    }

    public Page<ProudBoardDTO> proudSearch(Pageable pageable,String searchType,String searchKeyword){
        int page = pageable.getPageNumber() - 1; //spring JPA에서 page는 0부터 시작하기때문
        int pageLimit = 2; // 한페이지에 보여줄 글 갯수
        // 한 페이지 당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작
        if(searchType.equals("title")){ // 검색 키워드가 제목일경우
            Page<ProudBoardEntity> proudBoardEntities =                                                   //Entity에 들어있는 값 기준
                    proudBoardRepository.findAllByTitle(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bno")),searchKeyword);
            Page<ProudBoardDTO> boardDTOS = proudBoardEntities.map(board -> new ProudBoardDTO(board.getBno(), board.getTitle(), board.getContents(), board.getHits(), board.getWriter(), board.getMemberId(), board.getCreatedTime(), board.getUpdatedTime(), board.getHasFile(), board.getProudBoardFileEntityList(),board.getProudCommentEntityList(),board.getProudLikeEntityList()));
            return boardDTOS;
        } else if(searchType.equals("contents")){
            Page<ProudBoardEntity> proudBoardEntities =                                                   //Entity에 들어있는 값 기준
                    proudBoardRepository.findAllByContents(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bno")),searchKeyword);
            Page<ProudBoardDTO> boardDTOS = proudBoardEntities.map(board -> new ProudBoardDTO(board.getBno(), board.getTitle(), board.getContents(), board.getHits(), board.getWriter(), board.getMemberId(), board.getCreatedTime(), board.getUpdatedTime(), board.getHasFile(), board.getProudBoardFileEntityList(),board.getProudCommentEntityList(),board.getProudLikeEntityList()));
            return boardDTOS;
        } else if (searchType.equals("writer")){
            Page<ProudBoardEntity> proudBoardEntities =                                                   //Entity에 들어있는 값 기준
                    proudBoardRepository.findallbyWriter(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "bno")),searchKeyword);
            Page<ProudBoardDTO> boardDTOS = proudBoardEntities.map(board -> new ProudBoardDTO(board.getBno(), board.getTitle(), board.getContents(), board.getHits(), board.getWriter(), board.getMemberId(), board.getCreatedTime(), board.getUpdatedTime(), board.getHasFile(), board.getProudBoardFileEntityList(),board.getProudCommentEntityList(),board.getProudLikeEntityList()));
            return boardDTOS;
        } else {
            return null;
        }
    }

    @Transactional
    public Long proudLike(ProudLikeDTO proudLikeDTO){
        Optional<ProudBoardEntity> proudBoardEntity = proudBoardRepository.findById(proudLikeDTO.getBno());
        if(proudBoardEntity.isPresent()){
            Optional<ProudLikeEntity> optionalProudLikeEntity = proudLikeRepository.findByMemberId(proudLikeDTO.getBno(),proudLikeDTO.getMemberId());
            if(optionalProudLikeEntity.isPresent()){
                proudLikeRepository.deleteByLike(proudLikeDTO.getBno(),proudLikeDTO.getMemberId());
                Long result= proudLikeRepository.findAllCount(proudLikeDTO.getBno());
                return result;
            }else{
                ProudLikeEntity proudLikeEntity = ProudLikeEntity.toProudLikeEntity(proudLikeDTO,proudBoardEntity.get());
                proudLikeRepository.save(proudLikeEntity);
                Long result= proudLikeRepository.findAllCount(proudLikeDTO.getBno());
                return result;
            }
        } else{
            return null;
        }
    }

    @Transactional
    public List<ProudBoardDTO> proudFindAllList(String memberId) {
        List<ProudBoardEntity> proudBoardEntities =
                proudBoardRepository.findAllList(memberId);
        List<ProudBoardDTO> proudBoardDTOList = new ArrayList<>();
        for(ProudBoardEntity entits : proudBoardEntities){
            proudBoardDTOList.add(ProudBoardDTO.toBoardDTO(entits));
        }
        return proudBoardDTOList;
    }

    @Transactional
    public List<ProudBoardDTO> proudLikeFindMemberIdBoardList(String memberId){
        List<ProudLikeEntity> proudLikeEntities = proudLikeRepository.findAllMemberId(memberId);
        List<ProudBoardEntity> proudBoardEntities = new ArrayList<>();
        if(proudLikeEntities!=null) {
            for (int i = 0;i < proudLikeEntities.size();i++) {
                ProudLikeEntity proudLike = proudLikeEntities.get(i);
                ProudBoardEntity proudBoard=proudBoardRepository.findbyforLikeBno(proudLike.getProudBoardEntity().getBno());
                proudBoardEntities.add(proudBoard);
            }
            List<ProudBoardDTO> proudBoardDTOList = new ArrayList<>();
            for(ProudBoardEntity proudBoardEntity : proudBoardEntities){
                proudBoardDTOList.add(ProudBoardDTO.toBoardDTO(proudBoardEntity));
            }
            return proudBoardDTOList;
        }else{
            return null;
        }
    }

    @Transactional
    public List<ProudBoardDTO> proudBoardToIndex(){
        List<ProudBoardEntity> proudBoardEntities = proudBoardRepository.findTop9ProudBoardsByLikeCount();
        List<ProudBoardDTO> proudBoardDTOList = new ArrayList<>();
        if(proudBoardEntities!=null){
            for(ProudBoardEntity proudBoardEntity : proudBoardEntities){
                proudBoardDTOList.add(ProudBoardDTO.toBoardDTO(proudBoardEntity));
            }
            return proudBoardDTOList;
        }else {
            return null;
        }
    }
}
