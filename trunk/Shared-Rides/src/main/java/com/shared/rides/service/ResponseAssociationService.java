package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

@Service
public class ResponseAssociationService {

	@Autowired
	private IAssociationDAO assocDAO;
	
	/*
	 * Metodo que se encarga de devolver la lista de horarios entre dos usuarios para mostrarlo en la vista
	 * Si el tipo de asociacion es 0 --> Pendiente
	 * Si es 1 --> Asociado
	 */
	public String showAssociationSchedule(User requestUser, User assocUser, int assocType){
		
		List<Long> assocIdList = assocDAO.findAssoc(requestUser, assocUser);
		JsonArray json = new JsonArray();
		
		if (assocType == 0){
			for (int i = 0; i < assocIdList.size(); i++){
				Association assoc = new Association(assocIdList.get(i));
				assoc = assocDAO.load(assoc);
				
				if(assoc.getState().equals(State.PENDING)){
					JsonObject jsonSchedule = new JsonObject();
					jsonSchedule.addProperty("day", assoc.getDay());
					jsonSchedule.addProperty("inout", assoc.getInout());
					jsonSchedule.addProperty("applicant", assoc.getApplier().getUserId());
				
					json.add(jsonSchedule);
				}	
			}	
		}
		else{
			for (int i = 0; i < assocIdList.size(); i++){
				Association assoc = new Association(assocIdList.get(i));
				assoc = assocDAO.load(assoc);
				
				if(assoc.getState().equals(State.ACCEPTED)){
					JsonObject jsonSchedule = new JsonObject();
					jsonSchedule.addProperty("day", assoc.getDay());
					jsonSchedule.addProperty("inout", assoc.getInout());
					
					json.add(jsonSchedule);
				}	
			}
		}

		return json.toString();
	}
	
	/*
	 * Metodo que se encarga de cambiar el estado de la asociacion cuando un usuario responde a una solicitud
	 */
	public String sendResponseAssoc(long assocId, boolean response){
		Association assoc = new Association(assocId);
		assoc = assocDAO.load(assoc);
		
		String msg = null;
		
		if (response == true){
			assoc.setState(State.ACCEPTED);
			msg = "El usuario ha aceptado la solicitud";
		}
		else{
			assoc.setState(State.CANCELLED);
			msg = "El usuario ha rechazado la solicitud";
		}
		assocDAO.update(assoc);
		
		return msg;
	}
}
