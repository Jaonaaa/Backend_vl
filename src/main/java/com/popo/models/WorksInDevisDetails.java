package com.popo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "works_in_devis_details")
@Table(name = "works_in_devis_details")
public class WorksInDevisDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_unit")
	public Unit unit;

	@Column
	public Double quantity;

	@Column
	public Double pu;

	public WorksInDevisDetails() {
	}

	public WorksInDevisDetails(Long id, Unit unit, Double quantity, Double pu) {
		this.id = id;
		this.unit = unit;
		this.quantity = quantity;
		this.pu = pu;
	}

}
