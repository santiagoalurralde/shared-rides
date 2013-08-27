package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.domain.Vehicle;
import com.shared.rides.dao.interfaces.IVehicleDAO;

@Service
public class VehicleService {

	@Autowired
	private IVehicleDAO vehicleDAO;
	
	public List<Vehicle> listAllVehicles() {
		return this.vehicleDAO.listAll();
	}
	
}
