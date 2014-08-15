package com.shared.rides.dao.persistence;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Pedestrian;
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
		if (query.list().size() != 0) return (User) query.list().get(0);
		else return null;
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
		String sql = "(SELECT s.id as id FROM User u, User_Pedestrian uP, Pedestrian p, Pedestrian_Schedule pS, Schedule s WHERE u.id = ? AND u.id = uP.userID AND uP.pedestrianID = p.id AND p.id = pS.pedestrianID AND pS.scheduleID = s.id)"
				+ "UNION"
				+ "(SELECT s.id as id From User u, User_Driver uD, Driver d, Driver_Schedule dS, Schedule s WHERE u.id = ? AND u.id = uD.userID AND uD.driverID = d.id AND d.id = dS.driverID AND dS.scheduleID = s.id)";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter(0, u.getUserId());
		query.setParameter(1, u.getUserId());
		
		List<Long> result = new ArrayList<Long>();
		for(int i = 0; i < query.list().size(); i++) {
		    BigInteger schId = (BigInteger) query.list().get(i);
			result.add(schId.longValue());
		}
		return result;
	}
	
	public User getLastUser(){
		String sql = "SELECT MAX(id) FROM User";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		BigInteger id = (BigInteger) query.list().get(0);
		return (User) sessionFactory.getCurrentSession().get(User.class, id.longValue());
	}
	
	public void newPed(User u, Pedestrian ped) {
		Long idUser = u.getUserId();
		Long idPed = ped.getPedestrianId();
		String sql = "INSERT INTO User_Pedestrian (userID, pedestrianID) VALUES ( "+ idUser +" , "+ idPed +"  )";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	public void newDriver(User u, Driver driver) {
		Long idUser = u.getUserId();
		Long idDriver = driver.getDriverId();
		String sql = "INSERT INTO User_Driver (userID, driverID) VALUES ( "+ idUser +" , "+ idDriver +"  )";
		Query query  = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
}
