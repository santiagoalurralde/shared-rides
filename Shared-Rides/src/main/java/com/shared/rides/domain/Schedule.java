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

	private long scheduleId;
	private int day;	
	private String hourIn;
	private String hourOut;
	
//-----------CONSTRUCTOR 

	public Schedule(){
	}
	
	public Schedule(long id){
		this.scheduleId = id;
	}
	
//-----------GETTERS & SETTERS 
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	@Column(name="day", nullable = false)
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	@Column(name="hourIn", nullable = false)
	public String getHourIn() {
		return hourIn;
	}
	public void setHourIn(String hourIn) {
		this.hourIn = hourIn;
	}

	@Column(name="hourOut", nullable = false)
	public String getHourOut() {
		return hourOut;
	}
	public void setHourOut(String hourOut) {
		this.hourOut = hourOut;
	}
}
