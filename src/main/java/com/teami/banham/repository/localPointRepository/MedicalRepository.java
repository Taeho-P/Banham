package com.teami.banham.repository.localPointRepository;

import com.teami.banham.entity.localPointEntity.MedicalEntity;
import com.teami.banham.entity.localPointEntity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRepository extends JpaRepository<MedicalEntity,Long> {
}
