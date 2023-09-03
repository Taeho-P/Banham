package com.teami.banham.repository;

import com.teami.banham.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

   Optional<MemberEntity> findByMemberId(String memberId);

   Optional<MemberEntity> findByMemberNick(String memberNick);

   Optional<MemberEntity> findByMemberMail(String memberMail);

    Optional<MemberEntity> findByMemberIdAndMemberMail(String memberId, String memberMail);
}
