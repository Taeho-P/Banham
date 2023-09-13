package com.teami.banham.entity.MissingEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MissingRepository extends JpaRepository<TbMissingBoard, Long> { //pk에 해당하는 데이터 타입(long)


}
