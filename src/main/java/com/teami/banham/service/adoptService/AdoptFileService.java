package com.teami.banham.service.adoptService;

import com.teami.banham.dto.adoptDTO.AdoptFileRequest;
import com.teami.banham.entity.adoptEntity.TbAdoptFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
}
