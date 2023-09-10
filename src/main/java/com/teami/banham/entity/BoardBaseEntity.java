package com.teami.banham.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class BoardBaseEntity {

    //시간정보를 다루기 위해 따로 작성한 Entity
    @CreationTimestamp //생성시 시간정보
    @Column(updatable = false) //updatable = false 는 수정시에 이 컬럼이 관여 안하도록 설정
    private LocalDateTime createdTime;

    @UpdateTimestamp //수정시 시간정보
    @Column(insertable = false) //insertable = false 는 생성시에 이 컬럼이 관여 안하도록 설정
    private LocalDateTime updatedTime;
}
