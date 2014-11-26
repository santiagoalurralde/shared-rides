package com.shared.rides.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
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
		User requestUser = userDAO.load(requestUserId);
		
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
		else{
			//Lista de todas las peticiones que yo realice
			List<Association> myRequestsList = userDAO.getMyRequests(requestUser);
			
			for (int i = 0; i < assocList.size(); i++){
				User assocUser = assocList.get(i).getApplicantID();
				if (assocUser.getUserId() == assocUserId && assocList.get(i).getState().equals(State.ACCEPTED)){
					completeJson(assocList.get(i));
				}
			}
			for (int j = 0; j < myRequestsList.size(); j++){
				//Obtengo el id del usuario al cual le envie la peticion
				long userId = assocDAO.getSupplierId(myRequestsList.get(j));
				
				if (userId == assocUserId && myRequestsList.get(j).getState().equals(State.ACCEPTED)){
					completeJson(myRequestsList.get(j));
				}
			}
		}
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
			Schedule sch = scheduleDAO.load(schIdList.get(j));
			if (assoc.getDay() == sch.getDay()){
				if (assoc.getInout() == 0) jsonSchedule.addProperty("hour", sch.getHourIn());
				else jsonSchedule.addProperty("hour", sch.getHourOut());
				break;
			}
		}
		/*
		 * Si es 0; solicito un asiento, es decir yo soy conductor
		 * Si es 1; ofrece asiento, soy peaton
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
	public String sendResponseAssoc(long assocId, boolean response, User u){
		Association assoc = assocDAO.load(assocId);
		Date date = new Date();
		String msg = null;
		
		if (response == true){
			assoc.setState(State.ACCEPTED);
			assoc.setDate(date);			
			msg = "El usuario ha aceptado la solicitud";
		}
		else{
			assoc.setState(State.CANCELLED);
			if (!(assoc.getApplicantID().getUserId() == u.getUserId())){
				assoc.setDate(date);
			}
			msg = "El usuario ha rechazado la solicitud";
		}
		assocDAO.update(assoc);
/*			
		try{
			EmailSender emailSender = new EmailSender();
			emailSender.sendEmail(assoc.getApplicantID(), u, 1);					
		}
		catch(Exception e){
			System.out.println(e);
		}
	*/	
		return msg;
	}
}
