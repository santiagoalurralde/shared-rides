package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Schedule;
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
	
	public void newAssoc(User u, Association assoc){
		long idAssoc = assoc.getAssociationId();
		long idUser = u.getUserId();
		String hql = "INSERT INTO User_Assoc (userID, associationID) VALUES ( "+ idUser +" , "+ idAssoc +"  )";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(hql);
		query.executeUpdate();
	}

	public List<Association> getMyRequests(User u) {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Association assoc WHERE assoc.applicantID = " + u.getUserId() + " ");	
		List<Association> assocs = query.list();
		return assocs;
	}

	public List<Long> getAllSchedule(User u) {
		String sql = "(Select s.id as id From User u, User_Pedestrian uP, Pedestrian p, Pedestrian_Schedule pS, Schedule s"
				+ "Where u.id = ? And u.id = uP.userID And uP.pedestrianID = p.id And p.id = pS.pedestrianID And pS.scheduleID = s.id)"
				+ "UNION"
				+ "(Select s.id as id From User u, User_Driver uD, Driver d, Driver_Schedule dS, Schedule s"
				+ " Where u.id = ? And u.id = uD.userID And uD.driverID = d.id And d.id = dS.driverID And dS.scheduleID = s.id)";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, u.getUserId());
		
		return query.list();
	}
	
}
