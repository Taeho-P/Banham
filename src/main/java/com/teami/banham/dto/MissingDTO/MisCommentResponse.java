package com.teami.banham.dto.MissingDTO;


import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MisCommentResponse {
    private Long id;                       // 댓글 번호 (PK)
    private Long boardId;                   // 게시글 번호 (FK)
    private String content;                // 내용
    private String writer;                 // 작성자
    private Boolean deleteYn;              // 삭제 여부
    private LocalDateTime createdDate;     // 생성일시
    private LocalDateTime modifiedDate;    // 최종 수정일시
    private Long memberId;
    private String memberProfile;
}
