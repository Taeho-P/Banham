package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.dto.adoptDTO.AdoptFileResponse;
import com.teami.banham.entity.adoptEntity.TbAdoptFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdoptFileService {


    private final AdoptFileMapper adoptFileMapper;

    @Transactional
    public void saveFiles (final Long boardId, final List<AdoptFileRequest> files){
        if(CollectionUtils.isEmpty(files)){
            return;
        }
        for(AdoptFileRequest file : files){
            file.setBoardId(boardId);
            System.out.println(file.getOriginalName());
        }
       adoptFileMapper.saveAll(files);

    }



    /**
     * 파일 리스트 조회
     * @param boardId - 게시글 번호 (FK)
     * @return 파일 리스트
     */

    public List<AdoptFileResponse> findAllFileByBoardId(final Long boardId) {
        System.out.println("file service start----------------");
        return adoptFileMapper.findAllFileByBoardId(boardId);
    }

    /**
     * 파일 리스트 조회
     * @param ids - PK 리스트
     * @return 파일 리스트
     */
    public List<AdoptFileResponse> findAllFileByIds(final List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return adoptFileMapper.findAllByIds(ids);
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
        adoptFileMapper.deleteAllByIds(ids);
    }
}
