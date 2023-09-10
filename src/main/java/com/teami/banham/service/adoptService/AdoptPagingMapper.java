package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptCommonParams;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdoptPagingMapper {


    int count (final AdoptCommonParams params);

    List<AdoptResponseDto> findAll(final AdoptCommonParams params);
}
