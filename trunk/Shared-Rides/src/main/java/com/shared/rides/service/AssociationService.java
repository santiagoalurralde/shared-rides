package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.User;

public class AssociationService {
	
	@Autowired
	IAssociationDAO assocDAO;
	@Autowired
	IUserDAO userDAO;
	
	/*
	 * Funcion que se usa para verificar si el usuario posee asociaciones nuevas
	 */
	public String hasAssociation(User u){
		List<Association> assocList = u.getAssociations();
		JsonObject json = new JsonObject();
		
		for(int i = 0; i < assocList.size(); i++){
			if (assocList.get(i).getState().getStateName().equals("Pendiente")){
				json.addProperty("hasAssoc", true);
				return json.toString();
			}
		}
		json.addProperty("hasAssoc", false);
		return json.toString();
	}
	
	/*
	 * Funcion que se usa cuando el usuario envia una peticion de asociacion a otro usuario
	 */
	public void sendAssocRequest(int day, int inout, long idUser, long idApplicant){
	}
}
