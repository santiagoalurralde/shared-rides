package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Stop;

public interface IStopDAO {
	public boolean save(Stop stop);
	public Stop load(Stop stop);
	public Stop update(Stop stop);
	public Stop delete(Stop stop);
	public List<Stop> listAll();
	public Stop getLastStop();
}
