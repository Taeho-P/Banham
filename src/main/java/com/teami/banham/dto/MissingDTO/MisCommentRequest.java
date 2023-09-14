package com.teami.banham.dto.MissingDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MisCommentRequest {
    private Long id;
    private Long boardId;
    private String content;
    private String writer;
    private Long memberId;
    private String memberProfile;
}
