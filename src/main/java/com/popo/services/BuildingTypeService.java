package com.popo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.popo.models.BuildingType;
import com.popo.models.BuildingTypeDescription;
import com.popo.models.WorksPredefinedBy;
import com.popo.repository.BuildingTypeDescriptionRepository;
import com.popo.repository.BuildingTypeRepository;
import com.popo.repository.WorksPredefinedByRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildingTypeService {

    private final BuildingTypeRepository buildingTypeRepository;

    private final BuildingTypeDescriptionRepository buildingTypeDescriptionRepository;

    private final WorkPredefinedByService workPredefinedByService;

    public BuildingType saveBuild(BuildingType buildingType) {
        buildingType = buildingTypeRepository.save(buildingType);
        for (String description : buildingType.getDescriptions()) {
            BuildingTypeDescription buildingTypeDescription = BuildingTypeDescription.builder().description(description)
                    .buildingType(buildingType)
                    .build();
            buildingTypeDescriptionRepository.save(buildingTypeDescription);
        }
        return buildingType;

    }

    public List<BuildingType> getAll() {
        List<BuildingType> result = buildingTypeRepository.findAll();
        for (BuildingType buildingType : result) {
            Double price = workPredefinedByService.amountTotal(buildingType);
            buildingType.setPrice(price);
        }
        return result;
    }
}
