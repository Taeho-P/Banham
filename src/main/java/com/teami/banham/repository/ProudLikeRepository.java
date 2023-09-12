package com.teami.banham.repository;

import com.teami.banham.entity.ProudLikeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProudLikeRepository extends JpaRepository<ProudLikeEntity,Long> {

    @Query("select count(l) from ProudLikeEntity l where l.proudBoardEntity.bno=:bno")
    Long findAllCount(@Param("bno") Long bno);

    @Query("select l from ProudLikeEntity l where l.proudBoardEntity.bno=:bno and l.memberId=:memberId")
    Optional<ProudLikeEntity> findByMemberId(@Param("bno")Long bno,@Param("memberId") String memberId);

    @Modifying
    @Query("delete from ProudLikeEntity l where l.proudBoardEntity.bno=:bno and l.memberId=:memberId")
    void deleteByLike(Long bno, String memberId);
}
