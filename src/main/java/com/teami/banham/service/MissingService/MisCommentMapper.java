package com.teami.banham.service.MissingService;

import com.teami.banham.dto.MissingDTO.MisCommentRequest;
import com.teami.banham.dto.MissingDTO.MisCommentResponse;
import com.teami.banham.dto.MissingDTO.MisSearchDto;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MisCommentMapper {
    /**
     * 댓글 저장
     * @param params - 댓글 정보
     */
    void save(MisCommentRequest params);

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */
    MisCommentResponse findById(Long id);

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     */
    void update(MisCommentRequest params);


    /**
     * 댓글 삭제
     * @param id - PK
     */
    void deleteById(Long id);

    /**
     * 댓글 리스트 조회
     * @param params - search conditions
     * @return 댓글 리스트
     */
    List<MisCommentResponse> findAll(MisSearchDto params);

    /**
     * 댓글 수 카운팅
     * @param params - search conditions
     * @return 댓글 수
     */
    int count(MisSearchDto params);
}
