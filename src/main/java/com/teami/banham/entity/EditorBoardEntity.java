package com.teami.banham.entity;

import com.teami.banham.dto.EditorBoardDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table (name = "editor_board")
@SequenceGenerator(
        name = "SEQ_EDITOR_BOARD_GEN",
        sequenceName = "SEQ_EDITOR_BOARD",
        initialValue = 1,
        allocationSize = 1
) // 시퀀스 생성
public class EditorBoardEntity extends BoardBaseEntity{
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
            generator = "SEQ_EDITOR_BOARD_GEN"
    ) //시퀀스 값을 받아오는 설정
    private Long bno; //게시글 고유번호

    @Column
    private Long writerMno; //게시글 작성자 고유번호

    @Column
    private String boardWriter; //게시글 작성자
    
    @Column
    private String boardLocal; //추천 지역 정보

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




    @OneToMany(mappedBy = "editorBoardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EditorBoardFileEntity> editorBoardFileEntityList = new ArrayList<>();


    //파일 없는 상태의 게시글 DTO를 Entity타입으로 변환하기 위한 메소드
    public static EditorBoardEntity toSaveEntity(EditorBoardDTO editorBoardDTO) {

        EditorBoardEntity editorBoardEntity = new EditorBoardEntity();

        editorBoardEntity.setWriterMno(editorBoardDTO.getWriterMno());
        editorBoardEntity.setBoardWriter(editorBoardDTO.getBoardWriter());
        editorBoardEntity.setBoardTitle(editorBoardDTO.getBoardTitle());
        editorBoardEntity.setBoardContents(editorBoardDTO.getBoardContents());
        editorBoardEntity.setBoardLocal(editorBoardDTO.getBoardLocal());
        editorBoardEntity.setBoardHits(0); //게시글 첫 작성이니 조회수는 0으로 고정
        editorBoardEntity.setHasFile("N"); //파일첨부 없이 저장할 경우 넘어오는 메소드이기 때문에 N 삽입
        editorBoardEntity.setIsDelete("N"); //첫 작성이니 삭제하지 않은 상태

        return editorBoardEntity; //eorN지우러감
    }

    public static EditorBoardEntity toSaveFileEntity(EditorBoardDTO editorBoardDTO) {  //다른부분은 toSaveEntity와 같지만 파일첨부 여부가 다름

        EditorBoardEntity editorBoardEntity = new EditorBoardEntity();

        editorBoardEntity.setWriterMno(editorBoardDTO.getWriterMno());
        editorBoardEntity.setBoardWriter(editorBoardDTO.getBoardWriter());
        editorBoardEntity.setBoardTitle(editorBoardDTO.getBoardTitle());
        editorBoardEntity.setBoardContents(editorBoardDTO.getBoardContents());
        editorBoardEntity.setBoardLocal(editorBoardDTO.getBoardLocal());
        editorBoardEntity.setBoardHits(0); //게시글 첫 작성이니 조회수는 0으로 고정
        editorBoardEntity.setHasFile("Y"); //파일첨부된 DTO를 ENtity로 변환하는 작업이기 때문에 파일첨부 여부에 Y삽입
        editorBoardEntity.setIsDelete("N"); //첫 작성이니 삭제하지 않은 상태

        return editorBoardEntity;
    }

    public static EditorBoardEntity toUpdateEntity(EditorBoardDTO editorBoardDTO) {

        EditorBoardEntity editorBoardEntity = new EditorBoardEntity();

        editorBoardEntity.setBno(editorBoardDTO.getBno()); //insert가 아닌 update쿼리문이 생성되도록 pk값도 넘겨줌

        editorBoardEntity.setWriterMno(editorBoardDTO.getWriterMno());
        editorBoardEntity.setBoardWriter(editorBoardDTO.getBoardWriter());
        editorBoardEntity.setBoardLocal(editorBoardDTO.getBoardLocal());
        editorBoardEntity.setBoardTitle(editorBoardDTO.getBoardTitle());
        editorBoardEntity.setBoardContents(editorBoardDTO.getBoardContents());
        editorBoardEntity.setBoardHits(editorBoardDTO.getBoardHits());
        editorBoardEntity.setHasFile(editorBoardDTO.getHasFile());
        editorBoardEntity.setIsDelete(editorBoardDTO.getIsDelete());

        return editorBoardEntity;
    }
}
