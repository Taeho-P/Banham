package com.teami.banham.entity;

import com.teami.banham.dto.CommunityBoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "community_board_table")
@SequenceGenerator(
        name="SEQ_COMMUNITY_BOARD_GEN",
        sequenceName="SEQ_COMMUNITY_BOARD",
        initialValue = 1, //시작값
        allocationSize = 1
)
public class CommunityBoardEntity extends BoardBaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "SEQ_COMMUNITY_BOARD_GEN")
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

    @OneToMany(mappedBy = "communityBoardEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch=FetchType.LAZY)
    private List<CommunityBoardFileEntity> communityBoardFileEntityList = new ArrayList<>();

    @Column
    private int delete_ck;

    @OneToMany(mappedBy="communityBoardEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<CommunityCommentEntity> communityCommentEntityList =new ArrayList<>();


    // 파일이 없는 상태에서 게시글 저장시 사용되는 메소드
    public static CommunityBoardEntity toSaveEntity(CommunityBoardDTO communityBoardDTO){
        CommunityBoardEntity communityBoardEntity = new CommunityBoardEntity();
        communityBoardEntity.setBno(communityBoardDTO.getBno());
        communityBoardEntity.setTitle(communityBoardDTO.getTitle());
        communityBoardEntity.setContents(communityBoardDTO.getContents());
        if(communityBoardDTO.getHits()==0){
            communityBoardEntity.setHits(0);
        }else{
            communityBoardEntity.setHits(communityBoardDTO.getHits());
        }
        communityBoardEntity.setWriter(communityBoardDTO.getWriter());
        communityBoardEntity.setMemberId(communityBoardDTO.getMemberId());
        communityBoardEntity.setDelete_ck(0);
        communityBoardEntity.setHasFile(0); // 파일 없음

        return communityBoardEntity;
    }

    public static CommunityBoardEntity toSaveFileEntity(CommunityBoardDTO communityBoardDTO){
        CommunityBoardEntity communityBoardEntity = new CommunityBoardEntity();
        communityBoardEntity.setBno(communityBoardDTO.getBno());
        communityBoardEntity.setTitle(communityBoardDTO.getTitle());
        communityBoardEntity.setContents(communityBoardDTO.getContents());
        if(communityBoardDTO.getHits()==0){
            communityBoardEntity.setHits(0);
        }else{
            communityBoardEntity.setHits(communityBoardDTO.getHits());
        }
        communityBoardEntity.setWriter(communityBoardDTO.getWriter());
        communityBoardEntity.setMemberId(communityBoardDTO.getMemberId());
        communityBoardEntity.setDelete_ck(0);
        communityBoardEntity.setHasFile(1); // 파일 있음

        return communityBoardEntity;
    }

}
