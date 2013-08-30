package com.shared.rides.dao.persistence;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shared.rides.dao.interfaces.ITrackDAO;
import com.shared.rides.domain.Marker;
import com.shared.rides.domain.Track;


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

}
