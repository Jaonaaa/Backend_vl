package com.popo.models;

import jakarta.persistence.Entity;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "car")
@Table(name = "car")

public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column
	public String imma;

	@Column
	public String color;

	@Column
	public Double kilometrage;

	public LocalTime publishedOn;

	public Date date;

	public Timestamp timestamp;
	// @Column
	// public Period presalePeriod;

	public Car(Long id, String imma, String color, Double kilometrage) {
		this.id = id;
		this.imma = imma;
		this.color = color;
		this.kilometrage = kilometrage;
	}

	public Car() {

	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImma(String imma) {
		this.imma = imma;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setKilometrage(Double kilometrage) {
		this.kilometrage = kilometrage;
	}

	public Long getId() {
		return this.id;
	}

	public String getImma() {
		return this.imma;
	}

	public String getColor() {
		return this.color;
	}

	public Double getKilometrage() {
		return this.kilometrage;
	}

}
