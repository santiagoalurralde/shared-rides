package com.shared.rides.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Stops")
public class Stop {

	private long stopId;
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
		
	@Column (name = "pathFile")
	public String getPathFile() {
		return pathFile;
	}
	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}
	
}
