package com.teami.banham.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@Table(name = "notice_board_file")
@SequenceGenerator(
        name = "SEQ_NOTICE_FILE_GEN",
        sequenceName = "SEQ_NOTICE_FILE",
        initialValue = 1,
        allocationSize = 1
) // 시퀀스 생성
public class NoticeBoardFileEntity extends BoardBaseEntity{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_NOTICE_FILE_GEN"
    ) //시퀀스 값을 받아오는 설정
    private Long fno;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;



    //게시글 하나당 여러개의 파일이 올수있다 = BoardFileEntity : BoardEntity 는 '다수 : 1' 이기 때문에 @ManyToOne 어노테이션 사용
    // FetchType.LAZY 는 부모를 조회하면 필요에따라 자식까지 조회할수 있도록 지정하는것
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")  //board_file 테이블에 bno라는 이름의 컬럼이 생성되어 부모의 기본키를 이웃키로 받게됨
    private NoticeBoardEntity noticeBoardEntity;  //NoticeBoardEntity에 @OneToMany 어노테이션을 작성하면서 mappedBy 에 넣어줄 이름과 변수명이 같아야함


    //notice_board_file테이블에 넣기 위한 NoticeBoardFileEntity를 만들어주는 메소드
    public static NoticeBoardFileEntity toNoticeBoardFileEntity(NoticeBoardEntity noticeBoardEntity, String originalFileName, String storedFileName) {
        NoticeBoardFileEntity noticeBoardFileEntity = new NoticeBoardFileEntity();

        noticeBoardFileEntity.setOriginalFileName(originalFileName); // 기존 파일이름
        noticeBoardFileEntity.setStoredFileName(storedFileName); // 서버저장용 이름
        noticeBoardFileEntity.setNoticeBoardEntity(noticeBoardEntity); // bno값을 fk로 주기 위한 부모 엔티티를 통채로 넣어줌

        return noticeBoardFileEntity;
    }
}
