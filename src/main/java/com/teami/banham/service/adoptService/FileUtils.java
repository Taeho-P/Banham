package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptFileResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUtils {
    private final String uploadPath = Paths.get("C:", "filetest").toString();

    /**
     * 다중 파일 업로드
     * @param multipartFiles - 파일 객체 List
     * @return DB에 저장할 파일 정보 List
     */
    public List<AdoptFileRequest> uploadFiles(final List<MultipartFile> multipartFiles) {
        List<AdoptFileRequest> files = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            files.add(uploadFile(multipartFile));
        }
        return files;
    }

    /**
     * 단일 파일 업로드
     * @param multipartFile - 파일 객체
     * @return DB에 저장할 파일 정보
     */
    public AdoptFileRequest uploadFile(final MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            return null;
        }

        String saveName = generateSaveFilename(multipartFile.getOriginalFilename());
//        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd")).toString();
//        String uploadPath = getUploadPath(today) + File.separator + saveName;
        String uploadPath = getUploadPath(saveName);

        File uploadFile = new File(uploadPath);

        try {
            multipartFile.transferTo(uploadFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return AdoptFileRequest.builder()
                .originalName(multipartFile.getOriginalFilename())
                .storeName(saveName)
                .build();
    }

    /**
     * 저장 파일명 생성
     * @param filename 원본 파일명
     * @return 디스크에 저장할 파일명
     */
    private String generateSaveFilename(final String filename) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String extension = StringUtils.getFilenameExtension(filename);
        return uuid + "." + extension;
    }

    /**
     * 업로드 경로 반환
     * @return 업로드 경로
     */
    private String getUploadPath() {
        return makeDirectories(uploadPath);
    }

    /**
     * 업로드 경로 반환
     * @param addPath - 추가 경로
     * @return 업로드 경로
     */
    private String getUploadPath(final String addPath) {
        return makeDirectories(uploadPath + File.separator + addPath);
    }

    /**
     * 업로드 폴더(디렉터리) 생성
     * @param path - 업로드 경로
     * @return 업로드 경로
     */
    private String makeDirectories(final String path) {
        File dir = new File(path);
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        return dir.getPath();
    }

    /**
     * 파일 삭제 (from Disk)
     * @param files - 삭제할 파일 정보 List
     */
    public void deleteFiles(final List<AdoptFileResponse> files) {
        if (CollectionUtils.isEmpty(files)) {
            return;
        }
        for (AdoptFileResponse file : files) {
            deleteFilePath(file.getStoreName());
        }
    }

//    /**원래거 참고
//     * 파일 삭제 (from Disk)
//     * @param addPath - 추가 경로
//     * @param filename - 파일명
//     */
//    private void deleteFilePath(final String addPath, final String filename) {
//        String filePath = Paths.get(uploadPath, addPath, filename).toString();
//        deleteFile(filePath);
//    }
    /**
     * 파일 삭제 (from Disk)
     *
     * @param filename - 파일명
     */
    private void deleteFilePath(final String filename) {
        String filePath = Paths.get(uploadPath, filename).toString();
        deleteFile(filePath);
    }

    /**
     * 파일 삭제 (from Disk)
     * @param filePath - 파일 경로
     */
    private void deleteFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
