package com.shared.rides.domain;

/*
 Clase que representa la relación que existe entre dos personas para un
 día y un horario específico. Esa asociación puede estar en diferentes
 estados; lo que esta representado por el atributo state (tipo Enum).
*/

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name="Association")
public class Association implements Serializable{

	private long associationId;
	private User applicantID;
	private int day;
	private int inout;
	private State state;
	private Date date;
	
//-----------CONSTRUCTOR

	public Association(){	
	}
	
	public Association(long id){
		this.associationId = id;
	}
	
//-----------GETTERS & SETTERS 

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	public long getAssociationId() {
		return associationId;
	}

	public void setAssociationId(long associationId) {
		this.associationId = associationId;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "applicantID")
	public User getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(User applicantID) {
		this.applicantID = applicantID;
	}

	@Column(name="day", nullable = false)
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	@Column(name="hourInOut", nullable = false)
	public int getInout() {
		return inout;
	}
	public void setInout(int inout) {
		this.inout = inout;
	}
		
	@Enumerated(EnumType.STRING)
	@Column(name="state")
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	@Column(name="date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
//---------------------
	
}
