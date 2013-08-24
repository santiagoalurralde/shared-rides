package com.shared.rides.domain;

public class Organization {

	private long oid;
	private Address address;
	private String name;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
//------------------------
	
}
