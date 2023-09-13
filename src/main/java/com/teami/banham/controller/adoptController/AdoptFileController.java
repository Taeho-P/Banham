package com.teami.banham.controller.adoptController;

import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptFileResponse;
import com.teami.banham.dto.adoptDTO.AdoptRequestDTO;
import com.teami.banham.service.adoptService.AdoptFileService;
import com.teami.banham.service.adoptService.AdoptService;
import com.teami.banham.service.adoptService.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdoptFileController {
    private final AdoptService adoptService;

    private final FileUtils fileUtils;
    private final AdoptFileService adoptFileService;


    //글 수정 file ver
    @PostMapping("/api/adopt/{id}")
    public RedirectView save(@PathVariable final Long id, final AdoptRequestDTO params){
        List<AdoptFileRequest> uploadFiles = fileUtils.uploadFiles(params.getFiles());
        if(!uploadFiles.isEmpty()){
            adoptFileService.saveFiles(id, uploadFiles);
        }
        System.out.println("remove "+params.getRemoveFileIds());
        List<AdoptFileResponse> deleteFiles = adoptFileService.findAllFileByIds(params.getRemoveFileIds());
        fileUtils.deleteFiles(deleteFiles);
        adoptFileService.deleteAllFileByIds(params.getRemoveFileIds());
        adoptService.update(id, params);

        return new RedirectView("/board/adopt") ;
    }


    //test file저장ver 글 생성

    @PostMapping("/api/adopt/test")
    public RedirectView savePost(final AdoptRequestDTO params){

        Long id = adoptService.save(params);
        List<AdoptFileRequest> files = fileUtils.uploadFiles(params.getFiles());
//        System.out.println(files.get(0));
        if(!files.isEmpty()){
           adoptFileService.saveFiles(id, files);
        }
        return new RedirectView("/board/adopt") ;
}

    //file
    // 파일 전체 조회
    @GetMapping("/api/adopt/allfiles")
    public List<AdoptFileResponse> findAll(){
        return adoptFileService.findAll();
    }



    // 파일 리스트 조회
    @GetMapping("/api/adopt/{boardId}/files")
    public List<AdoptFileResponse> findAllFileByBoardId(@PathVariable final Long boardId) {
        System.out.println("file조회start");
        return adoptFileService.findAllFileByBoardId(boardId);
    }


    //파일 다운로드
    @GetMapping("/api/adopt/{boardId}/files/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable final Long boardId, @PathVariable final Long fileId) {
        AdoptFileResponse file = adoptFileService.findFileById(fileId);
        Resource resource = fileUtils.readFileAsResource(file);
        try {
            String filename = URLEncoder.encode(file.getOriginalName(), "UTF-8");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + filename + "\";")
//                    .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "")
                    .body(resource);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("filename encoding failed : " + file.getOriginalName());
        }
    }
}
