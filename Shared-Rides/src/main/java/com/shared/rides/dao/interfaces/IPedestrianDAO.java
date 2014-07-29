package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.Stop;

public interface IPedestrianDAO {

	public boolean save(Pedestrian ped);
	public Pedestrian load(Pedestrian ped);
	public Pedestrian update(Pedestrian ped);
	public Pedestrian delete(Pedestrian ped);
	public List<Pedestrian> listAll();
	public Pedestrian getLastPedestrian();
	public void newSch(Pedestrian ped, Schedule sch);
	public void newStop(Pedestrian ped, Stop stop);
}
