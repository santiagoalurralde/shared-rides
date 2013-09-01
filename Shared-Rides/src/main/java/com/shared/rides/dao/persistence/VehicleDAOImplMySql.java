package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IVehicleDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Vehicle;

@Repository
@Transactional
public class VehicleDAOImplMySql implements IVehicleDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Vehicle vehicle) {
		sessionFactory.getCurrentSession().save(vehicle);
		return true;
	}

	public Vehicle load(Vehicle vehicle) {
		return (Vehicle) sessionFactory.getCurrentSession().get(Vehicle.class, vehicle.getVehicleId());	
	}

	public Vehicle delete(Vehicle vehicle) {
		sessionFactory.getCurrentSession().delete(vehicle);
		return null;
	}

	public List<Vehicle> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Vehicle");
		List<Vehicle> vehicles = query.list();		
		return vehicles;
	}

	public Vehicle update(Vehicle vehicle) {
		sessionFactory.getCurrentSession().update(vehicle);
		return null;
	}

}
