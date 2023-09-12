package com.teami.banham.controller.missingController;

import com.teami.banham.dto.MissingDTO.MisFileRequest;
import com.teami.banham.dto.MissingDTO.MisFileResponse;
import com.teami.banham.dto.MissingDTO.MisRequestDTO;

import com.teami.banham.service.MissingService.MisFileService;
import com.teami.banham.service.MissingService.MisFileUtils;
import com.teami.banham.service.MissingService.MissingService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MisFileController {
    private final MissingService missingService;

    private final MisFileUtils fileUtils;
    private final MisFileService misFileService;


    //글 수정 file ver
    @PostMapping("/api/missing/{id}")
    public Long save(@PathVariable final Long id, final MisRequestDTO params){
        List<MisFileRequest> uploadFiles = fileUtils.uploadFiles(params.getFiles());
        if(!uploadFiles.isEmpty()){
            misFileService.saveFiles(id, uploadFiles);
        }
        System.out.println("remove "+params.getRemoveFileIds());
        List<MisFileResponse> deleteFiles = misFileService.findAllFileByIds(params.getRemoveFileIds());
        fileUtils.deleteFiles(deleteFiles);
        misFileService.deleteAllFileByIds(params.getRemoveFileIds());


        return missingService.update(id, params);
    }


    //test file저장ver 글 생성
    @PostMapping("/api/missing/test")
    public String savePost(final MisRequestDTO params){
        Long id = missingService.save(params);

        List<MisFileRequest> files = fileUtils.uploadFiles(params.getFiles());
//        System.out.println(files.get(0));
        if(!files.isEmpty()){
            misFileService.saveFiles(id, files);
        }

        return "redirect:/board/missing";
    }

    //file
    // 파일 전체 조회
    @GetMapping("/api/missing/allfiles")
    public List<MisFileResponse> findAll(){
        return misFileService.findAll();
    }



    // 파일 리스트 조회
    @GetMapping("/api/missing/{boardId}/files")
    public List<MisFileResponse> findAllFileByBoardId(@PathVariable final Long boardId) {
        System.out.println("file조회start");
        return misFileService.findAllFileByBoardId(boardId);
    }


    //파일 다운로드
    @GetMapping("/api/missing/{boardId}/files/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable final Long boardId, @PathVariable final Long fileId) {
        MisFileResponse file = misFileService.findFileById(fileId);
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
