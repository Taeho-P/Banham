package com.teami.banham.service.MissingService;

import com.teami.banham.dto.MissingDTO.MisFileRequest;
import com.teami.banham.dto.MissingDTO.MisFileResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MisFileService {


    private final MisFileMapper misFileMapper;

    @Transactional
    public void saveFiles (final Long boardId, final List<MisFileRequest> files){
        if(CollectionUtils.isEmpty(files)){
            return;
        }
        for(MisFileRequest file : files){
            file.setBoardId(boardId);
            System.out.println(file.getOriginalName());
        }
       misFileMapper.saveAll(files);

    }

    public List<MisFileResponse> findAll (){
       return misFileMapper.findAll();
    }


    /**
     * 파일 리스트 조회
     * @param boardId - 게시글 번호 (FK)
     * @return 파일 리스트
     */

    public List<MisFileResponse> findAllFileByBoardId(final Long boardId) {
        System.out.println("file service start----------------");
        return misFileMapper.findAllFileByBoardId(boardId);
    }

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */
    public List<MisFileResponse> findAllFileByIds(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return misFileMapper.findAllByIds(ids);
    }

    /**
     * 파일 삭제 (from Database)
     * @param ids - PK 리스트
     */
    @Transactional
    public void deleteAllFileByIds(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        misFileMapper.deleteAllByIds(ids);
    }


    /**
     * 파일 상세정보 조회
     * @param id - PK
     * @return 파일 상세정보
     */
    public MisFileResponse findFileById(final Long id) {
        return misFileMapper.findById(id);
    }
}
