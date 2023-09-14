package com.teami.banham.dto.adoptDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdoptCommentRequest {
    private Long id;
    private Long boardId;
    private String content;
    private String writer;
    private Long memberId;
    private String memberProfile;
}
