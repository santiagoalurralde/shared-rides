package com.shared.rides.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IScheduleDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

@Service
public class ResponseAssociationService {

	@Autowired
	private IAssociationDAO assocDAO;
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired 
	private IScheduleDAO scheduleDAO;
	
	private JsonObject json;
	private JsonArray requestedJson;
	private JsonArray offeredJson;
	private List<Long> schIdList;
	
	/*
	 * Metodo que se encarga de devolver la lista de horarios entre dos usuarios para mostrarlo en la vista
	 * Si el tipo de asociacion es 0 --> Pendiente
	 * Si es 1 --> Asociado
	 */
	public String showAssociationSchedule(long requestUserId, long assocUserId, int assocType){
		User requestUser = new User(requestUserId);
		requestUser = userDAO.load(requestUser);
		
		json = new JsonObject();
		requestedJson = new JsonArray();
		offeredJson = new JsonArray();
		List<Association> assocList = requestUser.getAssociations();
		schIdList =  userDAO.getAllSchedule(requestUser);
		
		Collections.sort(assocList, new Comparator() {
			public int compare(Object a1, Object a2) {
				Association assoc1 = (Association) a1;
				Association assoc2 = (Association) a2;
				return new Integer(assoc1.getDay()).compareTo(new Integer(assoc2.getDay()));
			}
		});

		//Si es 0 es porque es pendiente
		if (assocType == 0){
			for (int i = 0; i < assocList.size(); i++){
				User assocUser = assocList.get(i).getApplicantID();
				if (assocUser.getUserId() == assocUserId && assocList.get(i).getState().equals(State.PENDING)){
					completeJson(assocList.get(i));
				}
			}
		}
		/*else{
			//Lista de todas las peticiones que yo realice
			List<Association> myRequestsList = userDAO.getMyRequests(requestUser);
			
			for (int i = 0; i < assocList.size(); i++){
				User assocUser = assocList.get(i).getApplier();
				if (assocUser.getUserId() == assocUserId && assocList.get(i).getState().equals(State.ACCEPTED)){
					completeJson(assocList.get(i));
				}
			}
			for (int j = 0; j < myRequestsList.size(); j++){
				//Obtengo el id del usuario al cual le envie la peticion
				long userId = assocDAO.getSupplierId(myRequestsList.get(j));
				
				if (userId == assocUserId && myRequestsList.get(j).getState().equals(State.ACCEPTED)){
					completeJson(assocList.get(j));
				}
			}
		}*/
		json.add("requested", requestedJson);
		json.add("offered", offeredJson);
		
		return json.toString();
	}
	
	private void completeJson(Association assoc){
		int requestedOfferedFlag = 0;
		JsonObject jsonSchedule = new JsonObject();
		
		jsonSchedule.addProperty("assocId", assoc.getAssociationId());
		jsonSchedule.addProperty("day", assoc.getDay());
		jsonSchedule.addProperty("inout", assoc.getInout());
		
		for (int j = 0; j < schIdList.size(); j++){
			Schedule sch = new Schedule(schIdList.get(j));
			sch = scheduleDAO.load(sch);
			if (assoc.getDay() == sch.getDay()){
				if (assoc.getInout() == 1) jsonSchedule.addProperty("hour", sch.getHourIn());
				else jsonSchedule.addProperty("hour", sch.getHourOut());
				break;
			}
		}
		/*
		 * Si es 1; solicito un asiento, es decir yo soy conductor
		 * Si es 2; ofrece asiento, soy peaton
		 */
		if (assoc.getApplicantID().getDriver() != null){
			boolean flag = false;
			List<Schedule> auxSch = assoc.getApplicantID().getDriver().getSchedule();
			for (int i = 0; i < auxSch.size(); i++){
				if(auxSch.get(i).getDay() == assoc.getDay()){
					requestedOfferedFlag = 2;
					flag = true;
					break;
				}
			}
			//Si el aplicante es conductor, pero en ese dia no lo es, quiere decir que le solicito un asiento
			if (!flag) requestedOfferedFlag = 1; 
		}
		else requestedOfferedFlag = 1;
		
		if (requestedOfferedFlag == 1) requestedJson.add(jsonSchedule);
		if (requestedOfferedFlag == 2) offeredJson.add(jsonSchedule);
	}
	
	/*
	 * Metodo que se encarga de cambiar el estado de la asociacion cuando un usuario responde a una solicitud
	 */
	public String sendResponseAssoc(long assocId, boolean response){
		Association assoc = new Association(assocId);
		assoc = assocDAO.load(assoc);
		Date date = new Date();
		String msg = null;
		
		if (response == true){
			assoc.setState(State.ACCEPTED);
			assoc.setDate(date);
			msg = "El usuario ha aceptado la solicitud";
		}
		else{
			assoc.setState(State.CANCELLED);
			msg = "El usuario ha rechazado la solicitud";
		}
		assocDAO.update(assoc);
			
		return msg;
	}
	
	/*
	 * Metodo que se va a encargar de efectuar el calculo de la puntuacion de cada usuario cada vez que se
	 * lo puntee
	 * Si me pasa un 0 es un Peaton, sino es un Driver
	 */
	public boolean calculateRating(long requestUserID, long userID, int profile, float rating){
		User u = new User(userID);
		u = userDAO.load(u);
		User requestUser = new User(requestUserID);
		requestUser = userDAO.load(requestUser);
		boolean isValidate = false;
		
		/*
		 * Primero voy a ver si la asociacion entre las dos personas es mayor a 21 dias (3 semanas),
		 * para permitir que la persona puede puntuar
		 */
		Date actualDate = new Date();
		List<Association> assocList = requestUser.getAssociations();
		List<Association> myRequestsList = userDAO.getMyRequests(requestUser);
		
		for (int i = 0; i < assocList.size(); i++){
			User assocUser = assocList.get(i).getApplicantID();
			if (assocUser.getUserId() == userID && assocList.get(i).getState().equals(State.ACCEPTED)){
				int diff = (int) ((assocList.get(i).getDate().getTime() - actualDate.getTime()) / (24 * 60 * 60 * 1000));
				if (diff > 21){
					isValidate = true;
					break;
				}
			}
		}
		for (int j = 0; j < myRequestsList.size(); j++){
			//Obtengo el id del usuario al cual le envie la peticion
			long userAssocId = assocDAO.getSupplierId(myRequestsList.get(j));
			if (userID == userAssocId && myRequestsList.get(j).getState().equals(State.ACCEPTED)){
				int diff = (int) ((assocList.get(j).getDate().getTime() - actualDate.getTime()) / (24 * 60 * 60 * 1000));
				if (diff > 21){
					isValidate = true;
					break;
				}
			}
		}
		
		if(isValidate){
			if (profile == 0){
				float auxRating = u.getPedestrian().getRating();
				auxRating = (auxRating + rating) / 2;
				u.getPedestrian().setRating(auxRating);
			}
			else{
				float auxRating = u.getDriver().getRating();
				auxRating = (auxRating + rating) / 2;
				u.getDriver().setRating(auxRating);
				}
			userDAO.update(u);
			return true;
		}
		return false;
	}
	
}
