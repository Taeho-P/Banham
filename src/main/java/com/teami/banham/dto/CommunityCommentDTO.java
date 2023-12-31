package com.teami.banham.dto;

import com.teami.banham.entity.CommunityCommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommunityCommentDTO {
    private Long cno; // 댓글 고유 번호
    private String commentContents; // 댓글 내용
    private String writer; // 작성자 닉네임
    private String memberId; // 작성자 아이디
    private String profile; // 작성자 프로필 이미지
    private int delete_ck; // 삭제 여부 0:삭제안함 1:삭제함
    private Long bno; // 게시글 고유 번호
    private LocalDateTime commentCreatedTime;
    private LocalDateTime commentUpdateTime;

    public static CommunityCommentDTO toCommentDTO(CommunityCommentEntity communityCommentEntity){
        CommunityCommentDTO commentDTO=new CommunityCommentDTO();
        commentDTO.setCno(communityCommentEntity.getCno());
        commentDTO.setCommentContents(communityCommentEntity.getCommentContents());
        commentDTO.setWriter(communityCommentEntity.getWriter());
        commentDTO.setMemberId(communityCommentEntity.getMemberId());
        commentDTO.setProfile(communityCommentEntity.getProfile());
        commentDTO.setDelete_ck(communityCommentEntity.getDelete_ck());
        commentDTO.setCommentCreatedTime(communityCommentEntity.getCreatedTime());
        commentDTO.setCommentUpdateTime(communityCommentEntity.getUpdatedTime());

        return commentDTO;
    }
}
