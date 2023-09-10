package com.teami.banham.repository;

import com.teami.banham.entity.CommunityBoardEntity;
import com.teami.banham.entity.CommunityCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommunityCommentRepository extends JpaRepository<CommunityCommentEntity,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CommunityCommentEntity c WHERE c.cno = :cno")
    void deleteById(Long cno);

    @Query("select c from CommunityCommentEntity c where c.delete_ck=0 and c.communityBoardEntity=:communityBoardEntity order by c.cno desc")
    List<CommunityCommentEntity> findAllByCommunityBoardEntityOrderByCnoDesc(CommunityBoardEntity communityBoardEntity);


    @Query("select c from CommunityCommentEntity c where c.cno=:cno and c.delete_ck=0")
    Optional<CommunityCommentEntity> findById(Long cno);
}
