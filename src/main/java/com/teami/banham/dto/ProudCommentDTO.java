package com.teami.banham.dto;

import com.teami.banham.entity.ProudCommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProudCommentDTO {
    private Long cno; // 댓글 고유 번호
    private String commentContents; // 댓글 내용
    private String writer; // 작성자 닉네임
    private String memberId; // 작성자 아이디
    private String profile; // 작성자 프로필 사진
    private int delete_ck; // 삭제 여부 0:삭제안함 1:삭제함
    private Long bno; // 게시글 고유 번호
    private LocalDateTime commentCreatedTime;
    private LocalDateTime commentUpdateTime;

    public static ProudCommentDTO toCommentDTO(ProudCommentEntity proudCommentEntity){
        ProudCommentDTO commentDTO=new ProudCommentDTO();
        commentDTO.setCno(proudCommentEntity.getCno());
        commentDTO.setCommentContents(proudCommentEntity.getCommentContents());
        commentDTO.setWriter(proudCommentEntity.getWriter());
        commentDTO.setMemberId(proudCommentEntity.getMemberId());
        commentDTO.setProfile(proudCommentEntity.getProfile());
        commentDTO.setDelete_ck(proudCommentEntity.getDelete_ck());
        commentDTO.setCommentCreatedTime(proudCommentEntity.getCreatedTime());
        commentDTO.setCommentUpdateTime(proudCommentEntity.getUpdatedTime());

        return commentDTO;

    }
}
