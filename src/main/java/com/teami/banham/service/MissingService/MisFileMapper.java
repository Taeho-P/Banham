package com.teami.banham.service.MissingService;

import com.teami.banham.dto.MissingDTO.MisFileRequest;
import com.teami.banham.dto.MissingDTO.MisFileResponse;
import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptFileResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MisFileMapper {

    /**전체 파일**/
    List<MisFileResponse> findAll();


    /**
     * 파일 정보 저장
     * @param files - 파일 정보 리스트
     */
    void saveAll(List<MisFileRequest> files);



    /**
     * 파일 리스트 조회
     * @param boardId - 게시글 번호 (FK)
     * @return 파일 리스트
     */
    List<MisFileResponse> findAllFileByBoardId(Long boardId);

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */
    List<MisFileResponse> findAllByIds(List<Long> ids);

    /**
     * 파일 삭제
     * @param ids - PK 리스트
     */
    void deleteAllByIds(List<Long> ids);

    /**
     * 파일 상세정보 조회
     * @param id - PK
     * @return 파일 상세정보
     */
    MisFileResponse findById(Long id);
}
