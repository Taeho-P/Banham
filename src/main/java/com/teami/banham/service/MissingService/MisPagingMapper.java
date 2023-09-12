package com.teami.banham.service.MissingService;

import com.teami.banham.dto.MissingDTO.MisResponseDto;
import com.teami.banham.dto.MissingDTO.misPaging.MisCommonParams;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptCommonParams;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MisPagingMapper {


    int count (final MisCommonParams params);

    List<MisResponseDto> findAll(final MisCommonParams params);

    List<MisResponseDto> findMyMissing(Long writer);
}
