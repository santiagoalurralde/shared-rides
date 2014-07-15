package com.shared.rides.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/*
 Clase que representa el tipo de usuario Driver (conductor) que posee un 
 rating específico, un horario y un vehículo vinculado.
 Implementa la interfaz RoleType y extiende de la clase User.
 */

@Entity
@Table(name="Driver")
public class Driver implements Profile, Serializable{
	
	private long driverId;	
	private float rating;
	private List<Schedule> schedule;
	private Vehicle vehicle;
	private List<Track> tracks;

//-----------CONSTRUCTOR	

	public Driver(){	
	}

//-----------GETTERS & SETTERS

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}
	
	@Column(name="ratingDriver")
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Driver_Schedule", joinColumns = @JoinColumn(name="driverID"),
	inverseJoinColumns = @JoinColumn(name="scheduleID"))
	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicleID")
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Driver_Track", joinColumns = @JoinColumn(name="driverID"),
	inverseJoinColumns = @JoinColumn(name="trackID"))
	public List<Track> getTracks() {
		return tracks;
	}
	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	
//-------------------
	
}
