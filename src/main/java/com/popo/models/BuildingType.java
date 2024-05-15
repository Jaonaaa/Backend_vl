package com.popo.models;

import jakarta.persistence.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "building_type")
@Table(name = "building_type")
public class BuildingType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column
	public String label;

	@Column
	public Double duration;

	@Transient
	List<String> descriptions;

	@Transient
	Double price;

	@Column(name = "surface")
	Double surface;

	@OneToMany(mappedBy = "buildingType")
	public List<BuildingTypeDescription> childDescriptions;

	public BuildingType() {
	}

	public BuildingType(Long id, String label, Double duration, List<String> descriptions, Double price, Double surface,
			List<BuildingTypeDescription> childDescriptions) {
		this.id = id;
		this.label = label;
		this.duration = duration;
		this.descriptions = descriptions;
		this.price = price;
		this.surface = surface;
		this.childDescriptions = childDescriptions;
	}

}
