package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.User;

public interface IUserDAO {
	
	public boolean save(User user);
	public User load(User user);
	public User update(User user);
	public User delete(User user);
	public List<User> listAll();
	public User loadByEmail(User user);

}
