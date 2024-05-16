package com.popo.models;

import jakarta.persistence.Entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.popo.UserAuth.Models.User;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "payement")
@Table(name = "payement")
public class Payement {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
	public java.sql.Timestamp payement_time;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_client", nullable = true)
	public User client;

	@Transient
	int id_user;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_devis", nullable = true)
	@JsonIgnore
	public Devis devis;

	@Transient
	int id_devis;

	@Column
	public Double amount;

	@Column(name = "ref_paiement")
	public String refPaiement;

	public Payement() {
	}

	public Payement(Long id, Timestamp payement_time, User client, int id_user, Devis devis, int id_devis,
			Double amount, String refPaiement) {
		this.id = id;
		this.payement_time = payement_time;
		this.client = client;
		this.id_user = id_user;
		this.devis = devis;
		this.id_devis = id_devis;
		this.amount = amount;
		this.refPaiement = refPaiement;
	}

}
