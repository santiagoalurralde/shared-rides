package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shared.rides.dao.interfaces.DriverDAO;
import com.shared.rides.domain.Driver;


@Repository
public class DriverDAOImplMySql implements DriverDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	public void addDriver(Driver driver) {
		sessionFactory.getCurrentSession().save(driver);
	}

	public List<Driver> listDriver() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from User")
                .list();
	}

	public void removeDriver(Integer id) {
		  Driver driver = (Driver) sessionFactory.getCurrentSession().load(
	                Driver.class, id);
	        if (null != driver) {
	            sessionFactory.getCurrentSession().delete(driver);
	        }
	}

}
