package com.teami.banham.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BoardBaseEntity {

    @CreationTimestamp //게시글 생성시 타임 스탬프
    @Column(updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp //게시글 수정시 타임스탬프
    @Column(insertable = false)
    private LocalDateTime updateDate;
}
