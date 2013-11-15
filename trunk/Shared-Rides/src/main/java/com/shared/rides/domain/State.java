package com.shared.rides.domain;

public enum State {
	PENDING("Pendiente"), ACCEPTED("Aceptado"), CANCELLED("Cancelado");
	
	private String stateName;
	
	State(String name){
		this.stateName = name;
	}
	
	public String getStateName(){
		return this.stateName;
	}
	
}
