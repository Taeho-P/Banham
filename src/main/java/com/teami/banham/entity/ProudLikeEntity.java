package com.teami.banham.entity;

import com.teami.banham.dto.ProudLikeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="proud_like_table")
@SequenceGenerator(
        name="PROUD_LIKE_SEQ_GEN",
        sequenceName = "SEQ_PROUD_LIKE",
        initialValue = 1,
        allocationSize = 1
)
public class ProudLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "PROUD_LIKE_SEQ_GEN")
    private Long lno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="bno")
    private ProudBoardEntity proudBoardEntity;

    @Column
    private String memberId;

    public static ProudLikeEntity toProudLikeEntity(ProudLikeDTO proudBoardDTO, ProudBoardEntity proudBoardEntity){
        ProudLikeEntity proudLikeEntity=new ProudLikeEntity();
        proudLikeEntity.setMemberId(proudBoardDTO.getMemberId());
        proudLikeEntity.setProudBoardEntity(proudBoardEntity);

        return proudLikeEntity;
    }



}
