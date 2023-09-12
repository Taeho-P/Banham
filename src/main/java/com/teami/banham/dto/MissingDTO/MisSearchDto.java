package com.teami.banham.dto.MissingDTO;

import com.teami.banham.dto.MissingDTO.misPaging.MisCommonParams;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptCommonParams;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MisSearchDto extends MisCommonParams
{
    private  Long boardId; //게시물번호 fk

}
