package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
public class Pedestrian implements Profile, Serializable{

	@Id
	@GeneratedValue
	@Column(name="id")
	private long pedestrianID;
	
	@Column(name="rating_ped", nullable = false)
	private float rating;
	

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="Pedestrian_Schedule", joinColumns = @JoinColumn(name="pedestrianID"),
	inverseJoinColumns = @JoinColumn(name="scheduleID"))
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

	public long getPedestrianID() {
		return pedestrianID;
	}

	public void setPedestrianID(long pedestrianID) {
		this.pedestrianID = pedestrianID;
	}

	
//---------------------------

}
