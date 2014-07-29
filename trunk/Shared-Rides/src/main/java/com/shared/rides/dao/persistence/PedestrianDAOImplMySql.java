package com.shared.rides.dao.persistence;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IPedestrianDAO;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.Stop;
import com.shared.rides.domain.User;

@Repository
@Transactional
public class PedestrianDAOImplMySql implements IPedestrianDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Pedestrian ped) {
		sessionFactory.getCurrentSession().save(ped);
		return false;
	}

	public Pedestrian load(Pedestrian ped) {
		return (Pedestrian) sessionFactory.getCurrentSession().get(Pedestrian.class, ped.getPedestrianId());	
	}

	public Pedestrian delete(Pedestrian ped) {
		sessionFactory.getCurrentSession().delete(ped);
		return null;
	}

	public List<Pedestrian> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Pedestrian");     
		List<Pedestrian> pedestrians = query.list();
		return pedestrians;
	}

	public Pedestrian update(Pedestrian ped) {
		sessionFactory.getCurrentSession().update(ped);
		return null;
	}

	public Pedestrian getLastPedestrian(){
		String sql = "SELECT MAX(id) FROM Pedestrian";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		BigInteger id = (BigInteger) query.list().get(0);
		return (Pedestrian) sessionFactory.getCurrentSession().get(Pedestrian.class, id.longValue());
	}

	public void newSch(Pedestrian ped, Schedule sch) {
		Long idSch = sch.getScheduleId();
		Long idPed = ped.getPedestrianId();
		String sql = "INSERT INTO Pedestrian_Schedule (pedestrianID, scheduleID) VALUES ( "+ idPed +" , "+ idSch +"  )";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}

	public void newStop(Pedestrian ped, Stop stop) {
		Long idStop = stop.getStopId();
		Long idPed = ped.getPedestrianId();
		String sql = "INSERT INTO Pedestrian_Stop (pedestrianID, stopID) VALUES ( "+ idPed +" , "+ idStop +"  )";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}
}
