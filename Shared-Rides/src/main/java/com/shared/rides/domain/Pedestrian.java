package com.shared.rides.domain;

/*
 Clase que representa el tipo de usuario peatón; quien posee un rating, al igual
 que el conductor, y su propio horario.
 Implementa la interfaz RoleType y extiende de la clase User.
 */

public class Pedestrian extends User implements RoleType{

	private long oid;
	private float rating;
	private Schedule schedule;
	
//-----------GETTERS & SETTERS
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
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
