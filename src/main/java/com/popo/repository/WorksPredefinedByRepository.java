package com.popo.repository;

import com.popo.models.BuildingType;
import com.popo.models.WorksPredefinedBy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WorksPredefinedByRepository extends JpaRepository<WorksPredefinedBy, Long> {

    List<WorksPredefinedBy> findAllBybuildingTypeAndParentWorksPredefinedByNotNull(BuildingType buildingType);

    List<WorksPredefinedBy> findAllBybuildingTypeAndParentWorksPredefinedByIsNull(BuildingType buildingType);

    List<WorksPredefinedBy> findAllBybuildingType(BuildingType buildingType);

}
