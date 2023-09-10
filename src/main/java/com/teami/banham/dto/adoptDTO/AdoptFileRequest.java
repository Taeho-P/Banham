package com.teami.banham.dto.adoptDTO;

import com.teami.banham.entity.adoptEntity.TbAdoptFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AdoptFileRequest {

    private Long id;
    private Long boardId;
    private String originalName;
    private String storeName;
    private List<Long> removeFileIds = new ArrayList<>();

    @Builder
    public AdoptFileRequest(String originalName, String storeName){
        this.originalName = originalName;
        this.storeName = storeName;
    }

    public void setBoardId(Long boardId){
        this.boardId = boardId;
    }




}
