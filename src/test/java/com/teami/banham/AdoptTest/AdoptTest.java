package com.teami.banham.AdoptTest;

import com.teami.banham.entity.adoptEntity.TbAdoptBoard;
import com.teami.banham.entity.adoptEntity.AdoptRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdoptTest {

    @Autowired
    AdoptRepository adoptRepository;

    @Test
    void save() {
        // 1. 게시글 파라미터 생성
        TbAdoptBoard params = TbAdoptBoard.builder()
                .title("1번 게시글 제목")
                .content("1번 게시글 내용")
                .writer("나")
                .hits(0)
                .deleteYn(0)
                .build();

        // 2. 게시글 저장
        adoptRepository.save(params);
    }

}
