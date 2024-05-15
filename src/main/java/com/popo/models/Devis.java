package com.popo.models;

import java.sql.Timestamp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.popo.UserAuth.Models.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "devis")
@Table(name = "devis")
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "id_client")
    @JsonIgnore
    public User client;

    @Transient
    public int id_user;

    @Transient
    public String numero_user;

    @ManyToOne
    @JoinColumn(name = "id_lieu")
    public Lieu lieu;

    @ManyToOne
    @JoinColumn(name = "id_building_type")
    public BuildingType buildingType;

    @ManyToOne
    @JoinColumn(name = "id_building_finition")
    public BuildingFinition buildingFinition;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    Timestamp creation_date;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    Timestamp begin_date;

    @Column
    Double total_price;

    @Column(name = "ref_devis", unique = true)
    String refDevis;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
    Timestamp end_date;

    @Transient
    String time_label;

    @Transient
    Double restToPay;

    // @jakarta.persistence.ManyToOne
    // @jakarta.persistence.JoinColumn(name = "id_finition_by_building_type")
    // public FinitionByBuildingType finitionByBuildingType;

    @Column(name = "taux_finition")
    Double tauxFinition;

    @Column(name = "building_finition_label")
    String buildingFinitionLabel;

    @Column(name = "building_finition_percent")
    Double buildingFinitionPercent;

    @Column(name = "building_type_label")
    String buildingTypeLabel;

    @Column(name = "building_type_duration")
    Double buildingTypeDuration;

    @Column(name = "building_type_price")
    Double buildingTypePrice;

    //
    @Transient
    List<DevisSetDetails> devisSetDetails;

    public Devis() {
    }

    public Devis(Long id, User client, int id_user, String numero_user, Lieu lieu, BuildingType buildingType,
            BuildingFinition buildingFinition, Timestamp creation_date, Timestamp begin_date, Double total_price,
            String refDevis, Timestamp end_date, String time_label, Double restToPay, Double tauxFinition,
            String buildingFinitionLabel, Double buildingFinitionPercent, String buildingTypeLabel,
            Double buildingTypeDuration, Double buildingTypePrice, List<DevisSetDetails> devisSetDetails) {
        this.id = id;
        this.client = client;
        this.id_user = id_user;
        this.numero_user = numero_user;
        this.lieu = lieu;
        this.buildingType = buildingType;
        this.buildingFinition = buildingFinition;
        this.creation_date = creation_date;
        this.begin_date = begin_date;
        this.total_price = total_price;
        this.refDevis = refDevis;
        this.end_date = end_date;
        this.time_label = time_label;
        this.restToPay = restToPay;
        this.tauxFinition = tauxFinition;
        this.buildingFinitionLabel = buildingFinitionLabel;
        this.buildingFinitionPercent = buildingFinitionPercent;
        this.buildingTypeLabel = buildingTypeLabel;
        this.buildingTypeDuration = buildingTypeDuration;
        this.buildingTypePrice = buildingTypePrice;
        this.devisSetDetails = devisSetDetails;
    }

}
