package com.teami.banham.dto;

import com.teami.banham.entity.CommunityBoardEntity;
import com.teami.banham.entity.CommunityBoardFileEntity;
import com.teami.banham.entity.CommunityCommentEntity;
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
public class CommunityBoardDTO {
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
    private List<CommunityCommentDTO> communityCommentDTOList;

    public CommunityBoardDTO(Long bno, String title, String contents, int hits, String writer, String memberId, LocalDateTime createDate, LocalDateTime updateDate, int hasFile, List<CommunityBoardFileEntity> boardFileEntityList, List<CommunityCommentEntity> communityCommentEntityList){
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
            for (CommunityBoardFileEntity FileEntity : boardFileEntityList) {
                originalFileNameList.add(FileEntity.getOriginalFileName());
                repositoryFileNameList.add(FileEntity.getRepositoryFileName());
            }
            this.setOriginalFileName(originalFileNameList);
            this.setRepositoryFileName(repositoryFileNameList);
        }

        if(communityCommentEntityList.isEmpty()){
            this.communityCommentDTOList =null;
        } else{
            List<CommunityCommentDTO> dtoList = new ArrayList<>();
            for(int i = 0; i< communityCommentEntityList.size(); i++){
                dtoList.add(CommunityCommentDTO.toCommentDTO(communityCommentEntityList.get(i)));
            }
            this.communityCommentDTOList =dtoList;
        }

    }

    public static CommunityBoardDTO toBoardDTO(CommunityBoardEntity communityBoardEntity){
        CommunityBoardDTO communityBoardDTO = new CommunityBoardDTO();
        communityBoardDTO.setBno(communityBoardEntity.getBno());
        communityBoardDTO.setTitle(communityBoardEntity.getTitle());
        communityBoardDTO.setContents(communityBoardEntity.getContents());
        communityBoardDTO.setHits(communityBoardEntity.getHits());
        communityBoardDTO.setWriter(communityBoardEntity.getWriter());
        communityBoardDTO.setMemberId(communityBoardEntity.getMemberId());
        communityBoardDTO.setCreateDate(communityBoardEntity.getCreateDate());
        communityBoardDTO.setUpdateDate(communityBoardEntity.getUpdateDate());

        if(communityBoardEntity.getHasFile()==0){
            communityBoardDTO.setHasFile(communityBoardEntity.getHasFile());
        } else {
            communityBoardDTO.setHasFile(communityBoardEntity.getHasFile());
            List<String> originalFileName=new ArrayList<>();
            List<String> repositoryFileName=new ArrayList<>();
            for(CommunityBoardFileEntity communityBoardFileEntity : communityBoardEntity.getCommunityBoardFileEntityList()){
                originalFileName.add(communityBoardFileEntity.getOriginalFileName());
                repositoryFileName.add(communityBoardFileEntity.getRepositoryFileName());
            }
            communityBoardDTO.setOriginalFileName(originalFileName);
            communityBoardDTO.setRepositoryFileName(repositoryFileName);
        }
        return communityBoardDTO;
    }
}
