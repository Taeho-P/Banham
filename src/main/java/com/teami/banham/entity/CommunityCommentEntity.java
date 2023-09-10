package com.teami.banham.entity;

import com.teami.banham.dto.CommunityCommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="community_comment_table")
@SequenceGenerator(
        name="COMMUNITY_COMMENT_SEQ_GEN", //시퀀스 제네레이터 이름
        sequenceName = "SEQ_COMMUNITY_COMMENT", //시퀀스 이름
        initialValue = 1, //시퀀스 시작값
        allocationSize = 1 //할당할 범위 사이즈
)
public class CommunityCommentEntity extends BoardBaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "COMMUNITY_COMMENT_SEQ_GEN")
    private Long cno; // 댓글 고유 번호

    @Column
    private String commentContents;

    @Column
    private String writer;

    @Column
    private String memberId;

    @Column
    private int delete_ck;

    /*  Board:Comment 1:다수 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private CommunityBoardEntity communityBoardEntity;

    public static CommunityCommentEntity toCommunityBoardSaveEntity(CommunityCommentDTO commentDTO, CommunityBoardEntity communityBoardEntity){
        CommunityCommentEntity communityCommentEntity = new CommunityCommentEntity();
        communityCommentEntity.setCno(commentDTO.getCno());
        communityCommentEntity.setCommentContents(commentDTO.getCommentContents());
        communityCommentEntity.setWriter(commentDTO.getWriter());
        communityCommentEntity.setMemberId(commentDTO.getMemberId());
        communityCommentEntity.setDelete_ck(commentDTO.getDelete_ck());
        communityCommentEntity.setCommunityBoardEntity(communityBoardEntity);

        return communityCommentEntity;
    }

}
