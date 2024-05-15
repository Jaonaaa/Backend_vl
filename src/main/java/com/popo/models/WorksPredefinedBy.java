package com.popo.models;

import jakarta.persistence.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
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
@Entity(name = "works_predefined_by_building_type")
@Table(name = "works_predefined_by_building_type")
public class WorksPredefinedBy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_building_type")
	public BuildingType buildingType;

	@Column(name = "code_travaux", unique = false)
	public String codeTravaux;

	@Column
	public String label;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_works_in_devis_details", nullable = true)
	public WorksInDevisDetails worksInDevisDetails;

	@ManyToOne
	@JoinColumn(name = "parent_works_predefined_by_building_type_id", nullable = true)
	@JsonIgnore
	public WorksPredefinedBy parentWorksPredefinedBy;

	@OneToMany(mappedBy = "parentWorksPredefinedBy")
	public List<WorksPredefinedBy> childWorksWorksPredefinedBy;

	@Transient
	Double pu;

	@Transient
	Double quantity;

	@Transient
	Unit unit;

	public WorksPredefinedBy() {
	}

	public WorksPredefinedBy(Long id, BuildingType buildingType, String codeTravaux, String label,
			WorksInDevisDetails worksInDevisDetails, WorksPredefinedBy parentWorksPredefinedBy,
			List<WorksPredefinedBy> childWorksWorksPredefinedBy, Double pu, Double quantity, Unit unit) {
		this.id = id;
		this.buildingType = buildingType;
		this.codeTravaux = codeTravaux;
		this.label = label;
		this.worksInDevisDetails = worksInDevisDetails;
		this.parentWorksPredefinedBy = parentWorksPredefinedBy;
		this.childWorksWorksPredefinedBy = childWorksWorksPredefinedBy;
		this.pu = pu;
		this.quantity = quantity;
		this.unit = unit;
	}

}
