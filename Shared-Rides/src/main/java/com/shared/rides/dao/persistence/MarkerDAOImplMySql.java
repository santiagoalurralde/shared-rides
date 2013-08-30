package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IMarkerDAO;
import com.shared.rides.domain.Marker;

@Repository
@Transactional
public class MarkerDAOImplMySql implements IMarkerDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Marker marker) {
		sessionFactory.getCurrentSession().save(marker);
		return false;
	}

	public Marker load(Marker marker) {
		return (Marker) sessionFactory.getCurrentSession().get(Marker.class, marker.getMarkerId());	
	}

	public Marker delete(Marker marker) {
		sessionFactory.getCurrentSession().delete(marker);
		return null;
	}

	public List<Marker> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Marker");     
		List<Marker> markers = query.list();
		return markers;
	}

}
