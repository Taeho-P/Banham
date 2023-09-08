package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdoptFileMapper {
    /**
     * 파일 정보 저장
     * @param files - 파일 정보 리스트
     */
    void saveAll(List<AdoptFileRequest> files);

}
