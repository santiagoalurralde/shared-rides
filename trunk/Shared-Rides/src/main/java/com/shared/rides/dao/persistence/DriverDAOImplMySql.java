package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IDriverDAO;
import com.shared.rides.domain.Driver;

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
		return (Driver) sessionFactory.getCurrentSession().get(Driver.class, driver.getUserId());	
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

}
