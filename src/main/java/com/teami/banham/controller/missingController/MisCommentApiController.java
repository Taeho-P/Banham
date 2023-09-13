package com.teami.banham.controller.missingController;

import com.teami.banham.dto.MissingDTO.MisCommentRequest;
import com.teami.banham.dto.MissingDTO.MisCommentResponse;
import com.teami.banham.dto.MissingDTO.MisSearchDto;
import com.teami.banham.dto.MissingDTO.misPaging.MisPagingResponse;

import com.teami.banham.service.MissingService.MisCommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController //컨트롤러의 모든 메서드에 @ResponseBody 적용, 페이지(html)가 아닌 데이터(객체)리턴
@RequiredArgsConstructor
public class MisCommentApiController {

    private final MisCommentService commentService;

    //댓글 생성
    @PostMapping("/missing/{boardId}/comments") //@PathVariable = uri에서 템플릿형태로 파라미터 전달받음
    public MisCommentResponse saveComment(@PathVariable final Long boardId, @RequestBody final MisCommentRequest params) {
        Long id = commentService.saveComment(params);
        return commentService.findCommentById(id);
    }

    // 댓글 리스트 조회
    @GetMapping("/missing/{boardId}/comments")
    public MisPagingResponse<MisCommentResponse> findAllComment(@PathVariable final Long boardId, final MisSearchDto params) {
        return commentService.findAllComment(params);
    }

    // 댓글 상세정보 조회
    @GetMapping("/missing/{boardId}/comments/{id}")
    public MisCommentResponse findCommentById(@PathVariable final Long boardId, @PathVariable final Long id) {
        return commentService.findCommentById(id);
    }

    // 기존 댓글 수정
    @PatchMapping("/missing/{boardId}/comments/{id}")
    public MisCommentResponse updateComment(@PathVariable final Long boardId, @PathVariable final Long id, @RequestBody final MisCommentRequest params) {
        commentService.updateComment(params);
        return commentService.findCommentById(id);
    }

    // 댓글 삭제
    @DeleteMapping("/missing/{boardId}/comments/{id}")
    public Long deleteComment(@PathVariable final Long boardId, @PathVariable final Long id) {
        return commentService.deleteComment(id);
    }
}
