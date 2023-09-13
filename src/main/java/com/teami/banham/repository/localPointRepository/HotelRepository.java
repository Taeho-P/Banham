package com.teami.banham.repository.localPointRepository;

import com.teami.banham.entity.localPointEntity.HotelEntity;
import com.teami.banham.entity.localPointEntity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<HotelEntity,Long> {
}
