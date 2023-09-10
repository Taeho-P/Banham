package com.teami.banham.dto.adoptDTO;

import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptCommonParams;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdoptSearchDto extends AdoptCommonParams
{
    private  Long boardId; //게시물번호 fk

}
