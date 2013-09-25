package com.shared.rides.domain;

/*
 Clase que representa un usuario genérico. Esta clase en particular tiene por un lado
 el oid que es un autoincremental que representa de forma única a ese usuario; y por
 otro lado, el personalId que es lo que diferencia a esa persona de otra de la misma
 organizacion (por ejemplo, la clave de cada alumno en la UCC)
 */


import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name = "User")
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private long userId;
	
	@Column(name="personalID")
	private String personalId;
	
	@Column(name="password")
	private String pw;
	
	@Column(name="name")
	private String name;
	
	@Column(name="surname")
	private String surname;
	
	@OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
	private Address address;
	
	@OneToOne
    @PrimaryKeyJoinColumn
	private Organization organization;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="User-Assoc", joinColumns = @JoinColumn(name="userID"),
	inverseJoinColumns = @JoinColumn(name="associationID"))
	private List<Association> associations;
	
	@Column(name="phoneNumber")
	private int phoneNumber;
	
	@Column(name="email")
	private String email;
	
	@Column(name="shift")
	private int shift;
	
	@Column(name="picture")
	private String picture;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="Profile", joinColumns = @JoinColumn(name="userID"),
	inverseJoinColumns = @JoinColumn(name="pedestrianID"))
	private Pedestrian pedestrian;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="Profile", joinColumns = @JoinColumn(name="userID"),
	inverseJoinColumns = @JoinColumn(name="driverID"))
	private Driver driver;
	
	
//-----------CONSTRUCTOR 
	
	public User(){
	}
	
//-----------GETTERS & SETTERS 
	
	public int getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getShift() {
		return shift;
	}

	public void setShift(int shift) {
		this.shift = shift;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Pedestrian getPedestrian() {
		return pedestrian;
	}

	public void setPedestrian(Pedestrian pedestrian) {
		this.pedestrian = pedestrian;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	
//-------------------------------
	
	public boolean isPedestrian(){
		if (pedestrian == null) return false;
		return true;
	}

	public boolean isDriver(){
		if (driver == null) return false;
		return true;
	}
}
