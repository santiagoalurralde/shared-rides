package com.shared.rides.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;

/*
 Clase que representa el tipo de usuario peatón; quien posee un rating, al igual
 que el conductor, y su propio horario.
 Implementa la interfaz RoleType y extiende de la clase User.
 */

@Entity
@Table(name="Pedestrian")
@PrimaryKeyJoinColumn(name="pedestrianID")
public class Pedestrian extends User implements RoleType{

	@Column(name="rating", nullable = false)
	private float rating;
	
	@OneToOne(mappedBy="scheduleID", cascade = CascadeType.ALL)
	private Schedule schedule;

//-----------CONSTRUCTOR 

	public Pedestrian(){
	}
	
//-----------GETTERS & SETTERS
	
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public Schedule getSchedule() {
		return schedule;
	}
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
//---------------------------

}
