package com.shared.rides.domain;

/*
 Clase que representa un usuario genérico. Esta clase en particular tiene por un lado
 el oid que es un autoincremental que representa de forma única a ese usuario; y por
 otro lado, el personalId que es lo que diferencia a esa persona de otra de la misma
 organizacion (por ejemplo, la clave de cada alumno en la UCC)
 */


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "User")
@Inheritance(strategy=InheritanceType.JOINED)
public class User implements Serializable{
	
	private long userId;
	private String personalId;
	private String pw;
	private String name;
	private String surname;
	private Address address;
	private Organization organization;
	private List<Association> associations;
	private long phoneNumber;
	private String email;
	private Shift shift;
	private String picture;
	private Pedestrian pedestrian;
	private Driver driver;
	private Date lastLoginDate;
	
	
//-----------CONSTRUCTOR 
	
	public User(){
	}
	
	public User(long id){
		this.userId = id;
	}
	
//-----------GETTERS & SETTERS 

	@Id
	@GeneratedValue
	@Column(name="id")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Column(name="personalID")
	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Column(name="phoneNumber")
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name="surname")
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Column(name="password")
	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "addressID")
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@OneToOne
	@JoinColumn(name = "organizationID")
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="shift")
	public Shift getShift() {
		return shift;
	}

	public void setShift(Shift shift) {
		this.shift = shift;
	}

	@Column(name="picture")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}


	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="User_Assoc", joinColumns = @JoinColumn(name="userID"),
	inverseJoinColumns = @JoinColumn(name="associationID"))
	public List<Association> getAssociations() {
		return associations;
	}

	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="User_Pedestrian", joinColumns = @JoinColumn(name="userID"),
	inverseJoinColumns = @JoinColumn(name="pedestrianID"))
	public Pedestrian getPedestrian() {
		return pedestrian;
	}

	public void setPedestrian(Pedestrian pedestrian) {
		this.pedestrian = pedestrian;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name="User_Driver", joinColumns = @JoinColumn(name="userID"),
	inverseJoinColumns = @JoinColumn(name="driverID"))
	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	@Column(name="lastLoginDate")
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	
//-------------------------------

}
