package com.shared.rides.domain;

/*
 Clase que representa el tipo de usuario Driver (conductor) que posee un 
 rating específico, un horario y un vehículo vinculado.
 Implementa la interfaz RoleType y extiende de la clase User.
 */

public class Driver extends User implements RoleType{
	
	private long oid;
	private float rating;
	private Schedule schedule;
	private Vehicle vehicle;

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
	
//-------------------
	
}
