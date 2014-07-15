package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;

@Entity
@Table(name = "Address")
public class Address implements Serializable{

	private long addressId;
	private String street;
	private int number;
	private String neighborhood;
	private String city;
	private Marker marker;
	
 
//-----------CONSTRUCTOR	

	public Address(){
	}

//-----------GETTERS & SETTERS
	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	
	@Column(name="street", nullable = false)
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	
	@Column(name="number", nullable = false)
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Column(name="neighborhood")
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
	@Column(name="city")
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "markerID")
	public Marker getMarker() {
		return marker;
	}
	public void setMarker(Marker marker) {
		this.marker = marker;
	}


	
//-----------------------
	
}
