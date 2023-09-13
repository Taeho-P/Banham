package com.teami.banham.controller.missingController;

import com.teami.banham.dto.MissingDTO.MisRequestDTO;
import com.teami.banham.dto.MissingDTO.MisResponseDto;
import com.teami.banham.dto.MissingDTO.misPaging.MisCommonParams;
import com.teami.banham.dto.adoptDTO.AdoptPaging.AdoptCommonParams;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.service.MissingService.MisCommentMapper;
import com.teami.banham.service.MissingService.MissingService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MisApiController {

    private final MissingService missingService;
    private final MisCommentMapper commentMapper;
//    @GetMapping("/adopt/cmtcount")
//    public List<Integer> getCount (List<Integer> boardId) {
//        List<Integer> getCount;
//        for(int i = 0 ; i < boardId.size() ;i++){
//
//        }
//    }댓글..생각하다 잠이들다


    //리스트 전체 조회
    @GetMapping("/missing")
    public Map<String, Object> findAll(final MisCommonParams params) {
        return missingService.findAll(params);
    }

    //글 생성
    @PostMapping("/missing")
    public Long save (@RequestBody final MisRequestDTO params) {
        return missingService.save(params);
    }


    //글 수정 (파일 전 ver)
//    @PatchMapping("/adopt/{id}")
//    public Long save(@PathVariable final Long id, @RequestBody final AdoptRequestDTO params){
//        return missingService.update(id, params);
//    }

    //글 삭제
    @DeleteMapping("/missing/{id}")
    public Long delete(@PathVariable final Long id) {
        return missingService.delete(id);
    }
    //글 상세 조회 (view)
    @GetMapping("/missing/{id}")
    public MisResponseDto findById(@PathVariable final Long id) {
        return missingService.findById(id);
    }







}
