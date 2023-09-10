package com.teami.banham.dto;

import com.teami.banham.entity.NoticeBoardEntity;
import com.teami.banham.entity.NoticeBoardFileEntity;
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
public class NoticeBoardDTO {
    private Long bno; //게시글 고유 번호
    private String eorN; //이벤트 / 공지사항 구분
    private Long writerMno; //게시글 작성자 고유번호
    private String boardWriter; //게시글 작성자
    private String boardTitle; //게시글 제목
    private String boardContents; //게시글 내용
    private int boardHits; //게시글 조회수
    private String isDelete; //게시글 삭제 여부
    private LocalDateTime boardCreatedTime; //게시글 생성 시간
    private LocalDateTime boardUpdatedTime; //게시글 수정 시간


    //파일첨부를 위해 추가
    //다중 파일첨부를 위해 List에 담는다
    private List<MultipartFile> boardFile; //실제 파일을 담아줄수 있는 역할 (save.html에서 사용자가 첨부한 파일을 MultipartFile타입에 담아서 Controller로 전달)
    private List<String> originalFile; //원본 파일 이름
    private List<String> storedFile; //서버 저장시 변경된 파일 이름
    private String hasFile; //파일첨부 여부


    //EorN, boardTitle, boardWriter, createdTime, boardHits
    //Alt + insert 키를 누르면 자동 생성 가능 (getter, setter, constructor 등등)
    public NoticeBoardDTO(Long bno, String eorN, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.bno = bno;
        this.eorN = eorN;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static NoticeBoardDTO toNoticeBoardDTO(NoticeBoardEntity noticeBoardEntity) {
        NoticeBoardDTO noticeBoardDTO = new NoticeBoardDTO();


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

        noticeBoardDTO.setBno(noticeBoardEntity.getBno());
        noticeBoardDTO.setEorN(noticeBoardEntity.getEorN());
        noticeBoardDTO.setWriterMno(noticeBoardEntity.getWriterMno());
        noticeBoardDTO.setBoardWriter(noticeBoardEntity.getBoardWriter());
        noticeBoardDTO.setBoardTitle(noticeBoardEntity.getBoardTitle());
        noticeBoardDTO.setBoardContents(noticeBoardEntity.getBoardContents());
        noticeBoardDTO.setBoardHits(noticeBoardEntity.getBoardHits());
        noticeBoardDTO.setIsDelete(noticeBoardEntity.getIsDelete());
        noticeBoardDTO.setBoardCreatedTime(noticeBoardEntity.getCreatedTime());
        noticeBoardDTO.setBoardUpdatedTime(noticeBoardEntity.getUpdatedTime());


        if (noticeBoardEntity.getHasFile() == "N") {
            noticeBoardDTO.setHasFile(noticeBoardEntity.getHasFile()); // N
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();

            noticeBoardDTO.setHasFile(noticeBoardEntity.getHasFile()); // Y
            //noticeBoardFileEntityList
            for(NoticeBoardFileEntity boardFileEntity : noticeBoardEntity.getNoticeBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            noticeBoardDTO.setOriginalFile(originalFileNameList);
            noticeBoardDTO.setStoredFile(storedFileNameList);

            //파일 이름을 가져가야 함
            //originalFileName과 storedFileName은 NoticeBoardFileEntity에 있음
            //join
            // SELECT * FROM notice_board b, notice_board_file bf WHERE b.bno=bf.bno AND WHERE b.bno=?
        }
        return noticeBoardDTO;
    }

}
