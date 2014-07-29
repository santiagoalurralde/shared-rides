package com.shared.rides.dao.persistence;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IStopDAO;
import com.shared.rides.domain.Organization;
import com.shared.rides.domain.Stop;
import com.shared.rides.domain.User;

@Repository
@Transactional
public class StopDAOImplMySql implements IStopDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Stop stop) {
		sessionFactory.getCurrentSession().save(stop);
		return false;
	}

	public Stop load(Stop stop) {
		return (Stop) sessionFactory.getCurrentSession().get(Stop.class, stop.getStopId());
	}

	public Stop update(Stop stop) {
		sessionFactory.getCurrentSession().update(stop);
		return null;
	}

	public Stop delete(Stop stop) {
		sessionFactory.getCurrentSession().delete(stop);
		return null;
	}

	public List<Stop> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Stops");     
		List<Stop> stops = query.list();
		return stops;
	}

	public Stop getLastStop() {
		String sql = "SELECT MAX(id) FROM Stops";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		BigInteger id = (BigInteger) query.list().get(0);
		return (Stop) sessionFactory.getCurrentSession().get(Stop.class, id.longValue());
	}

}
