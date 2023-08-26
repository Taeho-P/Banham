package com.teami.banham.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class MemberBaseEntity { //회원가입일 컬럼을 위한 Entity클래스
    @CreationTimestamp //생성시 시간
    @Column(name = "regdate", updatable = false) //컬럼 이름 및 업데이트시 관여x
    private LocalDateTime regdate;
}
