package com.popo.models.temp;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Entity(name = "paiement_import")
@Table(name = "paiement_import")
@Data
@Builder
public class PaiementImport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "ref_devis")
    @CsvBindByName(column = "ref_devis")
    public String refDevis;

    @Column(name = "ref_paiement")
    @CsvBindByName(column = "ref_paiement")
    public String refPaiement;

    @Column(name = "date_paiement")
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    public Timestamp datePaiement;

    @Column(name = "montant")
    public Double montant;

    @Transient
    @CsvBindByName(column = "date_paiement")
    String datePaiementString;

    @Transient
    @CsvBindByName(column = "montant")
    String montantString;

    public PaiementImport() {
    }

    public PaiementImport(Long id, String refDevis, String refPaiement, Timestamp datePaiement, Double montant,
            String datePaiementString, String montantString) {
        this.id = id;
        this.refDevis = refDevis;
        this.refPaiement = refPaiement;
        this.datePaiement = datePaiement;
        this.montant = montant;
        this.datePaiementString = datePaiementString;
        this.montantString = montantString;
    }

    public static List<PaiementImport> getPaiementImportsFromCSV(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            CsvToBean<PaiementImport> csvToBean = new CsvToBeanBuilder<PaiementImport>(reader)
                    .withType(PaiementImport.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<PaiementImport> m = csvToBean.parse();
            PaiementImport.builder().build().reOrganiseAll(m);
            return m;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void reOrganise() {
        this.montant = parseDouble(this.montantString);
        this.datePaiement = parseTimeStamp(this.datePaiementString);

    }

    public Timestamp parseTimeStamp(String value) {
        return convertStringToTimestamp(value);
    }

    public Double parseDouble(String value) {
        String v = value.trim().replace(",", ".");
        return Double.parseDouble(v);
    }

    public void reOrganiseAll(List<PaiementImport> paiementImports) {
        for (PaiementImport PaiementImport : paiementImports) {
            PaiementImport.reOrganise();
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

}
