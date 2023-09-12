package com.teami.banham.repository;

import com.teami.banham.entity.EditorBoardEntity;
import org.springframework.data.jpa.domain.Specification;


public class EditorBoardSpecification {

    public static Specification<EditorBoardEntity> equalIsDelete(String isDelete) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isDelete"), isDelete);
    }

    public static Specification<EditorBoardEntity> equalWriterMno(Long writerMno) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("writerMno"), writerMno);
    }
}
