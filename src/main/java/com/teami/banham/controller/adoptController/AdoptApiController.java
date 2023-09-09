package com.teami.banham.controller.adoptController;

import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptFileResponse;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.service.adoptService.AdoptFileService;
import com.teami.banham.service.adoptService.AdoptService;
import com.teami.banham.service.adoptService.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdoptApiController {

    private final AdoptService adoptService;



    //리스트 전체 조회
    @GetMapping("/adopt")
    public List<AdoptResponseDto> findAll() {
        return adoptService.findAll();
    }

    //글 생성
    @PostMapping("/adopt")
    public Long save (@RequestBody final AdoptRequestDTO params) {
        return adoptService.save(params);
    }


    //글 수정 (파일 전 ver)
//    @PatchMapping("/adopt/{id}")
//    public Long save(@PathVariable final Long id, @RequestBody final AdoptRequestDTO params){
//        return adoptService.update(id, params);
//    }

    //글 삭제
    @DeleteMapping("/adopt/{id}")
    public Long delete(@PathVariable final Long id) {
        return adoptService.delete(id);
    }
    //글 상세 조회 (view)
    @GetMapping("/adopt/{id}")
    public AdoptResponseDto findById(@PathVariable final Long id) {
        return adoptService.findById(id);
    }







}
