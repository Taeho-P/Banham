package com.teami.banham.repository;


import com.teami.banham.entity.EditorBoardEntity;
import com.teami.banham.entity.NoticeBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


//JpaRepository를 상속받을때 규칙 <1, 2> 1 = 다룰 entity 클래스 , 2 = pk(기본키)의 타입
public interface EditorBoardRepository extends JpaRepository<EditorBoardEntity, Long>, JpaSpecificationExecutor<EditorBoardEntity> {

    Optional<EditorBoardEntity> findByBno(Long bno); //게시글 고유번호를 통해 특정 게시글을 조회해주는 메소드


    Page<EditorBoardEntity> findAll(Specification<EditorBoardEntity> spec, Pageable pageable);

    @Modifying //update나 delete등을 실행할때 필수로 붙여줘야하는 어노테이션
    @Query(value = "UPDATE EditorBoardEntity b SET b.boardHits=b.boardHits+1 WHERE b.bno=:bno") //Entity기준으로 쿼리문을 작성할수 있다.
    void updateHits(@Param("bno") Long bno);  //@Query 쿼리문 안에 WHERE b.bno=:bno 라고 되어있으면 키값이 bno인 파라미터 값을 대입하게 된다

    @Modifying
    @Query(value = "UPDATE EditorBoardEntity b SET b.isDelete='Y' WHERE b.bno=:bno")
    void updateIsDelete(@Param("bno") Long bno);

    @Modifying
    @Query(value = "SELECT c.* FROM (SELECT * FROM editor_board b WHERE b.IS_DELETE='N' ORDER BY b.CREATED_TIME DESC) c WHERE rownum<=5",nativeQuery = true)
    List<EditorBoardEntity> findIndexEditor();
}
