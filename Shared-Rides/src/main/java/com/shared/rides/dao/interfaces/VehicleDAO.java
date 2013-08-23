package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Vehicle;

public interface VehicleDAO {

	public void addVehicle(Vehicle vehicle);
    public List<Vehicle> listVehicle();
    public void removeVehicle(Integer id);
}
