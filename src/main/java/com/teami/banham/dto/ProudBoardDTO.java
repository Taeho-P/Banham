package com.teami.banham.dto;

import com.teami.banham.entity.ProudBoardEntity;
import com.teami.banham.entity.ProudBoardFileEntity;
import com.teami.banham.entity.ProudCommentEntity;
import com.teami.banham.entity.ProudLikeEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProudBoardDTO {
    //게시판 관련 DTO
    private Long bno; // 게시글 고유 번호
    private String title; // 게시글 제목
    private String contents; // 게시글 내용
    private int hits; // 게시글 조회 횟수
    private String writer; //글쓴 사람 닉네임
    private String memberId; // 글쓴사람 ID
    private LocalDateTime createDate; //만든
    private LocalDateTime updateDate;
    private int delete_ck; // 게시글 삭제 여부 = 0 : 삭제 안함 , 1 : 삭제상태
    //파일첨부 관련 DTO
    private int hasFile; // 파일 있는지 여부 0 : 파일첨부 없음 , 1 : 파일첨부 있음
    private List<String> originalFileName; //원본 파일 이름
    private List<String> repositoryFileName; //서버 저장용 파일 이름
    private List<MultipartFile> fileList; //게시글에 첨부된 파일 목록
    private List<ProudCommentDTO> proudCommentDTOList;
    private List<ProudLikeDTO> proudLikeDTOList;

    public ProudBoardDTO(Long bno, String title, String contents, int hits, String writer, String memberId, LocalDateTime createDate, LocalDateTime updateDate, int hasFile, List<ProudBoardFileEntity> boardFileEntityList, List<ProudCommentEntity> proudCommentEntityList, List<ProudLikeEntity> proudLikeEntityList){
        this.bno=bno;
        this.title=title;
        this.contents=contents;
        this.hits=hits;
        this.writer=writer;
        this.memberId=memberId;
        this.createDate=createDate;
        this.updateDate=updateDate;
        this.hasFile=hasFile;
        if (hasFile== 0) {
            this.setHasFile(hasFile);
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> repositoryFileNameList = new ArrayList<>();
            this.setHasFile(hasFile);
            for (ProudBoardFileEntity FileEntity : boardFileEntityList) {
                originalFileNameList.add(FileEntity.getOriginalFileName());
                repositoryFileNameList.add(FileEntity.getRepositoryFileName());
            }
            this.setOriginalFileName(originalFileNameList);
            this.setRepositoryFileName(repositoryFileNameList);
        }

        if(proudCommentEntityList.isEmpty()){
            this.proudCommentDTOList=null;
        } else{
            List<ProudCommentDTO> dtoList = new ArrayList<>();
            for(int i = 0; i< proudCommentEntityList.size(); i++){
                dtoList.add(ProudCommentDTO.toCommentDTO(proudCommentEntityList.get(i)));
            }
            this.proudCommentDTOList=dtoList;
        }

        if(proudLikeEntityList.isEmpty()){
            this.proudLikeDTOList=null;
        } else {
            List<ProudLikeDTO> dtoList=new ArrayList<>();
            for(int i = 0;i<proudLikeEntityList.size();i++){
                dtoList.add(ProudLikeDTO.toproudLikeDTO(proudLikeEntityList.get(i)));
            }
            this.proudLikeDTOList=dtoList;
        }

    }

    public static ProudBoardDTO toBoardDTO(ProudBoardEntity proudBoardEntity){
        ProudBoardDTO proudBoardDTO = new ProudBoardDTO();
        proudBoardDTO.setBno(proudBoardEntity.getBno());
        proudBoardDTO.setTitle(proudBoardEntity.getTitle());
        proudBoardDTO.setContents(proudBoardEntity.getContents());
        proudBoardDTO.setHits(proudBoardEntity.getHits());
        proudBoardDTO.setWriter(proudBoardEntity.getWriter());
        proudBoardDTO.setMemberId(proudBoardEntity.getMemberId());
        proudBoardDTO.setCreateDate(proudBoardEntity.getCreatedTime());
        proudBoardDTO.setUpdateDate(proudBoardEntity.getUpdatedTime());

        if(proudBoardEntity.getHasFile()==0){
            proudBoardDTO.setHasFile(proudBoardEntity.getHasFile());
        } else {
            proudBoardDTO.setHasFile(proudBoardEntity.getHasFile());
            List<String> originalFileName=new ArrayList<>();
            List<String> repositoryFileName=new ArrayList<>();
            for(ProudBoardFileEntity proudBoardFileEntity : proudBoardEntity.getProudBoardFileEntityList()){
                originalFileName.add(proudBoardFileEntity.getOriginalFileName());
                repositoryFileName.add(proudBoardFileEntity.getRepositoryFileName());
            }
            proudBoardDTO.setOriginalFileName(originalFileName);
            proudBoardDTO.setRepositoryFileName(repositoryFileName);
        }

        if(!proudBoardEntity.getProudLikeEntityList().isEmpty()){
            List<ProudLikeDTO> proudLikeEntityList = new ArrayList<>();
            for(int i = 0;i<proudBoardEntity.getProudLikeEntityList().size();i++){
                proudLikeEntityList.add(ProudLikeDTO.toproudLikeDTO(proudBoardEntity.getProudLikeEntityList().get(i)));
            }
            proudBoardDTO.setProudLikeDTOList(proudLikeEntityList);
        }
        return proudBoardDTO;
    }
}
