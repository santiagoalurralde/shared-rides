package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Vehicle;

public interface VehicleDAO {

	public boolean save(Vehicle vehicle);
	public Vehicle load(Vehicle vehicle);
	public Vehicle delete(Vehicle vehicle);
	public List<Vehicle> listAll();
}
