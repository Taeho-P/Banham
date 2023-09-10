package com.teami.banham.dto.adoptDTO;

import com.teami.banham.entity.adoptEntity.TbAdoptBoard;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AdoptResponseDto {


    private Long id; // PK
    private String title; // 제목
    private String content; // 내용
    private Long writer; // 작성자
    private int hits; // 조회 수
    private int deleteYn; // 삭제 여부
    private LocalDateTime createdDate; // 생성일
    private LocalDateTime modifiedDate; // 수정일
    private String memNick;

    public AdoptResponseDto(TbAdoptBoard entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.writer = entity.getWriter();
        this.hits = entity.getHits();
        this.deleteYn = entity.getDeleteYn();
        this.createdDate = entity.getCreatedDate();
        this.modifiedDate = entity.getModifiedDate();
        this.memNick = entity.getMemNick();
    }
}
