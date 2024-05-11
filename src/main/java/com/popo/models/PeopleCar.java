package com.popo.models; 

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "people_car")
@Table(name = "people_car")
public class PeopleCar  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_people")
	public People people;

	@jakarta.persistence.ManyToOne
	@jakarta.persistence.JoinColumn(name = "id_car")
	public Car car;

	@Column
	public java.sql.Timestamp date;

	public PeopleCar (Long id, People people, Car car, java.sql.Timestamp date){
 		this.id = id;
		this.people = people;
		this.car = car;
		this.date = date;
 	}

	public PeopleCar (){
  	}

	public void setId(Long id) {
		this.id = id ;
	}

	public void setPeople(People people) {
		this.people = people ;
	}

	public void setCar(Car car) {
		this.car = car ;
	}

	public void setDate(java.sql.Timestamp date) {
		this.date = date ;
	}

	public Long getId() {
		return this.id ;
	}

	public People getPeople() {
		return this.people ;
	}

	public Car getCar() {
		return this.car ;
	}

	public java.sql.Timestamp getDate() {
		return this.date ;
	}

}
