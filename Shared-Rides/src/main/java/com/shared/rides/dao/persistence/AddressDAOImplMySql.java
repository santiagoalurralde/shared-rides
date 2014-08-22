package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IAddressDAO;
import com.shared.rides.domain.Address;

@Repository
@Transactional
public class AddressDAOImplMySql implements IAddressDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Address address) {
		sessionFactory.getCurrentSession().save(address);
		return true;
	}

	public Address load(long id) {
		return (Address) sessionFactory.getCurrentSession().get(Address.class, id);	
	}

	public Address delete(Address address) {
		sessionFactory.getCurrentSession().delete(address);
		return null;
	}

	public List<Address> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Address");     
		List<Address> addresses = query.list();
		return addresses;
	}

	public Address update(Address address) {
		sessionFactory.getCurrentSession().update(address);
		return null;
	}

}
