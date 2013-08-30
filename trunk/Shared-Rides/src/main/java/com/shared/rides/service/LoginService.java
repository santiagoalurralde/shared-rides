package com.shared.rides.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.User;


@Service
public class LoginService {

	@Autowired
	private IUserDAO userDAO;
	String password;
	
	public boolean validate(String email, String pw){
		//Busco en la base de datos si se encuentra el usuario con ese email
		User u = new User();
		u.setEmail(email);
		u = userDAO.loadByEmail(u);
		//Si el usuario no es null, quiere decir que existe
		if (u != null){
			password = u.getPw().toString();
			if (password.equals(pw)) return true;
			else return false;
		}
		return false;
	}
	
}
