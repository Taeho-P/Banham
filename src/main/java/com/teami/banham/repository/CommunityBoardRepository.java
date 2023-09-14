package com.teami.banham.repository;

import com.teami.banham.entity.CommunityBoardEntity;
import com.teami.banham.entity.ProudBoardEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
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

    @Query("select b from CommunityBoardEntity b where b.memberId=:memberId and b.delete_ck=0 order by b.bno desc")
    List<CommunityBoardEntity> findAllList(@Param("memberId")String memberId);

    @Query("select b from CommunityBoardEntity b where b.title like %:searchKeyword% and b.delete_ck=0")
    Page<CommunityBoardEntity> findAllByTitle(Pageable pageable,@Param("searchKeyword") String searchKeyword);

    @Query("select b from CommunityBoardEntity b where b.title like %:searchKeyword% or b.contents like %:searchKeyword% and b.delete_ck=0")
    Page<CommunityBoardEntity> findAllByContents(Pageable pageable,@Param("searchKeyword") String searchKeyword);

    @Query("select b from CommunityBoardEntity b where b.writer like %:searchKeyword% and b.delete_ck=0")
    Page<CommunityBoardEntity> findallbyWriter(Pageable pageable, @Param("searchKeyword") String searchKeyword);

}
