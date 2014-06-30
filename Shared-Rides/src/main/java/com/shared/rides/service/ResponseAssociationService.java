package com.shared.rides.service;

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
	
	private JsonArray json = new JsonArray();
	private List<Long> schIdList;
	/*
	 * Metodo que se encarga de devolver la lista de horarios entre dos usuarios para mostrarlo en la vista
	 * Si el tipo de asociacion es 0 --> Pendiente
	 * Si es 1 --> Asociado
	 */
	public String showAssociationSchedule(User requestUser, long assocUserId, int assocType){			
		List<Association> assocList = requestUser.getAssociations();
		schIdList =  userDAO.getAllSchedule(requestUser);
		
		//Si es 0 es porque es pendiente
		if (assocType == 0){
			for (int i = 0; i < assocList.size(); i++){
				User assocUser = assocList.get(i).getApplier();
				if (assocUser.getUserId() == assocUserId && assocList.get(i).getState().equals(State.PENDING)){
					completeJson(assocList.get(i));					
				}
			}
		}
		else{
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
		}
		return json.toString();
	}
	
	private void completeJson(Association assoc){
		JsonObject jsonSchedule = new JsonObject();
		
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
			/*
			 * Si es 1; solicito un asiento, es decir yo soy conductor
			 * Si es 2; ofrece asiento, soy peaton
			 */
			if (assoc.getApplier().getDriver() != null){
				boolean flag = false;
				List<Schedule> auxSch = assoc.getApplier().getDriver().getSchedule();
				for (int i = 0; i < auxSch.size(); i++){
					if(auxSch.get(i).getDay() == assoc.getDay()){
						jsonSchedule.addProperty("askedOffered", 2);
						flag = true;
						break;
					}
				}
				//Si el aplicante es conductor, pero en ese dia no lo es, quiere decir que le solicito un asiento
				if (!flag) jsonSchedule.addProperty("askedOffered", 1); 
			}
			else	jsonSchedule.addProperty("askedOffered", 1);
		}
		json.add(jsonSchedule);
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
