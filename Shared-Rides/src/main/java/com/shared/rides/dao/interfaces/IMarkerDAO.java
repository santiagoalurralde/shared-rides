package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Marker;

public interface IMarkerDAO {

	public boolean save(Marker marker);
	public Marker load(Marker marker);
	public Marker update(Marker marker);
	public Marker delete(Marker marker);
    public List<Marker> listAll();
}
