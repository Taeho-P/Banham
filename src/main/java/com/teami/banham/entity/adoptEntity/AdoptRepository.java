package com.teami.banham.entity.adoptEntity;

import com.teami.banham.service.adoptService.AdoptService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptRepository extends JpaRepository<TbAdoptBoard, Long> { //pk에 해당하는 데이터 타입(long)


}
