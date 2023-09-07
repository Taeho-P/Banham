package com.teami.banham.controller.adoptController;

import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.dto.adoptDTO.AdoptResponseDto;
import com.teami.banham.service.adoptService.AdoptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdoptApiController {

    private final AdoptService adoptService;

    //글 생성
    @PostMapping("/adopt")
    public Long save (@RequestBody final AdoptRequestDTO params) {
        return adoptService.save(params);
    }

    //리스트 조회
    @GetMapping("/adopt")
    public List<AdoptResponseDto> findAll() {
        return adoptService.findAll();
    }

    @PatchMapping("/adopt/{id}")
    public Long save(@PathVariable final Long id, @RequestBody final AdoptRequestDTO params){
        return adoptService.update(id, params);
    }


}
