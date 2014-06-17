package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.domain.Association;


@Repository
@Transactional
public class AssociationDAOImplMySql implements IAssociationDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Association assoc) {
		sessionFactory.getCurrentSession().save(assoc);
		return true;
	}

	public Association load(Association assoc) {
		return (Association) sessionFactory.getCurrentSession().get(Association.class, assoc.getAssociationId());	
	}

	public Association delete(Association assoc) {
		sessionFactory.getCurrentSession().delete(assoc);
		return null;
	}

	public List<Association> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Association");     
		List<Association> associations = query.list();
		return associations;
	}

	public Association update(Association assoc) {
		sessionFactory.getCurrentSession().update(assoc);
		return null;
	}

}
