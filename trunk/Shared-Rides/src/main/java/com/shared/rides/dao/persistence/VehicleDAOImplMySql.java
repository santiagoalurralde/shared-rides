package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Session;

import com.shared.rides.dao.interfaces.VehicleDAO;
import com.shared.rides.domain.Vehicle;
import com.shared.rides.util.HibernateUtil;

public class VehicleDAOImplMySql implements VehicleDAO{

	public boolean save(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return false;
	}

	public Vehicle load(Vehicle vehicle) {
		
		Session s = HibernateUtil.getSessionFactory().openSession();
		Vehicle v = (Vehicle) s.get(Vehicle.class, 123);
		
		return v;
	}

	public Vehicle delete(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Vehicle> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
