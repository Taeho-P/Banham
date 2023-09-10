package com.teami.banham.controller.adoptController;

import com.teami.banham.dto.adoptDTO.AdoptCommentRequest;
import com.teami.banham.dto.adoptDTO.AdoptCommentResponse;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptPagingResponse;
import com.teami.banham.dto.adoptDTO.AdoptSearchDto;
import com.teami.banham.service.adoptService.AdoptCommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController //컨트롤러의 모든 메서드에 @ResponseBody 적용, 페이지(html)가 아닌 데이터(객체)리턴
@RequiredArgsConstructor
public class AdoptCommentApiController {

    private final AdoptCommentService commentService;

    //댓글 생성
    @PostMapping("/adopt/{boardId}/comments") //@PathVariable = uri에서 템플릿형태로 파라미터 전달받음
    public AdoptCommentResponse saveComment(@PathVariable final Long boardId, @RequestBody final AdoptCommentRequest params) {
        Long id = commentService.saveComment(params);
        return commentService.findCommentById(id);
    }

    // 댓글 리스트 조회
    @GetMapping("/adopt/{boardId}/comments")
    public AdoptPagingResponse<AdoptCommentResponse> findAllComment(@PathVariable final Long boardId, final AdoptSearchDto params) {
        return commentService.findAllComment(params);
    }

    // 댓글 상세정보 조회
    @GetMapping("/adopt/{boardId}/comments/{id}")
    public AdoptCommentResponse findCommentById(@PathVariable final Long boardId, @PathVariable final Long id) {
        return commentService.findCommentById(id);
    }

    // 기존 댓글 수정
    @PatchMapping("/adopt/{boardId}/comments/{id}")
    public AdoptCommentResponse updateComment(@PathVariable final Long boardId, @PathVariable final Long id, @RequestBody final AdoptCommentRequest params) {
        commentService.updateComment(params);
        return commentService.findCommentById(id);
    }

    // 댓글 삭제
    @DeleteMapping("/adopt/{boardId}/comments/{id}")
    public Long deleteComment(@PathVariable final Long boardId, @PathVariable final Long id) {
        return commentService.deleteComment(id);
    }
}
