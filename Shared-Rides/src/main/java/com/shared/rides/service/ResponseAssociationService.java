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

	public String hasResponse(long userId){
		User u = new User(userId);
		u = userDAO.load(u);
		
		List<Association> myRequestList = userDAO.getMyRequests(u);
		JsonArray json = new JsonArray();
		
		for(Association assoc : myRequestList){
			if(assoc.getState().equals(State.ACCEPTED) || assoc.getState().equals(State.CANCELLED)){
				if(assoc.getDate().after(u.getLastLoginDate())){
					long uAssocId = assocDAO.getSupplierId(assoc);
					User uAssoc = new User(uAssocId);
					uAssoc = userDAO.load(uAssoc);
					String fullName = uAssoc.getName() + " " + uAssoc.getSurname();
					JsonObject uJson = new JsonObject();
					uJson.addProperty("name", fullName);
					uJson.addProperty("date", assoc.getDate().toString());
					json.add(uJson);
				}
			}
		}
		return json.toString();
	}
	
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
}
