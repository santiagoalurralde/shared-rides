package com.shared.rides.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;
import com.shared.rides.util.EmailSender;

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
	public String hasAssociation(long userId){
		User u = userDAO.load(userId);
		
		List<Association> assocList = u.getAssociations();
		JsonArray json = new JsonArray();
		
		for(int i = 0; i < assocList.size(); i++){
			if (assocList.get(i).getState().equals(State.PENDING)){
				if (assocList.get(i).getDate().compareTo(new Date()) != 10){
					JsonObject uJson = new JsonObject();
					User uAssoc = assocList.get(i).getApplicantID();
					String fullName = uAssoc.getName() + " " + uAssoc.getSurname();
					uJson.addProperty("name", fullName);
					uJson.addProperty("date", assocList.get(i).getDate().toString());
					json.add(uJson);
				}
				else assocList.get(i).setState(State.CANCELLED);
			}
		}
		return json.toString();
	}
	
	/*
	 * Funcion que se usa cuando el usuario envia una peticion de asociacion a otro usuario
	 * A la hora de setear el inout; se setea un 0 si es in o un 1 si es out
	 */
	public String sendAssocRequest(int day, int inout, long idUser, long idApplicant){
		Date date = new Date();
		message = "No se pudo enviar la solicitud correctamente.";
		//Persona que hace la peticion
		applicantUser = userDAO.load(idApplicant);
		
		//Persona que tiene que responder
		supplierUser = userDAO.load(idUser);
		
		if (validateData(day)){
			if(validateAssoc(day, inout)){
				Association assoc = new Association();
				assoc.setDay(day);
				assoc.setInout(inout);
				assoc.setApplicantID(applicantUser);
				assoc.setState(State.PENDING);
				assoc.setDate(date);
				assocDAO.save(assoc);
				userDAO.newAssoc(supplierUser, assoc);
				message = "Se ha enviado la solicitud correctamente.";
				
				try{
					EmailSender emailSender = new EmailSender();
					emailSender.sendEmail(supplierUser.getEmail(), applicantUser.getEmail(), applicantUser.getPw());					
				}
				catch(Exception e){
					System.out.println(e);
				}

				
			}
			else message = "Esta peticion ya se ha realizado anteriormente";
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
	
	private boolean validateAssoc(int day, int inout){
		boolean isValidate = true;
		
		List<Association> myRequestList = userDAO.getMyRequests(applicantUser);
		
		for(Association assoc : myRequestList){
			long supplierId = assocDAO.getSupplierId(assoc);
			if((assoc.getDay() == day) && (assoc.getInout() == inout) && (supplierUser.getUserId() == supplierId)) isValidate = false;
		}	
		
		return isValidate;
	}
}
