package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="Schedule")
public class Schedule implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	private long scheduleId;
	
	@Column(name="day", nullable = false)
	private int day;
	
	@Column(name="hourIn", nullable = false)
	private int hourIn;
	
	@Column(name="hourOut", nullable = false)
	private int hourOut;
	
//-----------CONSTRUCTOR 

	public Schedule(){
	}
	
	public Schedule(long id){
		this.scheduleId = id;
	}
	
//-----------GETTERS & SETTERS 
	
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHourIn() {
		return hourIn;
	}
	public void setHourIn(int hourIn) {
		this.hourIn = hourIn;
	}
	public int getHourOut() {
		return hourOut;
	}
	public void setHourOut(int hourOut) {
		this.hourOut = hourOut;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
}
