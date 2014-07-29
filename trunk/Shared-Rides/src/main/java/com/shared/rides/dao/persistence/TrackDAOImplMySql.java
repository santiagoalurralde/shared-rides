package com.shared.rides.dao.persistence;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.ITrackDAO;
import com.shared.rides.domain.Marker;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;


@Repository
@Transactional
public class TrackDAOImplMySql implements ITrackDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(Track track) {
		sessionFactory.getCurrentSession().save(track);
		return false;
	}

	public Track load(Track track) {
		return (Track) sessionFactory.getCurrentSession().get(Track.class, track.getTrackId());	
	}

	public Track delete(Track track) {
		sessionFactory.getCurrentSession().delete(track);
		return null;
	}

	public List<Track> listAll() {
		Query query = sessionFactory.getCurrentSession().createQuery("FROM Track");     
		List<Track> tracks = query.list();
		return tracks;
	}

	public Track update(Track track) {
		sessionFactory.getCurrentSession().update(track);
		return null;
	}

	public Track getLastTrack() {
		String sql = "SELECT MAX(id) FROM Track";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		BigInteger id = (BigInteger) query.list().get(0);
		return (Track) sessionFactory.getCurrentSession().get(Track.class, id.longValue());
	}

}
