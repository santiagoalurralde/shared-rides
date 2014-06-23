package com.shared.rides.dao.persistence;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.User;


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

	public long getSupplierId(Association assoc) {
		String sql = "SELECT u.id as id FROM User u , User_Assoc uAssoc, Association assoc WHERE uAssoc.associationID = assoc.id AND uAssoc.userID = u.id AND assoc.id = ? ";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, assoc.getAssociationId());
		
		BigInteger supplierId = (BigInteger) query.list().get(0);
		return supplierId.longValue();
	}

	public List<Long> findAssoc (User requestUser, User assocUser){
		String sql = "SELECT uAssoc.id as id FROM User u, User_Assoc uAssoc, Association assoc WHERE u.id = uAssoc.userID AND uAssoc.associationID = assoc.id AND (assoc.applicantID = ? AND uAssoc.userID = ? OR assoc.applicantID = ? AND uAssoc.userID = ?)";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, requestUser.getUserId());
		query.setParameter(1, assocUser.getUserId());
		query.setParameter(2, assocUser.getUserId());
		query.setParameter(3, requestUser.getUserId());
		
		return query.list();
	}
}
