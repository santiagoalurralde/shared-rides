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

	@Id
	@GeneratedValue
	@Column(name="id")
	private long organizationId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressID")
	private Address address;
	
	@Column(name="name")
	private String name;
	
//-----------CONSTRUCTOR 

	public Organization(){
	}

//-----------GETTERS & SETTERS 


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

	public long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
	
//------------------------
	
}
