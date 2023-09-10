package com.teami.banham.dto.adoptDTO.AdoptPaging;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AdoptPagingResponse<T> {

    private List<T> list = new ArrayList<>();
    private AdoptPagination adoptPagination;

    public AdoptPagingResponse(List<T> list, AdoptPagination adoptPagination) {
        this.list.addAll(list);
        this.adoptPagination = adoptPagination;
    }

}
