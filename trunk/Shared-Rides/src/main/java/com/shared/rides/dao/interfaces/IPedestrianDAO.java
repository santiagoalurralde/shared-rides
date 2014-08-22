package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.Stop;

public interface IPedestrianDAO {

	public boolean save(Pedestrian ped);
	public Pedestrian load(long id);
	public Pedestrian update(Pedestrian ped);
	public Pedestrian delete(Pedestrian ped);
	public List<Pedestrian> listAll();
	public Pedestrian getLastPedestrian();
	public void newSch(long pedId, long schId);
	public void newStop(long pedId, long stopId);
}
