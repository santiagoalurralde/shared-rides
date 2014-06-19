package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

@Service
public class RequestAssociationService {
	
	@Autowired
	private IAssociationDAO assocDAO;
	@Autowired
	private IUserDAO userDAO;
	private User applicantUser;
	private User supplierUser;
	private String message;
	
	/*
	 * Funcion que se usa para verificar si el usuario posee asociaciones nuevas
	 */
	public String hasAssociation(User u){
		List<Association> assocList = u.getAssociations();
		JsonObject json = new JsonObject();
		for(int i = 0; i < assocList.size(); i++){
			if (assocList.get(i).getState().equals(State.PENDING)){
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
	public String sendAssocRequest(int day, int inout, long idUser, long idApplicant){
		message = "No se pudo enviar la solicitud correctamente.";
		//Persona que hace la peticion
		applicantUser = new User();
		applicantUser.setUserId(idApplicant);
		applicantUser = userDAO.load(applicantUser);
		
		//Persona que tiene que responder
		supplierUser = new User();
		supplierUser.setUserId(idUser);
		supplierUser = userDAO.load(supplierUser);
		
		if (validateData(day)){
			Association assoc = new Association();
			assoc.setDay(day);
			assoc.setInout(inout);
			assoc.setApplier(applicantUser);
			assoc.setState(State.PENDING);
			assocDAO.save(assoc);
			userDAO.newAssoc(supplierUser, assoc);
			message = "Se ha enviado la solicitud correctamente.";
		}
		return message;
	}
	
	private boolean validateData(int day){
		if (applicantUser.getPedestrian() != null){
			if (supplierUser.getDriver() != null){
				Pedestrian pedApplicant = applicantUser.getPedestrian();
				Driver driverSupplier = supplierUser.getDriver();
				/*
			 	* Si esto pasa, significa que para el dia en el que se solicito la invitacion, uno es un
			 	* pedestrian y otro es un driver, lo cual esta bien 
			 	*/
				if ( hasSchedule(pedApplicant, day, 0) && hasSchedule(driverSupplier, day, 1) ){
					return true;
				}
			}
		}
		if (applicantUser.getDriver() != null){
			if (supplierUser.getPedestrian() != null){
				Driver driverApplicant = applicantUser.getDriver();
				Pedestrian pedSupplier = supplierUser.getPedestrian();
				/*
			 	* Si esto pasa, significa que para el dia en el que se solicito la invitacion, uno es un
			 	* pedestrian y otro es un driver, lo cual esta bien 
			 	*/
				if ( hasSchedule(driverApplicant, day, 1) && hasSchedule(pedSupplier, day, 0) ){
					return true;
				}
			}
		}
		return false;
	}
	
	/*
	 * Funcion que le paso un objecto (que puede ser un driver o un pedestrian), el dia por el cual quiero buscar
	 * y el profile que me sirve para luego dentro de la funcion saber de que tipo es el parametro objeto
	 * Si retorna -1 quiere decir que ese usuario no tiene un schedule para ese dia
	 */
	private boolean hasSchedule(Object o, int day, int profile){
		List<Schedule> schList;
		if (profile == 0){
			Pedestrian p = (Pedestrian) o;
			schList = p.getSchedule();
		}
		else{
			Driver d = (Driver) o;
			schList = d.getSchedule();
		}
		
		for (int i = 0; i < schList.size(); i++){
			if (schList.get(i).getDay() == day){
				return true;
			}
		}
		return false;
	}
	
}
