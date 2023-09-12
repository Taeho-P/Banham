package com.teami.banham.repository;

import com.teami.banham.entity.ProudBoardEntity;
import com.teami.banham.entity.ProudCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProudCommentRepository extends JpaRepository<ProudCommentEntity,Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM ProudCommentEntity c WHERE c.cno = :cno")
    void deleteById(Long cno);

    List<ProudCommentEntity> findAllByProudBoardEntityOrderByCnoDesc(ProudBoardEntity proudBoardEntity);


    @Query("select c from ProudCommentEntity c where c.cno=:cno and c.delete_ck=0")
    Optional<ProudCommentEntity> findById(Long cno);

    @Modifying
    @Query(value="update ProudCommentEntity c set c.delete_ck=1 where c.proudBoardEntity.bno=:bno")
    void deleteByDelete_ck(Long bno);

    @Query("select c from ProudCommentEntity c inner join MemberEntity m on c.memberId=m.memberId where m.memberId=:memberId order by c.cno desc")
    List<ProudCommentEntity> findByCommentMemberId(String memberId);
}
