package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "User")
public abstract class User implements Serializable{
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	protected long oid;
	
	@Column(name="name")
	protected String name;
	
	@Column(name="surname")
	protected String surname;
	
	@Column(name="personalID")
	protected String personalId;
	
	@Column(name="address")
	protected String address;
	
	@Column(name="phoneNumer")
	protected int phoneNumber;
	
	protected RoleType role;	
	
//-----------GETTERS & SETTERS 
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public RoleType getRole() {
		return role;
	}
	public void setRole(RoleType role) {
		this.role = role;
	}
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

//-------------------------------
	

}
