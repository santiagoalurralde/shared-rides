package com.shared.rides.domain;

/*
 Clase que representa los diferentes puntos de ubicación que va a servir
 para interactuar con la API.
 */

public class Marker {

	private long oid;
	private Address address;
	private float latitude;
	private float longitude;
	
//-----------GETTERS & SETTERS 
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
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

//-----------------------
	
}
