package com.popo.models.temp;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.springframework.context.annotation.Description;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.popo.models.BuildingType;
import com.popo.models.Unit;
import com.popo.models.WorksInDevisDetails;
import com.popo.models.WorksPredefinedBy;

import io.jsonwebtoken.lang.Arrays;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Entity(name = "maison_travaux")
@Table(name = "maison_travaux")
@Data
@Builder
public class MaisonTravaux {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "type_maison")
    @CsvBindByName(column = "type_maison")
    public String typeMaison;

    @Column(name = "description")
    @CsvBindByName(column = "description")
    public String description;

    @Column(name = "surface")
    public Double surface;

    @Column(name = "code_travaux")
    @CsvBindByName(column = "code_travaux")
    public String codeTravaux;

    @Column(name = "type_travaux")
    @CsvBindByName(column = "type_travaux")
    public String typeTravaux;

    @Column(name = "unite")
    @CsvBindByName(column = "unité")
    public String unite;

    @Column(name = "prix_unitaire")
    public Double pu;

    @Column(name = "quantite")
    public Double quantite;

    @Column(name = "duree_travaux")
    public Double durerTravaux;

    // String double
    @Transient
    @CsvBindByName(column = "surface")
    public String surfaceString;

    @Transient
    @CsvBindByName(column = "prix_unitaire")
    public String puString;

    @Transient
    @CsvBindByName(column = "quantite")
    public String quantiteString;

    @Transient
    @CsvBindByName(column = "duree_travaux")
    public String durerTravauxString;

    public MaisonTravaux(Long id, String typeMaison, String description, Double surface, String codeTravaux,
            String typeTravaux, String unite, Double pu, Double quantite, Double durerTravaux, String surfaceString,
            String puString, String quantiteString, String durerTravauxString) {
        this.id = id;
        this.typeMaison = typeMaison;
        this.description = description;
        this.surface = surface;
        this.codeTravaux = codeTravaux;
        this.typeTravaux = typeTravaux;
        this.unite = unite;
        this.pu = pu;
        this.quantite = quantite;
        this.durerTravaux = durerTravaux;
        this.surfaceString = surfaceString;
        this.puString = puString;
        this.quantiteString = quantiteString;
        this.durerTravauxString = durerTravauxString;
    }

    public void reOrganise() {
        this.surface = parseDouble(this.surfaceString);
        this.pu = parseDouble(this.puString);
        this.quantite = parseDouble(this.quantiteString);
        this.durerTravaux = parseDouble(this.durerTravauxString);
    }

    public void reOrganiseAll(List<MaisonTravaux> maisons) {
        for (MaisonTravaux maisonTravaux : maisons) {
            maisonTravaux.reOrganise();
        }
    }

    public static List<BuildingType> getListTypeMaisonDistinct(List<MaisonTravaux> maisons) {
        List<String> list = new Vector<String>();
        String separator = "---ooo--d-";
        List<BuildingType> typeList = new ArrayList<BuildingType>();
        for (MaisonTravaux maisonTravaux : maisons) {
            list.add(maisonTravaux.typeMaison.trim() + separator + maisonTravaux.description.trim() + separator +
                    maisonTravaux.surface + separator + maisonTravaux.durerTravaux);
        }
        list = removeDuplicatesUsingSet(list);
        for (String string : list) {
            String[] parts = string.split(separator);
            if (parts.length < 4) {
                continue;
            }
            // split description
            List<String> descriptions = Arrays.asList(parts[1].trim().split(","));
            BuildingType buildingType = BuildingType.builder().label(parts[0])
                    .descriptions(descriptions).surface(Double.parseDouble(parts[2]))
                    .duration(Double.parseDouble(parts[3]))
                    .build();
            typeList.add(buildingType);
        }
        return typeList;
    }

    public static List<WorksPredefinedBy> getListWorksPredefinedByDistinct(List<MaisonTravaux> maisons,
            List<Unit> units, List<BuildingType> buildingTypes) {
        List<String> list = new Vector<String>();
        String separator = "---ooo--d-";
        List<WorksPredefinedBy> typeList = new ArrayList<WorksPredefinedBy>();
        for (MaisonTravaux maisonTravaux : maisons) {
            list.add(maisonTravaux.codeTravaux.trim() + separator + maisonTravaux.typeTravaux.trim() + separator +
                    maisonTravaux.pu + separator + maisonTravaux.quantite + separator + maisonTravaux.unite.trim()
                    + separator +
                    maisonTravaux.typeMaison.trim());

        }
        list = removeDuplicatesUsingSet(list);
        for (String string : list) {
            String[] parts = string.split(separator);
            if (parts.length < 6) {
                continue;
            }

            WorksInDevisDetails worksInDevisDetails = WorksInDevisDetails.builder().pu(Double.parseDouble(parts[2]))
                    .quantity(Double.parseDouble(parts[3]))
                    .unit(searchUnit(parts[4], units))
                    .build();
            WorksPredefinedBy worksPredefinedBy = WorksPredefinedBy.builder().label(parts[1])
                    .codeTravaux(parts[0])
                    .worksInDevisDetails(worksInDevisDetails)
                    .buildingType(searchBuildType(parts[5], buildingTypes))
                    .build();
            typeList.add(worksPredefinedBy);
        }
        return typeList;
    }

    public static Unit searchUnit(String unitName, List<Unit> results) {
        for (Unit unit : results) {
            if (unit.getLabel().equals(unitName)) {
                return unit;
            }
        }
        return null;
    }

    public static BuildingType searchBuildType(String label_maison, List<BuildingType> results) {
        for (BuildingType buildingType : results) {
            if (buildingType.getLabel().equals(label_maison)) {
                return buildingType;
            }
        }
        return null;
    }

    public static List<Unit> getListUniteDistinct(List<MaisonTravaux> maisons) {
        List<String> list = new Vector<String>();
        List<Unit> typeList = new ArrayList<Unit>();

        for (MaisonTravaux maisonTravaux : maisons) {
            list.add(maisonTravaux.unite.trim());
        }
        list = removeDuplicatesUsingSet(list);
        for (String unit : list) {
            typeList.add(Unit.builder().label(unit).build());
        }
        return typeList;
    }

    public static List<String> removeDuplicatesUsingSet(List<String> list) {
        Set<String> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

    public Double parseDouble(String value) {
        String v = value.trim().replace(",", ".");
        return Double.parseDouble(v);
    }

    public MaisonTravaux() {
    }

    public static List<MaisonTravaux> getMaisonTravauxsFromCSV(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<MaisonTravaux> csvToBean = new CsvToBeanBuilder<MaisonTravaux>(reader)
                    .withType(MaisonTravaux.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<MaisonTravaux> m = csvToBean.parse();
            MaisonTravaux.builder().build().reOrganiseAll(m);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
