package com.teami.banham.repository;

import com.teami.banham.entity.ProudBoardEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProudBoardRepository extends JpaRepository<ProudBoardEntity,Long> {

    @Modifying // update, delete를 위한 어노테이션
    @Query(value = "update ProudBoardEntity b set b.hits=b.hits+1 where b.bno=:bno")
    void updateHits(@Param("bno") Long bno);

    @Query(value= "select pb from ProudBoardEntity pb where pb.delete_ck=0")
    Page<ProudBoardEntity> findAll(Pageable pageable);

    @Modifying
    @Query(value="update ProudBoardEntity b set b.delete_ck=1 where b.bno=:bno")
    void deleteById(Long bno);

    Optional<ProudBoardEntity> findByBno(Long bno);

    @Query("select b from ProudBoardEntity b where b.title like %:searchKeyword%")
    Page<ProudBoardEntity> findAllByTitle(Pageable pageable,@Param("searchKeyword") String searchKeyword);

    @Query("select b from ProudBoardEntity b where b.title like %:searchKeyword% or b.contents like %:searchKeyword%")
    Page<ProudBoardEntity> findAllByContents(Pageable pageable,@Param("searchKeyword") String searchKeyword);

    @Query("select b from ProudBoardEntity b where b.writer like %:searchKeyword%")
    Page<ProudBoardEntity> findAllbyWriter(Pageable pageable,@Param("searchKeyword") String searchKeyword);

    @Query("select b from ProudBoardEntity b where b.memberId=:memberId and b.delete_ck=0 order by b.bno desc")
    List<ProudBoardEntity> findAllList(@Param("memberId") String memberId);
}
