package com.shared.rides.domain;

/*
 Clase que representa la relación que existe entre dos personas para un
 día y un horario específico. Esa asociación puede estar en diferentes
 estados; lo que esta representado por el atributo state (tipo Enum).
*/


public class Association {
		
	private User supplier;
	private User applier;
	private int day;
	private int inout;
	private State state;
	
//-----------GETTERS & SETTERS 

	public User getSupplier() {
		return supplier;
	}
	public void setSupplier(User supplier) {
		this.supplier = supplier;
	}
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
	
//---------------------
	
}
