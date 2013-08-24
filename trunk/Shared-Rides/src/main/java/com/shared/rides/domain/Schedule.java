package com.shared.rides.domain;

public class Schedule {

	private long oid;
	private User user;
	private int day;
	private int hour;
	private int inout;
	
//-----------GETTERS & SETTERS 
	
	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
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
}
