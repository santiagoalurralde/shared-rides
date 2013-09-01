package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Organization;

public interface IOrganizationDAO {

	public boolean save(Organization org);
	public Organization load(Organization org);
	public Organization update(Organization org);
	public Organization delete(Organization org);
	public List<Organization> listAll();
}
