package com.shared.rides.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;


/*
 Clase que representa el tipo de usuario Driver (conductor) que posee un 
 rating específico, un horario y un vehículo vinculado.
 Implementa la interfaz RoleType y extiende de la clase User.
 */

@Entity
@Table(name="Driver")
@PrimaryKeyJoinColumn(name="driverID")
public class Driver extends User implements RoleType{
	
	@Column(name="rating")
	private float rating;
	
	@OneToOne(mappedBy="scheduleID", cascade = CascadeType.ALL)
	private Schedule schedule;
	
	@OneToOne(mappedBy="vehicleID", cascade = CascadeType.ALL)
	private Vehicle vehicle;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="Driver-Track", joinColumns = @JoinColumn(name="driverID"),
	inverseJoinColumns = @JoinColumn(name="trackID"))
	private List<Track> tracks;

//-----------CONSTRUCTOR	

	public Driver(){	
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
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public List<Track> getTracks() {
		return tracks;
	}
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}
	
//-------------------
	
}
