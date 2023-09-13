package com.teami.banham.dto;

import com.teami.banham.entity.EditorBoardEntity;
import com.teami.banham.entity.EditorBoardFileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EditorBoardDTO {
    private Long bno; //게시글 고유 번호
    private Long writerMno; //게시글 작성자 고유번호
    private String boardWriter; //게시글 작성자
    private String boardTitle; //게시글 제목
    private String boardContents; //게시글 내용
    private int boardHits; //게시글 조회수
    private String isDelete; //게시글 삭제 여부
    private String boardLocal; //추천 지역 정보
    private LocalDateTime boardCreatedTime; //게시글 생성 시간
    private LocalDateTime boardUpdatedTime; //게시글 수정 시간


    //파일첨부를 위해 추가
    //다중 파일첨부를 위해 List에 담는다
    private List<MultipartFile> boardFile; //실제 파일을 담아줄수 있는 역할 (save.html에서 사용자가 첨부한 파일을 MultipartFile타입에 담아서 Controller로 전달)
    private List<String> originalFile; //원본 파일 이름
    private List<String> storedFile; //서버 저장시 변경된 파일 이름
    private String hasFile; //파일첨부 여부


    //boardTitle, boardWriter, createdTime, boardHits
    //Alt + insert 키를 누르면 자동 생성 가능 (getter, setter, constructor 등등)
    public EditorBoardDTO(Long bno, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime, String hasFile, List<EditorBoardFileEntity> boardFileEntityList) {
        this.bno = bno;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        if (hasFile== "N") {
            this.setHasFile(hasFile);
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> repositoryFileNameList = new ArrayList<>();
            this.setHasFile(hasFile);
            for (EditorBoardFileEntity FileEntity : boardFileEntityList) {
                originalFileNameList.add(FileEntity.getOriginalFileName());
                repositoryFileNameList.add(FileEntity.getStoredFileName());
            }
            this.setOriginalFile(originalFileNameList);
            this.setStoredFile(repositoryFileNameList);
        }
    }

    public EditorBoardDTO(Long bno, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.bno = bno;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public EditorBoardDTO(Long bno, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime, List<EditorBoardFileEntity> boardFile, String boardLocal) {
        this.bno = bno;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
        this.boardLocal = boardLocal;

        this.storedFile = new ArrayList<>();
        storedFile.add(boardFile.get(0).getStoredFileName());
    }

    public static EditorBoardDTO toEditorBoardDTO(EditorBoardEntity editorBoardEntity) {
        EditorBoardDTO editorBoardDTO = new EditorBoardDTO();


//        private Long bno; //게시글 고유 번호
//        private String eorN; //이벤트 / 공지사항 구분
//        private Long writerMno; //게시글 작성자 고유번호
//        private String boardWriter; //게시글 작성자
//        private String boardTitle; //게시글 제목
//        private String boardContents; //게시글 내용
//        private int boardHits; //게시글 조회수
//        private String isDelete; //게시글 삭제 여부
//        private LocalDateTime boardCreatedTime; //게시글 생성 시간
//        private LocalDateTime boardUpdatedTime; //게시글 수정 시간

        editorBoardDTO.setBno(editorBoardEntity.getBno());
        editorBoardDTO.setWriterMno(editorBoardEntity.getWriterMno());
        editorBoardDTO.setBoardLocal(editorBoardEntity.getBoardLocal());
        editorBoardDTO.setBoardWriter(editorBoardEntity.getBoardWriter());
        editorBoardDTO.setBoardTitle(editorBoardEntity.getBoardTitle());
        editorBoardDTO.setBoardContents(editorBoardEntity.getBoardContents());
        editorBoardDTO.setBoardHits(editorBoardEntity.getBoardHits());
        editorBoardDTO.setIsDelete(editorBoardEntity.getIsDelete());
        editorBoardDTO.setBoardCreatedTime(editorBoardEntity.getCreatedTime());
        editorBoardDTO.setBoardUpdatedTime(editorBoardEntity.getUpdatedTime());


        if (editorBoardEntity.getHasFile() == "N") {
            editorBoardDTO.setHasFile(editorBoardEntity.getHasFile()); // N
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            editorBoardDTO.setHasFile(editorBoardEntity.getHasFile()); // Y
            //noticeBoardFileEntityList
            for(EditorBoardFileEntity boardFileEntity : editorBoardEntity.getEditorBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            editorBoardDTO.setOriginalFile(originalFileNameList);
            editorBoardDTO.setStoredFile(storedFileNameList);

            //파일 이름을 가져가야 함
            //originalFileName과 storedFileName은 NoticeBoardFileEntity에 있음
            //join
            // SELECT * FROM notice_board b, notice_board_file bf WHERE b.bno=bf.bno AND WHERE b.bno=?
        }
        return editorBoardDTO;
    }

}
