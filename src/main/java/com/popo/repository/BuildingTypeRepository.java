package com.popo.repository;

import com.popo.models.BuildingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingTypeRepository extends JpaRepository<BuildingType, Long> {

    BuildingType findByLabel(String label);
}
