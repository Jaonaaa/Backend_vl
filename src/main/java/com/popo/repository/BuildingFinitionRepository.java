package com.popo.repository;

import com.popo.models.BuildingFinition;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingFinitionRepository extends JpaRepository<BuildingFinition, Long> {

    BuildingFinition findByLabel(String label);

}
