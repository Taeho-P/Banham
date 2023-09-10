package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptCommentRequest;
import com.teami.banham.dto.adoptDTO.AdoptCommentResponse;
import com.teami.banham.dto.adoptDTO.AdoptSearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdoptCommentMapper {
    /**
     * 댓글 저장
     * @param params - 댓글 정보
     */
    void save(AdoptCommentRequest params);

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */
    AdoptCommentResponse findById(Long id);

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     */
    void update(AdoptCommentRequest params);


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
    List<AdoptCommentResponse> findAll(AdoptSearchDto params);

    /**
     * 댓글 수 카운팅
     * @param params - search conditions
     * @return 댓글 수
     */
    int count(AdoptSearchDto params);
}
