package com.popo.models.temp;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.popo.models.BuildingFinition;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Entity(name = "devis_import")
@Table(name = "devis_import")
@Data
@Builder
public class DevisImport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "client")
    @CsvBindByName(column = "client")
    public String client;

    @Column(name = "ref_devis")
    @CsvBindByName(column = "ref_devis")
    public String refDevis;

    @Column(name = "type_maison")
    @CsvBindByName(column = "type_maison")
    public String typeMaison;

    @Column(name = "finition")
    @CsvBindByName(column = "finition")
    public String finition;

    @Column(name = "taux_finition")
    public Double tauxFinition;

    @Column(name = "date_devis")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    public Timestamp dateDevis;

    @Column(name = "date_debut")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    public Timestamp dateDebut;

    @Column(name = "lieu")
    @CsvBindByName(column = "lieu")
    public String lieu;

    ///
    @Transient
    @CsvBindByName(column = "date_devis")
    public String dateDevisString;

    @Transient
    @CsvBindByName(column = "date_debut")
    public String dateDebutString;

    @Transient
    @CsvBindByName(column = "taux_finition")
    public String tauxFinitionString;

    public DevisImport() {
    }

    public DevisImport(Long id, String client, String refDevis, String typeMaison, String finition, Double tauxFinition,
            Timestamp dateDevis, Timestamp dateDebut, String lieu, String dateDevisString, String dateDebutString,
            String tauxFinitionString) {
        this.id = id;
        this.client = client;
        this.refDevis = refDevis;
        this.typeMaison = typeMaison;
        this.finition = finition;
        this.tauxFinition = tauxFinition;
        this.dateDevis = dateDevis;
        this.dateDebut = dateDebut;
        this.lieu = lieu;
        this.dateDevisString = dateDevisString;
        this.dateDebutString = dateDebutString;
        this.tauxFinitionString = tauxFinitionString;
    }

    public void reOrganise() {
        this.tauxFinition = parseDouble(this.tauxFinitionString);
        this.dateDevis = parseTimeStamp(this.dateDevisString);
        this.dateDebut = parseTimeStamp(this.dateDebutString);

    }

    public Timestamp parseTimeStamp(String value) {
        return convertStringToTimestamp(value);
    }

    public Double parseDouble(String value) {
        String v = value.trim().replace("%", "").replace(",", ".");
        return Double.parseDouble(v);
    }

    public void reOrganiseAll(List<DevisImport> devisImp) {
        for (DevisImport DevisImport : devisImp) {
            DevisImport.reOrganise();
        }
    }

    public static Timestamp convertStringToTimestamp(String dateString) {
        dateString = formatDate(dateString);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        return Timestamp.from(instant);
    }

    public static String formatDate(String dateString) {
        // Split the input date string by '/'
        String[] parts = dateString.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        String year = parts[2];

        // Format day and month to have two digits
        String formattedDay = String.format("%02d", day);
        String formattedMonth = String.format("%02d", month);

        // Combine formatted parts into the final string
        return formattedDay + "/" + formattedMonth + "/" + year;
    }

    public static List<DevisImport> getDevisImportsFromCSV(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<DevisImport> csvToBean = new CsvToBeanBuilder<DevisImport>(reader)
                    .withType(DevisImport.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<DevisImport> m = csvToBean.parse();
            DevisImport.builder().build().reOrganiseAll(m);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static List<String> getListClientDistinct(List<DevisImport> devisImports) {
        List<String> list = new Vector<String>();
        for (DevisImport devisImport : devisImports) {
            list.add(devisImport.client.trim());
        }
        list = removeDuplicatesUsingSet(list);
        return list;
    }

    public static List<BuildingFinition> getListFinitionDistinct(List<DevisImport> devisImports) {
        List<String> list = new Vector<String>();
        String separator = "---ooo--d-";
        for (DevisImport devisImport : devisImports) {
            list.add(devisImport.finition.trim() + separator + devisImport.getTauxFinition());
        }
        list = removeDuplicatesUsingSet(list);
        List<BuildingFinition> listFinition = new Vector<BuildingFinition>();

        for (String row : list) {
            String[] parts = row.split(separator);
            if (parts.length < 2) {
                continue;
            }
            BuildingFinition buildingFinition = BuildingFinition.builder().label(parts[0])
                    .percent(Double.parseDouble(parts[1])).build();
            listFinition.add(buildingFinition);
        }
        return listFinition;
    }

    public static List<String> getListLieuDistinct(List<DevisImport> devisImports) {
        List<String> list = new Vector<String>();
        for (DevisImport devisImport : devisImports) {
            list.add(devisImport.lieu.trim());
        }
        list = removeDuplicatesUsingSet(list);
        return list;
    }

    public static List<String> removeDuplicatesUsingSet(List<String> list) {
        Set<String> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }

}
