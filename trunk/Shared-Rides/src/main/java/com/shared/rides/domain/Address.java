package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToOne;

@Entity
@Table(name = "Address")
public class Address implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	private long addressId;
	
	@Column(name="street", nullable = false)
	private String street;
	
	@Column(name="number", nullable = false)
	private int number;
	
	@Column(name="neighborhood")
	private String neighborhood;
	
	@Column(name="city")
	private String city;
	
	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Marker marker;
	
 
//-----------CONSTRUCTOR	

	public Address(){
	}

//-----------GETTERS & SETTERS

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getNeighborhood() {
		return neighborhood;
	}
	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public Marker getMarker() {
		return marker;
	}
	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	
//-----------------------
	
}
