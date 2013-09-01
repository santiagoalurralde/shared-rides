package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Address;

public interface IAddressDAO {

	public boolean save(Address address);
	public Address load(Address address);
	public Address update(Address address);
	public Address delete(Address address);
	public List<Address> listAll();
	
}
