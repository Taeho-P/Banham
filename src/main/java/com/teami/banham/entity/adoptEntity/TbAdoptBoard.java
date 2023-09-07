package com.teami.banham.entity.adoptEntity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TbAdoptBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK

    private String title; // 제목

    private String content; // 내용

    private Long writer; // 작성자넘버

    private int hits; // 조회 수

    private int deleteYn; // 삭제 여부

    private LocalDateTime createdDate = LocalDateTime.now(); // 생성일

    private LocalDateTime modifiedDate; // 수정일

    private String memNick; //작성자닉네임

    @Builder
    public TbAdoptBoard(String title, String content, long writer, int hits, int deleteYn, String memNick) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
        this.memNick  = memNick;
    }


    public void update(String title, String content, String memNick) {
        this.title = title;
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
        this.memNick = memNick;
    }
}

