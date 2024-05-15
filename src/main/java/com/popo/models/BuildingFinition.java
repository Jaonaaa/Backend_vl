package com.popo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Entity(name = "building_finition")
@Table(name = "building_finition")
@Data
@Builder
public class BuildingFinition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column
	public String label;

	// @Transient
	Double percent;

	public BuildingFinition() {
	}

	public BuildingFinition(Long id, String label, Double percent) {
		this.id = id;
		this.label = label;
		this.percent = percent;
	}

}
