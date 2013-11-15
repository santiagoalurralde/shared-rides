package com.shared.rides.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Column;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
	private long pedestrianId;
	
	@Column(name="ratingPed", nullable = false)
	private float rating;
	

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Pedestrian_Schedule", joinColumns = @JoinColumn(name="pedestrianID"),
	inverseJoinColumns = @JoinColumn(name="scheduleID"))
	private List<Schedule> schedule;

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


	public long getPedestrianID() {
		return pedestrianId;
	}

	public void setPedestrianID(long pedestrianId) {
		this.pedestrianId = pedestrianId;
	}

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}


//---------------------------

}
