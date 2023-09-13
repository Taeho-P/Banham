package com.teami.banham.dto.MissingDTO.misPaging;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MisPagingResponse<T> {

    private List<T> list = new ArrayList<>();
    private MisPagination misPagination;

    public MisPagingResponse(List<T> list, MisPagination misPagination) {
        this.list.addAll(list);
        this.misPagination = misPagination;
    }

}
