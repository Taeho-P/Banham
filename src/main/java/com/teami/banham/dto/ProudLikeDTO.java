package com.teami.banham.dto;

import com.teami.banham.entity.ProudLikeEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProudLikeDTO {
    private Long lno; // likeboard 고유 번호
    private Long bno; // 게시글번호
    private String memberId; // memberId값

    public static ProudLikeDTO toproudLikeDTO(ProudLikeEntity proudLikeEntity){
        ProudLikeDTO proudLikeDTO = new ProudLikeDTO();
        proudLikeDTO.setLno(proudLikeEntity.getLno());
        proudLikeDTO.setMemberId(proudLikeEntity.getMemberId());

        return proudLikeDTO;
    }

}
