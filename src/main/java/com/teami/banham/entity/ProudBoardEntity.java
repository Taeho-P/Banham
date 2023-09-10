package com.teami.banham.entity;

import com.teami.banham.dto.ProudBoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "proud_board_table")
@SequenceGenerator(
        name="SEQ_BOARD_GEN",
        sequenceName="SEQ_BOARD",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class ProudBoardEntity extends BoardBaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "SEQ_BOARD_GEN")
    private Long bno; // 게시글 고유 번호

    @Column
    private String title; // 게시글 제목

    @Column
    private String contents; // 게시글 내용

    @Column
    private int hits; // 게시글 조회 횟수

    @Column
    private String writer; // 글쓴 사람 닉네임

    @Column
    private String memberId; // 글쓴사람 ID

    @Column
    private int hasFile; // 파일 첨부 체크 : 0 - 첨부x , 1 - 첨부o

    @OneToMany(mappedBy = "proudBoardEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch=FetchType.LAZY)
    private List<ProudBoardFileEntity> proudBoardFileEntityList = new ArrayList<>();

    @Column
    private int delete_ck;

    @OneToMany(mappedBy="proudBoardEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<ProudCommentEntity> proudCommentEntityList =new ArrayList<>();


    // 파일이 없는 상태에서 게시글 저장시 사용되는 메소드
    public static ProudBoardEntity toSaveEntity(ProudBoardDTO proudBoardDTO){
        ProudBoardEntity proudBoardEntity = new ProudBoardEntity();
        proudBoardEntity.setBno(proudBoardDTO.getBno());
        proudBoardEntity.setTitle(proudBoardDTO.getTitle());
        proudBoardEntity.setContents(proudBoardDTO.getContents());
        if(proudBoardDTO.getHits()==0){
            proudBoardEntity.setHits(0);
        }else{
            proudBoardEntity.setHits(proudBoardDTO.getHits());
        }
        proudBoardEntity.setWriter(proudBoardDTO.getWriter());
        proudBoardEntity.setMemberId(proudBoardDTO.getMemberId());
        proudBoardEntity.setDelete_ck(0);
        proudBoardEntity.setHasFile(0); // 파일 없음

        return proudBoardEntity;
    }

    public static ProudBoardEntity toSaveFileEntity(ProudBoardDTO proudBoardDTO){
        ProudBoardEntity proudBoardEntity = new ProudBoardEntity();
        proudBoardEntity.setBno(proudBoardDTO.getBno());
        proudBoardEntity.setTitle(proudBoardDTO.getTitle());
        proudBoardEntity.setContents(proudBoardDTO.getContents());
        if(proudBoardDTO.getHits()==0){
            proudBoardEntity.setHits(0);
        }else{
            proudBoardEntity.setHits(proudBoardDTO.getHits());
        }
        proudBoardEntity.setWriter(proudBoardDTO.getWriter());
        proudBoardEntity.setMemberId(proudBoardDTO.getMemberId());
        proudBoardEntity.setDelete_ck(0);
        proudBoardEntity.setHasFile(1); // 파일 있음

        return proudBoardEntity;
    }

}
