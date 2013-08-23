package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Driver;

public interface DriverDAO {
	
		public void addDriver(Driver driver);
	    public List<Driver> listDriver();
	    public void removeDriver(Integer id);
	
}
