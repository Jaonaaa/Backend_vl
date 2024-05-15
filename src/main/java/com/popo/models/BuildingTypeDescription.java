package com.popo.models;

import jakarta.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "building_type_description")
@Table(name = "building_type_description")
public class BuildingTypeDescription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	public Long id;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_building_type")
	@JsonIgnore
	public BuildingType buildingType;

	@Column
	public String description;

	public BuildingTypeDescription() {
	}

	public BuildingTypeDescription(Long id, BuildingType buildingType, String description) {
		this.id = id;
		this.buildingType = buildingType;
		this.description = description;
	}

}
