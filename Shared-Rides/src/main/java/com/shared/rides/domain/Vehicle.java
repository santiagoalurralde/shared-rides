package com.shared.rides.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="Vehicle")
public class Vehicle {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long oid;
	
	@Column (name="model")
	private String model;
	
	@Column (name="licensePlate")
	private String licensePlate;
	
	@Column (name="seats")
	private int seats;
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getLicensePlate() {
		return licensePlate;
	}
	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public int getSeats() {
		return seats;
	}
	public void setSeats(int seats) {
		this.seats = seats;
	}
	
	

}
