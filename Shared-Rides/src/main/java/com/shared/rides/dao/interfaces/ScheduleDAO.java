package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Schedule;

public interface ScheduleDAO {

	public boolean save(Schedule sch);
	public Schedule load(Schedule sch);
	public Schedule delete(Schedule sch);
	public List<Schedule> listAll();
}
