package com.shared.rides.dao.persistence;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IDriverDAO;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;

@Repository
@Transactional
public class DriverDAOImplMySql implements IDriverDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Driver driver) {
		sessionFactory.getCurrentSession().save(driver);
		return true;
	}

	public Driver load(Driver driver) {
		return (Driver) sessionFactory.getCurrentSession().get(Driver.class, driver.getDriverId());	
	}

	public Driver delete(Driver driver) {
		sessionFactory.getCurrentSession().delete(driver);
		return null;
	}

	public List<Driver> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Driver");     
		List<Driver> drivers = query.list();
		return drivers;
	}

	public Driver update(Driver driver) {
		sessionFactory.getCurrentSession().update(driver);
		return null;
	}

	public Driver getLastDriver(){
		String sql = "SELECT MAX(id) FROM Driver";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		BigInteger id = (BigInteger) query.list().get(0);
		return (Driver) sessionFactory.getCurrentSession().get(Driver.class, id.longValue());
	}

	public void newSch(Driver driver, Schedule sch) {
		Long idSch = sch.getScheduleId();
		Long idDriver = driver.getDriverId();
		String sql = "INSERT INTO Driver_Schedule (driverID, scheduleID) VALUES ( "+ idDriver +" , "+ idSch +"  )";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();		
	}

	public void newTrack(Driver driver, Track track) {
		Long idTrack = track.getTrackId();
		Long idDriver = driver.getDriverId();
		String sql = "INSERT INTO Driver_Track (driverID, trackID) VALUES ( "+ idDriver +" , "+ idTrack +"  )";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();				
	}

}
