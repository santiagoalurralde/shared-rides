package com.shared.rides.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

@Service
public class NotificationService {
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IAssociationDAO assocDAO;
	
	private JsonArray notifications;
	private boolean newNotification;
	
	/*
	 * Metodo que devuelve una lista de notificaciones. Esta lista esta formada por:
	 * 	- Respuestas a las peticiones que yo envie; que puede ser ACEPTADA O RECHAZADA
	 * 	- Peticiones que recibo yo y que tengo que responder. Una vez que las mismas son respondidas, la notificacion desaparece.
	 */
	public String getNotifications(long userId){
		User u = userDAO.load(userId);
		newNotification = false;
		
		//Lista de asociaciones que yo envie, o sea que me tienen que responder
		List<Association> myRequestList = userDAO.getMyRequests(u);
		//Lista de asociaciones que me mandaron, o sea, que yo tengo que responder
		List<Association> assocList = u.getAssociations();
		
		notifications = new JsonArray();
		JsonObject json = new JsonObject();
		
		for(Association assoc : myRequestList){
			if(assoc.getState().equals(State.ACCEPTED) || assoc.getState().equals(State.CANCELLED)){
				if(assoc.getDate().after(u.getLastLoginDate())) {
					newNotification = true;
					
					long uAssocId = assocDAO.getSupplierId(assoc);
					User uAssoc = userDAO.load(uAssocId);
					String fullName = uAssoc.getName() + " " + uAssoc.getSurname();
					JsonObject uJson = new JsonObject();
					
					uJson.addProperty("type", "response");
					uJson.addProperty("name", fullName);
					uJson.addProperty("answer", assoc.getState().toString());
					uJson.addProperty("date", parseDate(assoc.getDate()));
				
					notifications.add(uJson);
				}
			}
		}

		for(int i = 0; i < assocList.size(); i++){
			if (assocList.get(i).getState().equals(State.PENDING)){
				//Si no respondi la nueva solicitud en 10 dias, se cancela automaticamente
				if (assocList.get(i).getDate().compareTo(new Date()) != 10){
					if(assocList.get(i).getDate().after(u.getLastLoginDate())){
						newNotification = true;
						
						JsonObject uJson = new JsonObject();
						User uAssoc = assocList.get(i).getApplicantID();
						String fullName = uAssoc.getName() + " " + uAssoc.getSurname();
						
						uJson.addProperty("type", "request");
						uJson.addProperty("name", fullName);
						uJson.addProperty("date", parseDate(assocList.get(i).getDate()));
						
						notifications.add(uJson);
					}
				}
				else{
					assocList.get(i).setState(State.CANCELLED);
				}

			}
		}
		
		sortNotifications();
			
		json.addProperty("newNotification", newNotification);
		json.add("notifications", notifications);
		
		return json.toString();
	}
	
	private String parseDate(Date date){
		//Guardo la fecha sin horario y con otro formato
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		String dateString = ""+ day + "/" + month + "/" + year;
		
		return dateString;
	}
	
	private void sortNotifications(){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		//Ordeno la lista de notificaciones
		if (notifications.size() != 0){  
			int[] auxList = new int[notifications.size()];
			int i, aux;
			
			//Utilizo una lista auxiliar donde se van a guardar los indices de las notificaciones ordenadas
			for (i = 0; i < notifications.size(); i++){
				auxList[i] = i;
			}
			
			for(i = 0; i < notifications.size()-1; i++){
				for(int j = 0; j < notifications.size()-i-1; j++){
					JsonObject uJson1 = notifications.get(j).getAsJsonObject();
					JsonObject uJson2 = notifications.get(j+1).getAsJsonObject();
					
					String dateInString1 = uJson1.get("date").getAsString();
					String dateInString2 = uJson2.get("date").getAsString();
					
					try{
						Date date1 = formatter.parse(dateInString1);	
						Date date2 = formatter.parse(dateInString2);
						
						if (date2.after(date1)){
							//Si la fecha date2 es anterior que la fecha date1, entonces cambio los indices
							aux = auxList[j+1];
							auxList[j+1] = auxList[j];
							auxList[j] = aux;
						}
					}
					catch(ParseException e){
						e.printStackTrace();
					}
					
					JsonArray auxNotifications = new JsonArray();
					//Reordeno el JsonArray de acuerdo a los indices ordenado en la auxList
					for(int k = 0; k < auxList.length; k++){
						auxNotifications.add(notifications.get(auxList[k]));
					}
					notifications = auxNotifications;
				}
			}	
		}
	}
	
}
