package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Vehicle;

public interface IVehicleDAO {

	public boolean save(Vehicle vehicle);
	public Vehicle load(long id);
	public Vehicle update(Vehicle vehicle);
	public Vehicle delete(Vehicle vehicle);
	public List<Vehicle> listAll();
}
