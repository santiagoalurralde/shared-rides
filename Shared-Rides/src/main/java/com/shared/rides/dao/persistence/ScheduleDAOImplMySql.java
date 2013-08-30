package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IScheduleDAO;
import com.shared.rides.domain.Schedule;

@Repository
@Transactional
public class ScheduleDAOImplMySql implements IScheduleDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Schedule sch) {
		sessionFactory.getCurrentSession().save(sch);
		return false;
	}

	public Schedule load(Schedule sch) {
		return (Schedule) sessionFactory.getCurrentSession().get(Schedule.class, sch.getScheduleId());	
	}

	public Schedule delete(Schedule sch) {
		sessionFactory.getCurrentSession().delete(sch);
		return null;
	}

	public List<Schedule> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Schedule");     
		List<Schedule> schedules = query.list();
		return schedules;
	}

}
