package com.teami.banham.service.MissingService;

import com.teami.banham.dto.MissingDTO.MisCommentRequest;
import com.teami.banham.dto.MissingDTO.MisCommentResponse;
import com.teami.banham.dto.MissingDTO.MisSearchDto;
import com.teami.banham.dto.MissingDTO.misPaging.MisPagination;
import com.teami.banham.dto.MissingDTO.misPaging.MisPagingResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MisCommentService {
    private final MisCommentMapper commentMapper;

    /**
     * 댓글 저장
     * @param params - 댓글 정보
     * @return Generated PK
     */
    @Transactional
    public Long saveComment(final MisCommentRequest params) {
        commentMapper.save(params);
        return params.getId();
    }

    /**
     * 댓글 상세정보 조회
     * @param id - PK
     * @return 댓글 상세정보
     */
    public MisCommentResponse findCommentById(final Long id) {
        return commentMapper.findById(id);
    }

    /**
     * 댓글 수정
     * @param params - 댓글 정보
     * @return PK
     */
    @Transactional
    public Long updateComment(final MisCommentRequest params) {
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
    public MisPagingResponse<MisCommentResponse> findAllComment(final MisSearchDto params) {
        int count = commentMapper.count(params);
        if (count < 1) {
            return new MisPagingResponse<>(Collections.emptyList(), null);
        }

        MisPagination pagination = new  MisPagination(count, params);
        List<MisCommentResponse> list = commentMapper.findAll(params);
        return new MisPagingResponse<>(list, pagination);
    }

//    public int getCountCmt (){
//        int count = commentMapper.count();
//    }

}
