package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Marker;
import com.shared.rides.domain.User;


@Repository
@Transactional
public class UserDAOImplMySql implements IUserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(User user) {
		sessionFactory.getCurrentSession().save(user);
		return false;
	}

	public User load(User user) {
		return (User) sessionFactory.getCurrentSession().get(User.class, user.getUserId());	
	}

	public User delete(User user) {
		sessionFactory.getCurrentSession().delete(user);
		return null;
	}

	public List<User> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM User");     
		List<User> users = query.list();
		return users;
	}
	
	public User loadByEmail(User user) {
		String hql = "FROM User u WHERE u.email = ?";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setParameter(0, user.getEmail());
		return (User) query.list().get(0);	
	}

	public User update(User user) {
		sessionFactory.getCurrentSession().update(user);
		return null;
	}
	
	

}
