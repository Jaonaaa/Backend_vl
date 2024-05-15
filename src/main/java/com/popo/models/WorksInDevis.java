package com.popo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "works_in_devis")
@Table(name = "works_in_devis")
public class WorksInDevis {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_devis")
	public Devis devis;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_works")
	public Works works;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_works_in_devis")
	public WorksInDevis worksInDevis;

}
