package com.shared.rides.domain;


public class Driver extends User implements RoleType{
	
	private float rating;
	private int[][] schedule;
	
	public float getRating() {
		return 0;
	}
	
	
	
	public int[][] getSchedule() {
		return null;
	}

	public Vehicle getVehicle() {
		return null;
	}

}
