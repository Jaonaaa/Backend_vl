package com.popo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.popo.models.BuildingFinition;
import com.popo.models.BuildingType;
import com.popo.models.Devis;
import com.popo.models.DevisSetDetails;
import com.popo.models.WorksInDevisDetails;
import com.popo.models.WorksPredefinedBy;
import com.popo.repository.DevisSetDetailsRepository;
import com.popo.repository.WorksInDevisDetailsRepository;
import com.popo.repository.WorksPredefinedByRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkPredefinedByService {

    private final WorksPredefinedByRepository worksPredefinedByRepository;
    private final WorksInDevisDetailsRepository worksInDevisDetailsRepository;
    private final DevisSetDetailsRepository devisSetDetailsRepository;

    public List<WorksPredefinedBy> saveAll(List<WorksPredefinedBy> worksPredefinedBies, WorksPredefinedBy parent) {
        if (worksPredefinedBies != null)
            if (worksPredefinedBies.size() == 0)
                return worksPredefinedBies;

        for (WorksPredefinedBy worksPredefinedBy : worksPredefinedBies) {

            if (worksPredefinedBy.getWorksInDevisDetails() != null) {
                WorksInDevisDetails details = addDetails(worksPredefinedBy);
                worksPredefinedBy.setWorksInDevisDetails(details);
            }
            worksPredefinedBy.setParentWorksPredefinedBy(parent);

            // worksPredefinedBy.setCodeTravaux("TT");
            WorksPredefinedBy w = worksPredefinedByRepository.save(worksPredefinedBy);
            if (w.getChildWorksWorksPredefinedBy() != null)
                if (w.getChildWorksWorksPredefinedBy().size() > 0) {
                    saveAll(w.getChildWorksWorksPredefinedBy(), w);
                }
        }
        return (worksPredefinedBies);
    }

    public WorksInDevisDetails addDetails(WorksPredefinedBy worksPredefinedBy) {
        return worksInDevisDetailsRepository.save(worksPredefinedBy.getWorksInDevisDetails());
    }

    public Double amountTotal(BuildingType buildingType) {
        Double amout = 0.0;
        List<WorksPredefinedBy> works = worksPredefinedByRepository
                .findAllBybuildingType(buildingType);

        for (WorksPredefinedBy worksPredefinedBy : works) {
            WorksInDevisDetails details = worksPredefinedBy.getWorksInDevisDetails();
            if (details != null)
                amout += details.getPu() * details.getQuantity();
            // unit we don't know how to use it
        }
        return amout;
    }

    // Rehefa manan ID le Devis , rehefa voa save
    public void registerDeviseDetails(Devis devis) {
        List<WorksPredefinedBy> works = worksPredefinedByRepository
                .findAllBybuildingType(devis.getBuildingType());
        for (WorksPredefinedBy worksPredefinedBy : works) {
            rDD(devis, worksPredefinedBy, null);
        }
    }

    public void rDD(Devis devis, WorksPredefinedBy work, DevisSetDetails parent) {
        WorksInDevisDetails details = work.getWorksInDevisDetails();
        DevisSetDetails devisSetDetails = details != null
                ? DevisSetDetails.builder().devis(devis).work_label(work.getLabel())
                        .unit(details.getUnit().getLabel()).quantity(details.getQuantity()).pu(details.getPu())
                        .parentDevisSetDetails(parent).build()
                : DevisSetDetails.builder().devis(devis).work_label(work.getLabel())
                        .parentDevisSetDetails(parent).build();

        DevisSetDetails newD = devisSetDetailsRepository.save(devisSetDetails);
        if (work.getChildWorksWorksPredefinedBy() != null) {
            if (work.getChildWorksWorksPredefinedBy().size() > 0) {
                for (WorksPredefinedBy workPredefined : work.getChildWorksWorksPredefinedBy()) {
                    rDD(devis, workPredefined, newD);
                }
            }
        }
    }

    public Double amountTotal(BuildingType buildingType, BuildingFinition buildingFinition, Devis devis) {
        Double amount = amountTotal(buildingType);
        devis.setBuildingTypePrice(amount);
        // percent by buildingFinition
        Double percent_x = (buildingFinition.getPercent() * amount) / 100;
        return amount + percent_x;
    }

    public WorksPredefinedBy update(WorksPredefinedBy worksPredefinedBy) {

        WorksPredefinedBy wk = worksPredefinedByRepository.findById(worksPredefinedBy.getId()).get();
        wk.setLabel(worksPredefinedBy.getLabel());

        WorksInDevisDetails details = wk.getWorksInDevisDetails();
        details.setUnit(worksPredefinedBy.getUnit());
        details.setPu(worksPredefinedBy.getPu());
        details.setQuantity(worksPredefinedBy.getQuantity());
        worksInDevisDetailsRepository.save(details);

        return worksPredefinedByRepository.save(wk);
    }
}
