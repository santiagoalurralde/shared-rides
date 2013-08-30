package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

/*
 Clase que representa los diferentes puntos de ubicación que va a servir
 para interactuar con la API.
 */

@Entity
@Table(name="Marker")
public class Marker implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="markerID", nullable = false)
	private long markerId;
	
	@Column(name="lat", nullable = false)
	private float latitude;
	
	@Column(name="long", nullable = false)
	private float longitude;
	
//-----------CONSTRUCTOR 
	
	public Marker(){
	}

//-----------GETTERS & SETTERS 

	
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public long getMarkerId() {
		return markerId;
	}

	public void setMarkerId(long markerId) {
		this.markerId = markerId;
	}

//-----------------------
	
}
