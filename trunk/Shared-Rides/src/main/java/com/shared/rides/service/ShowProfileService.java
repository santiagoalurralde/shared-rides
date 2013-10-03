package com.shared.rides.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.User;


@Service
public class ShowProfileService {

	@Autowired
	private IUserDAO userDAO;
	private boolean isAssociation = false;
	
	public User showProfile(int userId, HttpServletRequest req){
		
		HttpSession s = req.getSession(false);
		User u = (User)s.getAttribute("user");
		
		for (Association assoc : u.getAssociations()){
			if (assoc.getApplier().getUserId() == userId){
				isAssociation = true;
			}
		}
		
		/*
		 * TODO: Hay que ver lo que la vista necesita para mostrar; tanto cuando
		 * se debe mostrar tanto informacion publica y privada; como cuando solo
		 * hay que mostrar los datos publicos.
		 */
		
		User userAssoc = new User();
		//Si la asociacion existe entonces devolvemos toda la informacion
		//necesaria del usuario asociado.
		if(isAssociation){
			userAssoc.setUserId(userId);
			userAssoc = userDAO.load(userAssoc);
			return userAssoc;
		}
		//Sino tengo que filtrar informacion y devolver solamente la informacion publica
		//del usuario asociado.
		
		return userAssoc;
	}
	
}
