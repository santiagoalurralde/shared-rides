package com.shared.rides.domain;

public enum Shift {
	MOORNING("Mañana"), AFTERNOON("Tarde"), EVENING("Noche");

	private String shiftName;
	
	Shift(String n){
		this.shiftName = n;
	}
	
	public String getShiftName(){  
		  return shiftName;
		  }  
	
}
