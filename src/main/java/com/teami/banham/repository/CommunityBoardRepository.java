package com.teami.banham.repository;

import com.teami.banham.entity.CommunityBoardEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommunityBoardRepository extends JpaRepository<CommunityBoardEntity,Long> {

    @Modifying // update, delete를 위한 어노테이션
    @Query(value = "update CommunityBoardEntity c set c.hits=c.hits+1 where c.bno=:bno")
    void updateHits(@Param("bno") Long bno);

    @Query(value= "select c from CommunityBoardEntity c where c.delete_ck=0")
    Page<CommunityBoardEntity> findAll(Pageable pageable);

    @Modifying
    @Query(value="update CommunityBoardEntity c set c.delete_ck=1 where c.bno=:bno")
    void deleteById(Long bno);

    Optional<CommunityBoardEntity> findByBno(Long bno);
}
