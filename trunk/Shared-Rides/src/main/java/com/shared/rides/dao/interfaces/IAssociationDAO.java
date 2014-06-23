package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Association;
import com.shared.rides.domain.User;

public interface IAssociationDAO {
	
	public boolean save(Association assoc);
	public Association load(Association assoc);
	public Association update(Association assoc);
	public Association delete(Association assoc);
	public List<Association> listAll();
	public long getSupplierId(Association assoc);
	public List<Long> findAssoc (User requestUser, User assocUser);

}
