package com.teami.banham.repository;

import com.teami.banham.entity.NoticeBoardEntity;
import org.springframework.data.jpa.domain.Specification;


public class NoticeBoardSpecification {

    public static Specification<NoticeBoardEntity> equalIsDelete(String isDelete) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDelete"), isDelete);
    }

}
