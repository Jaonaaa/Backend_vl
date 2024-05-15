package com.popo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.popo.models.BuildingFinition;
import com.popo.models.BuildingType;
import com.popo.models.FinitionByBuildingType;
import com.popo.repository.BuildingFinitionRepository;
import com.popo.repository.BuildingTypeRepository;
import com.popo.repository.FinitionByBuildingTypeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildingFinitionService {

    private final BuildingFinitionRepository buildingFinitionRepository;
    private final FinitionByBuildingTypeRepository finitionByBuildingTypeRepository;
    private final BuildingTypeRepository buildingTypeRepository;

    public BuildingFinition saveBuild(BuildingFinition buildingFinition) {

        buildingFinition = buildingFinitionRepository.save(buildingFinition);
        // List<BuildingType> buildingTypes = buildingTypeRepository.findAll();
        // for (BuildingType buildingType : buildingTypes) {
        // FinitionByBuildingType finitionByBuildingType =
        // FinitionByBuildingType.builder()
        // .percent(buildingFinition.getPercent()).buildingType(buildingType)
        // .buildingFinition(buildingFinition).build();
        // finitionByBuildingTypeRepository.save(finitionByBuildingType);
        // }
        return buildingFinition;
    }
}
