package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToOne;

@Entity
@Table(name="Organization")
public class Organization implements Serializable{

	private long organizationId;
	private Address address;
	private String name;
	
//-----------CONSTRUCTOR 

	public Organization(){
	}

//-----------GETTERS & SETTERS 
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressID")
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

//------------------------
	
}
