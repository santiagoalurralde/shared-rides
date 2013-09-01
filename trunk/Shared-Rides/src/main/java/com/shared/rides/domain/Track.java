package com.shared.rides.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;

/*
 Clase que representa las diferentes rutas o caminos que realizan los 
 conductores dependiendo el dia y si es horario de salida o entrada.
 En el atributo pathFile se almacena un archivo .gpx que es el tipo de archivo
 que utiliza la API OpenStreetMaps.
 */

@Entity
@Table(name="Track")
public class Track implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private long trackId;
	
	@Column(name="day")
	private int day;
	
	@Column(name="inout")
	private int inout;
	
	@Column(name="pathFile")
	private String pathFile;


//-----------CONSTRUCTOR

	public Track(){
	}
	
//-----------GETTERS & SETTERS 

	
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

	public long getTrackId() {
		return trackId;
	}

	public void setTrackId(long trackId) {
		this.trackId = trackId;
	}
	
}
