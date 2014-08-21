package com.shared.rides.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.User;


@Service
public class LoginService {

	@Autowired
	private IUserDAO userDAO;
	
	private String password;
	
	public boolean validate(String email, String pw, HttpServletRequest r){
		//Busco en la base de datos si se encuentra el usuario con ese email
		User u = new User();
		u.setEmail(email);
		u = userDAO.loadByEmail(u);
		//Si el usuario no es null, quiere decir que existe
		if (u != null){
			password = u.getPw().toString();
			if (password.equals(pw)){
				//Seteamos a la session el atributo user	    		
				HttpSession session = r.getSession(false);
				session.setAttribute("user", u);
				return true;
			}
			else return false;
		}
		return false;
	}
	
	public void saveLastLoginDate(long userId){
		User u = new User(userId);
		u = userDAO.load(u);
		u.setLastLoginDate(new Date());
		userDAO.update(u);		
	}
	
}
