package com.teami.banham.repository.localPointRepository;

import com.teami.banham.entity.localPointEntity.FoodEntity;
import com.teami.banham.entity.localPointEntity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<FoodEntity,Long> {
}
