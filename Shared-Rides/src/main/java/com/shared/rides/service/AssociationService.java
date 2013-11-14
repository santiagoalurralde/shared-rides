package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.User;

@Service
public class AssociationService {
	
	@Autowired
	IAssociationDAO assocDAO;
	@Autowired
	IUserDAO userDAO;
	
	/*
	 * Funcion que se usa para verificar si el usuario posee asociaciones nuevas
	 */
	public boolean hasAssociation(User u){
		List<Association> assocList = u.getAssociations();
		JsonObject json = new JsonObject();
		
		for(int i = 0; i < assocList.size(); i++){
			if (assocList.get(i).getState().getStateName().equals("Pendiente")){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Funcion que se usa cuando el usuario envia una peticion de asociacion a otro usuario
	 */
	public void sendAssocRequest(int day, int inout, long idUser, long idApplicant){
	}
}
