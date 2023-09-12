package com.teami.banham.entity;

import com.teami.banham.dto.ProudCommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="proud_comment_table")
@SequenceGenerator(
        name="PROUD_COMMENT_SEQ_GEN", //시퀀스 제네레이터 이름
        sequenceName = "SEQ_PROUD_COMMENT", //시퀀스 이름
        initialValue = 1, //시퀀스 시작값
        allocationSize = 1 //할당할 범위 사이즈
)
public class ProudCommentEntity extends BoardBaseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator = "PROUD_COMMENT_SEQ_GEN")
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bno", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ProudBoardEntity proudBoardEntity;

    public static ProudCommentEntity toProudBoardSaveEntity(ProudCommentDTO commentDTO, ProudBoardEntity proudBoardEntity){
        ProudCommentEntity proudCommentEntity = new ProudCommentEntity();
        proudCommentEntity.setCno(commentDTO.getCno());
        proudCommentEntity.setCommentContents(commentDTO.getCommentContents());
        proudCommentEntity.setWriter(commentDTO.getWriter());
        proudCommentEntity.setMemberId(commentDTO.getMemberId());
        proudCommentEntity.setDelete_ck(commentDTO.getDelete_ck());
        proudCommentEntity.setProudBoardEntity(proudBoardEntity);

        return proudCommentEntity;
    }

}
