package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.Track;

public interface IDriverDAO {
	
		public boolean save(Driver driver);
		public Driver load(long id);
		public Driver update(Driver driver);
		public Driver delete(Driver driver);
	    public List<Driver> listAll();
	    public Driver getLastDriver();
	    public void newSch(long driverId, long schId);
	    public void newTrack(long driverId, long trackId);
}
