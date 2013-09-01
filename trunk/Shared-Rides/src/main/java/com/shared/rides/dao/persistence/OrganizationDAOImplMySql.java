package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IOrganizationDAO;
import com.shared.rides.domain.Organization;

@Repository
@Transactional
public class OrganizationDAOImplMySql implements IOrganizationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Organization org) {
		sessionFactory.getCurrentSession().save(org);
		return false;
	}

	public Organization load(Organization org) {
		return (Organization) sessionFactory.getCurrentSession().get(Organization.class, org.getOrganizationId());	
	}

	public Organization delete(Organization org) {
		sessionFactory.getCurrentSession().delete(org);
		return null;
	}

	public List<Organization> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Organization");     
		List<Organization> organizations = query.list();
		return organizations;
	}

	public Organization update(Organization org) {
		sessionFactory.getCurrentSession().update(org);
		return null;
	}

}
