package com.popo.services;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import org.springframework.stereotype.Service;

import com.popo.UserAuth.Models.User;
import com.popo.UserAuth.Repository.UserRepository;
import com.popo.UserAuth.Service.AuthService;
import com.popo.models.BuildingFinition;
import com.popo.models.BuildingType;
import com.popo.models.Devis;
import com.popo.models.Lieu;
import com.popo.models.Payement;
import com.popo.models.Unit;
import com.popo.models.WorksPredefinedBy;
import com.popo.models.temp.DevisImport;
import com.popo.models.temp.MaisonTravaux;
import com.popo.models.temp.PaiementImport;
import com.popo.repository.BuildingFinitionRepository;
import com.popo.repository.BuildingTypeRepository;
import com.popo.repository.DevisRepository;
import com.popo.repository.LieuRepository;
import com.popo.repository.PayementRepository;
import com.popo.repository.UnitRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final BuildingTypeService buildingTypeService;
    private final UnitRepository unitRepository;
    private final WorkPredefinedByService workPredefinedByService;
    private final AuthService authService;
    private final UserRepository userRepository;
    private final BuildingFinitionService buildingFinitionService;
    private final LieuRepository lieuRepository;
    private final DevisService devisService;
    private final BuildingTypeRepository buildingTypeRepository;
    private final BuildingFinitionRepository buildingFinitionRepository;
    private final DevisRepository devisRepository;
    private final PayementRepository payementRepository;

    @Transactional
    public void importDevisAndMaisonsTravaux(List<MaisonTravaux> maisonTravauxs, List<DevisImport> devisImports) {
        saveMaisonTravaux(maisonTravauxs);
        saveDevis(devisImports);
    }

    public void saveMaisonTravaux(List<MaisonTravaux> maisonTravauxs) {
        List<BuildingType> buildingTypes = MaisonTravaux.getListTypeMaisonDistinct(maisonTravauxs);
        buildingTypes = saveBuildType(buildingTypes);
        List<Unit> units = MaisonTravaux.getListUniteDistinct(maisonTravauxs);
        saveUnits(units);
        //
        units = unitRepository.findAll();
        //
        List<WorksPredefinedBy> worksPredefinedBy = MaisonTravaux.getListWorksPredefinedByDistinct(maisonTravauxs,
                units, buildingTypes);
        workPredefinedByService.saveAll(worksPredefinedBy, null);
        //
    }

    public void saveDevis(List<DevisImport> devisImports) {

        List<String> numeros = DevisImport.getListClientDistinct(devisImports);
        List<User> clients = saveClients(numeros);
        List<BuildingFinition> finitions = DevisImport.getListFinitionDistinct(devisImports);
        finitions = saveFinitions(finitions);
        List<String> lieux_name = DevisImport.getListLieuDistinct(devisImports);
        saveLieux(lieux_name);
        //

        for (DevisImport devisImport : devisImports) {

            //
            User client = userRepository.findByNumero(devisImport.getClient().trim()).get();
            BuildingType buildingType = buildingTypeRepository.findByLabel(devisImport.getTypeMaison().trim());
            BuildingFinition buildingFinition = buildingFinitionRepository
                    .findByLabel(devisImport.getFinition().trim());
            Lieu lieu = lieuRepository.findByLabel(devisImport.getLieu().trim());
            //

            Devis newDevis = Devis.builder()
                    .client(client).refDevis(devisImport.getRefDevis().trim())
                    .buildingType(buildingType)
                    .buildingFinition(buildingFinition).creation_date(devisImport.getDateDevis())
                    .begin_date(devisImport.getDateDebut())
                    .lieu(lieu)
                    .build();

            devisService.saveBuildCustomDateCreated(newDevis);
        }
        //

    }

    @Transactional
    public void importPaiement(List<PaiementImport> paiementImports) {
        for (PaiementImport paiementImport : paiementImports) {

            if (paiementImport.getRefPaiement() != null) {
                Optional<Payement> paiementCheck = payementRepository
                        .findByRefPaiement(paiementImport.getRefPaiement().trim());

                if (paiementCheck.isPresent()) {
                    System.out.println("Payement already there ref" + paiementCheck.get().getRefPaiement() + "\n");
                    continue;
                }
            }

            Devis devis = devisRepository.findByRefDevis(paiementImport.getRefDevis().trim());
            Payement payement = Payement.builder().amount(paiementImport.getMontant()).client(devis.getClient())
                    .refPaiement(paiementImport.getRefPaiement())
                    .payement_time(paiementImport.getDatePaiement()).devis(devis).build();
            payementRepository.save(payement);
        }
    }

    public List<BuildingType> saveBuildType(List<BuildingType> buildingTypes) {
        List<BuildingType> buildingTypesss = new Vector<>();

        for (BuildingType buildingType : buildingTypes) {
            BuildingType b = buildingTypeService.saveBuild(buildingType);
            buildingTypesss.add(b);
        }
        return buildingTypesss;
    }

    public List<Unit> saveUnits(List<Unit> Units) {
        List<Unit> units = new Vector<>();
        for (Unit Unit : Units) {
            Unit unit2 = unitRepository.save(Unit);
            units.add(unit2);
        }
        return units;
    }

    public List<User> saveClients(List<String> rows) {
        List<User> rows_news = new Vector<>();
        for (String row : rows) {
            User res = authService.saveUserClient(row);
            rows_news.add(res);
        }
        return rows_news;
    }

    public List<Lieu> saveLieux(List<String> rows) {
        List<Lieu> rows_news = new Vector<>();
        for (String row : rows) {
            Lieu lieu = Lieu.builder().label(row).build();
            Lieu res = lieuRepository.save(lieu);
            rows_news.add(res);
        }
        return rows_news;
    }

    public List<BuildingFinition> saveFinitions(List<BuildingFinition> rows) {
        List<BuildingFinition> rows_news = new Vector<>();
        for (BuildingFinition row : rows) {
            BuildingFinition res = buildingFinitionService.saveBuild(row);
            rows_news.add(res);
        }
        return rows_news;
    }

}
