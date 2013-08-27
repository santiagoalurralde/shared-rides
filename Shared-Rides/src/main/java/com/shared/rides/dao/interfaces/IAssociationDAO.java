package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Association;

public interface IAssociationDAO {
	
	public boolean save(Association assoc);
	public Association load(Association assoc);
	public Association delete(Association assoc);
	public List<Association> listAll();

}
