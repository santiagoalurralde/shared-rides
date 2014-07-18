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

	private long pedestrianId;
	private float rating;
	private List<Schedule> schedule;
	private List<Stop> stops;

//-----------CONSTRUCTOR 

	public Pedestrian(){
	}
	
//-----------GETTERS & SETTERS

	@Id
	@GeneratedValue
	@Column(name="id")
	public long getPedestrianId() {
		return pedestrianId;
	}

	public void setPedestrianId(long pedestrianId) {
		this.pedestrianId = pedestrianId;
	}

	@Column(name="ratingPed", nullable = false)
	public float getRating() {
		return rating;
	}
	
	public void setRating(float rating) {
		this.rating = rating;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Pedestrian_Schedule", joinColumns = @JoinColumn(name="pedestrianID"),
	inverseJoinColumns = @JoinColumn(name="scheduleID"))
	public List<Schedule> getSchedule() {
		return schedule;
	}
	
	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

	@OneToMany(mappedBy = "pedestrian")
	public List<Stop> getStops() {
		return stops;
	}

	public void setStops(List<Stop> stops) {
		this.stops = stops;
	}


//---------------------------

}
