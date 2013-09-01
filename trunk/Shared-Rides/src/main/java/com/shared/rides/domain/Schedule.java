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
@Table(name="Shedule")
public class Schedule implements Serializable{

	@Id
	@GeneratedValue
	@Column(name="id", nullable = false)
	private long scheduleId;
	
	@Column(name="day", nullable = false)
	private int day;
	
	@Column(name="hour", nullable = false)
	private int hour;
	
	@Column(name="inout", nullable = false)
	private int inout;
	
//-----------CONSTRUCTOR 

	public Schedule(){
	}
	
//-----------GETTERS & SETTERS 
	
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getInout() {
		return inout;
	}
	public void setInout(int inout) {
		this.inout = inout;
	}

	public long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
}
