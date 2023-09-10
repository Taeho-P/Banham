package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptCommentRequest;
import com.teami.banham.dto.adoptDTO.AdoptCommentResponse;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptPagination;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptPagingResponse;
import com.teami.banham.dto.adoptDTO.AdoptSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptCommentService {
    private final AdoptCommentMapper commentMapper;

    /**
     * 댓글 저장
     * @param params - 댓글 정보
     * @return Generated PK
     */
    @Transactional
    public Long saveComment(final AdoptCommentRequest params) {
        commentMapper.save(params);
        return params.getId();
    }

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */
    public AdoptCommentResponse findCommentById(final Long id) {
        return commentMapper.findById(id);
    }

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     * @return PK
     */
    @Transactional
    public Long updateComment(final AdoptCommentRequest params) {
        commentMapper.update(params);
        return params.getId();
    }

    /**
     * 댓글 삭제
     * @param id - PK
     * @return PK
     */
    @Transactional
    public Long deleteComment(final Long id) {
        commentMapper.deleteById(id);
        return id;
    }

    /**
     * 댓글 리스트 조회
     * @param params - 게시글 번호 (FK)
     * @return 특정 게시글에 등록된 댓글 리스트
     */
    public AdoptPagingResponse<AdoptCommentResponse> findAllComment(final AdoptSearchDto params) {
        int count = commentMapper.count(params);
        if (count < 1) {
            return new AdoptPagingResponse<>(Collections.emptyList(), null);
        }

        AdoptPagination pagination = new AdoptPagination(count, params);
        List<AdoptCommentResponse> list = commentMapper.findAll(params);
        return new AdoptPagingResponse<>(list, pagination);
    }

}
