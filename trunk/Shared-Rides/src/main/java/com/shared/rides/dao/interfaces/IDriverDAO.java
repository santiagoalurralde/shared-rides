package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Driver;

public interface IDriverDAO {
	
		public boolean save(Driver driver);
		public Driver load(Driver driver);
		public Driver delete(Driver driver);
	    public List<Driver> listAll();
	
}
