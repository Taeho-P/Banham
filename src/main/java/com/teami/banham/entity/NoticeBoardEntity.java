package com.teami.banham.entity;

import com.teami.banham.dto.NoticeBoardDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table (name = "notice_board")
@SequenceGenerator(
        name = "SEQ_NOTICE_BOARD_GEN",
        sequenceName = "SEQ_NOTICE_BOARD",
        initialValue = 1,
        allocationSize = 1
) // 시퀀스 생성
public class NoticeBoardEntity extends BoardBaseEntity{
//    private Long bno; //게시글 고유 번호 --
//    private Long writerMno; //게시글 작성자 고유번호 --
//    private String boardWriter; //게시글 작성자 --
//    private String boardTitle; //게시글 제목 --
//    private String boardContents; //게시글 내용 --
//    private int boardHits; //게시글 조회수 --
//    private String hasFile; //파일첨부 여부
//    private String isDelete; //게시글 삭제 여부
//    private LocalDateTime boardCreatedTime; //게시글 생성 시간
//    private LocalDateTime boardUpdatedTime; //게시글 수정 시간

    @Id //기본키 지정
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "SEQ_NOTICE_BOARD_GEN"
    ) //시퀀스 값을 받아오는 설정
    private Long bno; //게시글 고유번호
    
    @Column
    private String EorN; //이벤트 / 공지사항 구분
    
    @Column
    private Long writerMno; //게시글 작성자 고유번호

    @Column
    private String boardWriter; //게시글 작성자

    @Column
    private String boardTitle; //게시글 제목

    @Column(length = 1000)
    private String boardContents; //게시글 내용

    @Column
    private int boardHits; //게시글 조회수

    @Column
    private String hasFile; //파일 첨부 여부

    @Column
    private String isDelete; //게시글 삭제 상태




    @OneToMany(mappedBy = "noticeBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<NoticeBoardFileEntity> noticeBoardFileEntityList = new ArrayList<>();


    //파일 없는 상태의 게시글 DTO를 Entity타입으로 변환하기 위한 메소드
    public static NoticeBoardEntity toSaveEntity(NoticeBoardDTO noticeBoardDTO) {

        NoticeBoardEntity noticeBoardEntity = new NoticeBoardEntity();

        noticeBoardEntity.setEorN(noticeBoardDTO.getEorN());
        noticeBoardEntity.setWriterMno(noticeBoardDTO.getWriterMno());
        noticeBoardEntity.setBoardWriter(noticeBoardDTO.getBoardWriter());
        noticeBoardEntity.setBoardTitle(noticeBoardDTO.getBoardTitle());
        noticeBoardEntity.setBoardContents(noticeBoardDTO.getBoardContents());
        noticeBoardEntity.setBoardHits(0); //게시글 첫 작성이니 조회수는 0으로 고정
        noticeBoardEntity.setHasFile("N"); //파일첨부 없이 저장할 경우 넘어오는 메소드이기 때문에 N 삽입
        noticeBoardEntity.setIsDelete("N"); //첫 작성이니 삭제하지 않은 상태

        return noticeBoardEntity;
    }

    public static NoticeBoardEntity toSaveFileEntity(NoticeBoardDTO noticeBoardDTO) {  //다른부분은 toSaveEntity와 같지만 파일첨부 여부가 다름

        NoticeBoardEntity noticeBoardEntity = new NoticeBoardEntity();

        noticeBoardEntity.setEorN(noticeBoardDTO.getEorN());
        noticeBoardEntity.setWriterMno(noticeBoardDTO.getWriterMno());
        noticeBoardEntity.setBoardWriter(noticeBoardDTO.getBoardWriter());
        noticeBoardEntity.setBoardTitle(noticeBoardDTO.getBoardTitle());
        noticeBoardEntity.setBoardContents(noticeBoardDTO.getBoardContents());
        noticeBoardEntity.setBoardHits(0); //게시글 첫 작성이니 조회수는 0으로 고정
        noticeBoardEntity.setHasFile("Y"); //파일첨부된 DTO를 ENtity로 변환하는 작업이기 때문에 파일첨부 여부에 Y삽입
        noticeBoardEntity.setIsDelete("N"); //첫 작성이니 삭제하지 않은 상태

        return noticeBoardEntity;
    }

    public static NoticeBoardEntity toUpdateEntity(NoticeBoardDTO noticeBoardDTO) {

        NoticeBoardEntity noticeBoardEntity = new NoticeBoardEntity();

        noticeBoardEntity.setBno(noticeBoardDTO.getBno()); //insert가 아닌 update쿼리문이 생성되도록 pk값도 넘겨줌

        noticeBoardEntity.setEorN(noticeBoardDTO.getEorN());
        noticeBoardEntity.setWriterMno(noticeBoardDTO.getWriterMno());
        noticeBoardEntity.setBoardWriter(noticeBoardDTO.getBoardWriter());
        noticeBoardEntity.setBoardTitle(noticeBoardDTO.getBoardTitle());
        noticeBoardEntity.setBoardContents(noticeBoardDTO.getBoardContents());
        noticeBoardEntity.setBoardHits(noticeBoardDTO.getBoardHits());
        noticeBoardEntity.setHasFile(noticeBoardDTO.getHasFile());
        noticeBoardEntity.setIsDelete(noticeBoardDTO.getIsDelete());

        return noticeBoardEntity;
    }
}
