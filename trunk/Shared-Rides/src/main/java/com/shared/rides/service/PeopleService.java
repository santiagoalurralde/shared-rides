package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

@Service
public class PeopleService {

	@Autowired
	private IUserDAO userDAO;
	
	public String getPeople(long userId){
		ModelAndView model = null;
		User u = new User(userId);
		u = userDAO.load(u);
		
		List <Association> assocList = u.getAssociations();
		JsonArray jsonList = new JsonArray();
		JsonArray pendingList = new JsonArray();
		JsonArray acceptedList = new JsonArray();
		
		for(int i = 0; i < assocList.size(); i++){
			Association assoc = assocList.get(i);
			if (assoc.getState().equals(State.PENDING)){
				String fullNameApplier = assoc.getApplier().getName() + assoc.getApplier().getSurname();
				
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("assocId", assoc.getAssociationId());
				jsonUser.addProperty("applierName", fullNameApplier);
				
				pendingList.add(jsonUser);
			}
			if (assoc.getState().equals(State.ACCEPTED)){
				String fullNameApplier = assoc.getApplier().getName() + assoc.getApplier().getSurname();
				String pictureApplier = assoc.getApplier().getPicture();
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("assocId", assoc.getAssociationId());
				jsonUser.addProperty("applierName", fullNameApplier);
				jsonUser.addProperty("picture", pictureApplier);
				
				acceptedList.add(jsonUser);
			}
		}
	
		jsonList.add(pendingList);
		jsonList.add(acceptedList);
		
		return jsonList.toString();
	}
}
