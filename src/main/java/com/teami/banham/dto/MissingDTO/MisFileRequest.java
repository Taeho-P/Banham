package com.teami.banham.dto.MissingDTO;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MisFileRequest {

    private Long id;
    private Long boardId;
    private String originalName;
    private String storeName;
    private List<Long> removeFileIds = new ArrayList<>();

    @Builder
    public MisFileRequest(String originalName, String storeName){
        this.originalName = originalName;
        this.storeName = storeName;
    }

    public void setBoardId(Long boardId){
        this.boardId = boardId;
    }




}
