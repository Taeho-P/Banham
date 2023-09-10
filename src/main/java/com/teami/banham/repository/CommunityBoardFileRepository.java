package com.teami.banham.repository;

import com.teami.banham.entity.CommunityBoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommunityBoardFileRepository extends JpaRepository<CommunityBoardFileEntity,Long> {

    //해당 게시글 파일 삭제
    @Modifying
    @Query("delete from CommunityBoardFileEntity cf WHERE  cf.communityBoardEntity.bno=:bno")
    void deleteCommunityBoardFileEntitiesByByBno(Long bno);

}
