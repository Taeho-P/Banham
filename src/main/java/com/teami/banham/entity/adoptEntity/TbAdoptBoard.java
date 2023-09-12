package com.teami.banham.entity.adoptEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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

    private int aniType; //동물 종류

    @OneToMany(mappedBy = "tbAdoptBoard")
    private List<TbAdoptFile> files = new ArrayList<>();//파일첨부

    @Builder
    public TbAdoptBoard(String title, String content, long writer, int hits, int deleteYn, String memNick, List<TbAdoptFile> files, int aniType) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.hits = hits;
        this.deleteYn = deleteYn;
        this.memNick  = memNick;
        this.files = files;
        this.aniType =aniType;
    }


    public void update(String title, String content, String memNick, int aniType) {
        this.title = title;
        this.content = content;
        this.modifiedDate = LocalDateTime.now();
        this.memNick = memNick;
        this.aniType = aniType;
    }

    /**
     * 조회 수 증가
     */
    public void increaseHits() {
        this.hits++;
    }

    /**
     * 게시글 삭제
     */
    public void delete() {
        this.deleteYn = 1;
    }
}

