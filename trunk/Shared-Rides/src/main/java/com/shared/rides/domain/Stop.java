package com.shared.rides.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Clase que representa las paradas de los usuarios peatones de la aplicacion.
 * Atributos:
 * 		stopId, generado por la base de datos
 * 		day, que es un entero, donde el 1 es el Lunes
 * 		inout, entero que indica si la parada es para la entrada o salida; in --> 0, out --> 1
 * 		pathFile, que es un String que representa el nombre del archivo .gpx
 */

@Entity
@Table(name = "Stops")
public class Stop implements Serializable{

	private long stopId;
	private int day;
	private int inout;
	private Double lat;
	private Double lon;
	
	//-----------CONSTRUCTOR

		public Stop(){	
		}
		
	//-----------GETTERS & SETTERS 

	@Id
	@GeneratedValue
	@Column(name = "id")
	public long getStopId() {
		return stopId;
	}
	public void setStopId(long stopId) {
		this.stopId = stopId;
	}
	
	@Column(name = "day")
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	@Column(name = "hourInOut")
	public int getInout() {
		return inout;
	}

	public void setInout(int inout) {
		this.inout = inout;
	}

	@Column(name = "lat")
	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Column(name = "lon")
	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	
}
