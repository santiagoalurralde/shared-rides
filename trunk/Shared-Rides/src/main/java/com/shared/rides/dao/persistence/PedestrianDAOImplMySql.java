package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IPedestrianDAO;
import com.shared.rides.domain.Pedestrian;

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

}
