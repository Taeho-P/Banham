package com.teami.banham.repository.localPointRepository;

import com.teami.banham.entity.localPointEntity.ServiceEntity;
import com.teami.banham.entity.localPointEntity.ShoppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingRepository extends JpaRepository<ShoppingEntity,Long> {
}
