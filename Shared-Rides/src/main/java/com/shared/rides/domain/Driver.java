package com.shared.rides.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;

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
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	private long driverId;
	
	@Column(name="ratingDriver")
	private float rating;
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Driver_Schedule", joinColumns = @JoinColumn(name="driverID"),
	inverseJoinColumns = @JoinColumn(name="scheduleID"))
	private List<Schedule> schedule;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "vehicleID")
	private Vehicle vehicle;
	
	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Driver_Track", joinColumns = @JoinColumn(name="driverID"),
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

	public long getDriverId() {
		return driverId;
	}

	public void setDriverId(long driverId) {
		this.driverId = driverId;
	}

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}
	
//-------------------
	
}
