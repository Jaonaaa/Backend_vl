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
import lombok.Data;

@Data
@Entity(name = "works")
@Table(name = "works")
public class Works {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column
	public String name;

	@Transient
	public Long parentWorksId;

	@ManyToOne
	@JoinColumn(name = "parent_works_id")
	@JsonIgnore
	public Works parentWorks;

	@OneToMany(mappedBy = "parentWorks")
	public List<Works> childWorks;

	public Works() {
	}

}
