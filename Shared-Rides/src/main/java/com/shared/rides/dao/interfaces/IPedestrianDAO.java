package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Pedestrian;

public interface IPedestrianDAO {

	public boolean save(Pedestrian ped);
	public Pedestrian load(Pedestrian ped);
	public Pedestrian delete(Pedestrian ped);
	public List<Pedestrian> listAll();
}
