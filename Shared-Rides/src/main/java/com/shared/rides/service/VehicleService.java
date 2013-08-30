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
		System.out.println("Se van a listar los vehiculos");
		return this.vehicleDAO.listAll();
	}
	
	public boolean saveVehicle(Vehicle v){
		System.out.println("Se va a guardar un vehiculo");
		return this.vehicleDAO.save(v);
	}
	
	public Vehicle deleteVehicle(Vehicle v){
		System.out.println("Se va a eliminar un vehiculo");
		return this.vehicleDAO.delete(v);
	}
	
	public Vehicle loadVehicle(Vehicle v){
		System.out.println("Se va a obtener un vehiculo");
		return this.vehicleDAO.load(v);
	}
	
}
