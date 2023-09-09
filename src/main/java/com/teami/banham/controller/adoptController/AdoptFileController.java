package com.teami.banham.controller.adoptController;

import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptFileResponse;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.service.adoptService.AdoptFileService;
import com.teami.banham.service.adoptService.AdoptService;
import com.teami.banham.service.adoptService.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdoptFileController {
    private final AdoptService adoptService;

    private final FileUtils fileUtils;
    private final AdoptFileService adoptFileService;


    //test file저장ver 글 생성
    @PostMapping("/adopt/test")
    public String savePost(final AdoptRequestDTO params){
        Long id = adoptService.save(params);

        List<AdoptFileRequest> files = fileUtils.uploadFiles(params.getFiles());
//        System.out.println(files.get(0));
        if(!files.isEmpty()){
            adoptFileService.saveFiles(id, files);
        }

        return "adopt/Adopt";
    }

    //file
    // 파일 리스트 조회
    @GetMapping("/api/adopt/{boardId}/files")
    public List<AdoptFileResponse> findAllFileByBoardId(@PathVariable final Long boardId) {
        System.out.println("file조회start");
        return adoptFileService.findAllFileByBoardId(boardId);
    }
}
