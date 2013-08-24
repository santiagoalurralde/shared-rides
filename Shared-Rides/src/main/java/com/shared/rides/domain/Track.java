package com.shared.rides.domain;

/*
 Clase que representa las diferentes rutas o caminos que realizan los 
 conductores dependiendo el dia y si es horario de salida o entrada.
 En el atributo pathFile se almacena un archivo .gpx que es el tipo de archivo
 que utiliza la API OpenStreetMaps.
 */

public class Track {
	
	private long oid;
	private Driver driver;
	private int day;
	private int inout;
	private String pathFile;
	
//-----------GETTERS & SETTERS 

	public long getOid() {
		return oid;
	}
	public void setOid(long oid) {
		this.oid = oid;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
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
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
//-------------------------
	
}
