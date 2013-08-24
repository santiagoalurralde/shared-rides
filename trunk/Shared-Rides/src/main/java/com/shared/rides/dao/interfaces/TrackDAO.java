package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Track;

public interface TrackDAO {

	public boolean save(Track track);
	public Track load(Track track);
	public Track delete(Track track);
	public List<Track> listAll();
}
