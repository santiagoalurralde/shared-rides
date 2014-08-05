package com.shared.rides.domain;

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
public class Stop {

	private long stopId;
	private int day;
	private int inout;
	private String pathFile;
	
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

	@Column (name = "pathFile")
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
}
