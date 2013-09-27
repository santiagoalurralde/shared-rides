package com.shared.rides.domain;

/*
 Clase que representa la relación que existe entre dos personas para un
 día y un horario específico. Esa asociación puede estar en diferentes
 estados; lo que esta representado por el atributo state (tipo Enum).
*/

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="Association")
public class Association implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	private long associationId;

	@Column(name="applicantID", nullable = false)
	private User applier;
	
	@Column(name="day", nullable = false)
	private int day;
	
	@Column(name="inout", nullable = false)
	private int inout;
	
	@Enumerated(EnumType.STRING)
	@Column(name="state")
	private State state;
	
//-----------CONSTRUCTOR

	public Association(){	
	}
	
//-----------GETTERS & SETTERS 

	
	public User getApplier() {
		return applier;
	}
	public void setApplier(User applier) {
		this.applier = applier;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	public int getInout() {
		return inout;
	}
	public void setInout(int inout) {
		this.inout = inout;
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	
	public long getAssociationId() {
		return associationId;
	}

	public void setAssociationId(long associationId) {
		this.associationId = associationId;
	}

	
//---------------------
	
}
