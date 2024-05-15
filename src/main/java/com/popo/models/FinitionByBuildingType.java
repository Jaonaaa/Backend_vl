package com.popo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity(name = "finition_by_building_type")
@Table(name = "finition_by_building_type")
@Data
@Builder
public class FinitionByBuildingType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_building_type")
	public BuildingType buildingType;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_building_finition")
	public BuildingFinition buildingFinition;

	@Column
	public Double percent;

}
