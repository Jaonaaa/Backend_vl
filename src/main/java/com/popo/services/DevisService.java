package com.popo.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.popo.UserAuth.Repository.UserRepository;
import com.popo.models.BuildingFinition;
import com.popo.models.BuildingType;
import com.popo.models.Devis;
import com.popo.models.DevisSetDetails;
import com.popo.repository.BuildingFinitionRepository;
import com.popo.repository.BuildingTypeRepository;
import com.popo.repository.DevisRepository;
import com.popo.repository.DevisSetDetailsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DevisService {

    private final DevisRepository devisRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final BuildingFinitionRepository buildingFinitionRepository;
    private final UserRepository userRepository;
    private final WorkPredefinedByService workPredefinedByService;
    private final DevisSetDetailsRepository devisSetDetailsRepository;
    private final PayementService payementService;

    public java.util.List<Devis> getAllOfMyDevis(int id_user) {
        List<Devis> devis = devisRepository.getByIdUser(id_user);
        for (Devis dev : devis) {
            List<DevisSetDetails> details = devisSetDetailsRepository.findByDevis(dev);
            dev.setDevisSetDetails(details);
            dev.setNumero_user(dev.getClient().getNumero());
            // reste a payer
            Double totalPayed = payementService.getTotalPayement(dev);
            dev.setRestToPay(dev.getTotal_price() - totalPayed);
        }
        return devis;
    }

    public java.util.List<Devis> getAllDevis() {
        List<Devis> devis = devisRepository.findAll();
        for (Devis dev : devis) {
            List<DevisSetDetails> details = devisSetDetailsRepository.findByDevis(dev);
            dev.setDevisSetDetails(details);
            dev.setNumero_user(dev.getClient().getNumero());
            // reste a payer
            Double totalPayed = payementService.getTotalPayement(dev);
            dev.setRestToPay(dev.getTotal_price() - totalPayed);
        }
        return devis;
    }

    public Devis saveBuild(Devis devis) {
        devis.setCreation_date(Timestamp.from(Instant.now()));
        devis.setEnd_date(getEndingTime(devis));
        devis.setClient(userRepository.findById((long) devis.getId_user()).get());
        //
        BuildingFinition buildingFinition = buildingFinitionRepository.findById(devis.getBuildingFinition().getId())
                .get();
        BuildingType buildingType = buildingTypeRepository.findById(devis.getBuildingType().getId()).get();
        //
        // get total price
        Double amount_total = workPredefinedByService.amountTotal(buildingType, buildingFinition, devis);
        devis.setTotal_price(amount_total);
        // setting static report
        devis.setBuildingFinitionLabel(buildingFinition.getLabel());
        devis.setBuildingFinitionPercent(buildingFinition.getPercent());
        devis.setBuildingTypeLabel(buildingType.getLabel());
        devis.setBuildingTypeDuration(buildingType.getDuration());

        devis = devisRepository.save(devis);
        workPredefinedByService.registerDeviseDetails(devis);
        return devis;
    }

    public Devis saveBuildCustomDateCreated(Devis devis) {
        devis.setEnd_date(getEndingTime(devis));
        //
        BuildingFinition buildingFinition = buildingFinitionRepository.findById(devis.getBuildingFinition().getId())
                .get();
        BuildingType buildingType = buildingTypeRepository.findById(devis.getBuildingType().getId()).get();
        //
        // get total price
        Double amount_total = workPredefinedByService.amountTotal(buildingType, buildingFinition, devis);
        devis.setTotal_price(amount_total);
        // setting static report
        devis.setBuildingFinitionLabel(buildingFinition.getLabel());
        devis.setBuildingFinitionPercent(buildingFinition.getPercent());
        devis.setBuildingTypeLabel(buildingType.getLabel());
        devis.setBuildingTypeDuration(buildingType.getDuration());

        devis = devisRepository.save(devis);
        workPredefinedByService.registerDeviseDetails(devis);
        return devis;
    }

    public Timestamp getEndingTime(Devis devis) {
        BuildingType buildingType = buildingTypeRepository.findById(devis.getBuildingType().getId()).get();
        Double durationDay = buildingType.getDuration();
        LocalDateTime endingTime = devis.getBegin_date().toLocalDateTime()
                .plusDays(((long) durationDay.doubleValue()));
        return Timestamp.valueOf(endingTime);

    }
}
