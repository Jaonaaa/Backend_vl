package com.popo.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "devis_set_details")
@Table(name = "devis_set_details")
public class DevisSetDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "id_devis")
    @JsonIgnore
    public Devis devis;

    @Transient
    int id_devis;

    @Column(nullable = true)
    String work_label;

    @Column(nullable = true)
    String unit;

    @Column(nullable = true)
    Double quantity;

    @Column(nullable = true)
    Double pu;

    @ManyToOne
    @JoinColumn(name = "parent_devis_set_details_id")
    @JsonIgnore
    public DevisSetDetails parentDevisSetDetails;

    @OneToMany(mappedBy = "parentDevisSetDetails")
    public List<DevisSetDetails> childDevisSetDetails;

    public DevisSetDetails() {
    }

    public DevisSetDetails(Long id, Devis devis, int id_devis, String work_label, String unit, Double quantity,
            Double pu, DevisSetDetails parentDevisSetDetails, List<DevisSetDetails> childDevisSetDetails) {
        this.id = id;
        this.devis = devis;
        this.id_devis = id_devis;
        this.work_label = work_label;
        this.unit = unit;
        this.quantity = quantity;
        this.pu = pu;
        this.parentDevisSetDetails = parentDevisSetDetails;
        this.childDevisSetDetails = childDevisSetDetails;
    }

}
