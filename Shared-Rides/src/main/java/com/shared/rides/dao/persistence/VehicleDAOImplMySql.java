package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IVehicleDAO;
import com.shared.rides.domain.Vehicle;

@Repository
@Transactional
public class VehicleDAOImplMySql implements IVehicleDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return false;
	}

	public Vehicle load(Vehicle vehicle) {
		return vehicle;
	}

	public Vehicle delete(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Vehicle> listAll() {
		
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Vehicle");
        
		List<Vehicle> vehicles = query.list();
		
		return vehicles;
	}

}
