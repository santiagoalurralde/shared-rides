package com.shared.rides.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IPedestrianDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.State;
import com.shared.rides.domain.Stop;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;

/*
 * Servicio que se va a encargar de generar el Json que tiene que devolver, cuando se 
 * desee ver el perfil de una persona o el propio
 * Para esto existe un metodo "showProfile" que recibe el id del usuario a ver, el
 * request que solicita eso y si lo que deseo ver es mi id o no.
 * Luego dependiendo si hay o no una asociacion entre ambas personas se van a filtrar
 * datos o no.
 * Los datos privados, que solamente se muestran si hay asociacion entre ambas personas o
 * si quiero ver mi perfil son:
 * - Foto
 * - Telefono
 * - Email
 * - Vehiculo
 * - Patente
 */
@Service
public class ProfileService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IPedestrianDAO pedDAO;
	@Autowired
	private IAssociationDAO assocDAO;
	
	private boolean isAssociation = false;
	private boolean myProfile;
	private ModelAndView model;
	private long userLogInId;
	
	public ModelAndView getProfile(long userId, HttpServletRequest req, boolean myProf){	
		HttpSession s = req.getSession(false);
		User u = (User)s.getAttribute("user");
		this.userLogInId = u.getUserId();
		myProfile = myProf;
		
		//Si no es mi profile, entonces veo si tengo asociacion con esa persona
		if(!myProfile){
			for (Association assoc : u.getAssociations()){
				if (assoc.getApplicantID().getUserId() == userId && assoc.getState().equals(State.ACCEPTED)){
					isAssociation = true;
					break;
				}
			}
			
			if (!isAssociation){
				List<Association> myRequestsList = userDAO.getMyRequests(u);
				for (int j = 0; j < myRequestsList.size(); j++){
					//Obtengo el id del usuario al cual le envie la peticion
					long supplierId = assocDAO.getSupplierId(myRequestsList.get(j));
					if (userId == supplierId && myRequestsList.get(j).getState().equals(State.ACCEPTED)){
						isAssociation = true;
						break;
					}
				}
			}
			
		User userAssoc = userDAO.load(userId);
		createModel(userAssoc);		
		return model;
		}
		
		createModel(u);	
		return model;
	}
	
	private void createModel(User u){
		//json object para el usuario general
		model = new ModelAndView();
		
		model.addObject("id", u.getUserId());
		model.addObject("name", u.getName());
		model.addObject("surname", u.getSurname());
		model.addObject("shift", u.getShift().getShiftName());
		
		String street = u.getAddress().getStreet();
		int numberStreet = u.getAddress().getNumber();
		model.addObject("address", street + " " + numberStreet);
		model.addObject("neighborhood", u.getAddress().getNeighborhood());
		
		//Agrego los datos del driver si el usuario es uno.
		if(u.getDriver() != null){
			model.addObject("driver", "true");
			addModelDriver(u);
		}
		else{
			model.addObject("driver", "false");
		}
		
		//Agrego los datos del pedestrian si el usuario es uno.
		if(u.getPedestrian() != null){
			model.addObject("pedestrian", "true");
			addModelPedestrian(u);
		}
		else{
			model.addObject("pedestrian", "false");
		}
		
		//Agrego boolean que indica si es mi perfil o no.
		model.addObject("mine", myProfile);
		
		//Agrego los datos privados del usuario en caso de que existe la asociacion
		if(isAssociation || myProfile){
			model.addObject("visible", true);	
			model.addObject("telephone", u.getPhoneNumber());
			model.addObject("email", u.getEmail());
			model.addObject("picture", u.getPicture());
		}
		else{
			model.addObject("visible", false);			
			model.addObject("picture", "user.png");
		}
	}
	
	private void addModelDriver(User u){
		Driver d = u.getDriver();
		model.addObject("idDriver", d.getDriverId());
		model.addObject("ratingDriver", d.getRating());
		
		int totalSeats = d.getVehicle().getSeats();
		//Agrego el horario al objecto jsonDriver
		ArrayList arraySch = new ArrayList();
		for(int i = 0; i < d.getSchedule().size(); i++){	
			int freeSeatsIn = calculateFreeSeats(u, d.getSchedule().get(i), totalSeats, 0);
			int freeSeatsOut = calculateFreeSeats(u, d.getSchedule().get(i), totalSeats, 1);
			Schedule sch = d.getSchedule().get(i);
			Map<String, Object> day = new HashMap();
			//TRACK IN
			day.put("dayDriver", sch.getDay());
			day.put("hourInDriver", sch.getHourIn());
			day.put("freeSeatsIn", freeSeatsIn);
			day.put("trackIn", getNameTrack(d, sch, 0));
			day.put("allowIn", allowRequest(u.getUserId(), sch.getDay(), 0));
			//TRACK OUT
			day.put("hourOutDriver", sch.getHourOut());
			day.put("freeSeatsOut", freeSeatsOut);
			day.put("trackOut", getNameTrack(d, sch, 1));
			day.put("allowOut", allowRequest(u.getUserId(), sch.getDay(), 1));
			
			arraySch.add(i, day);
		}
		//Agrego el horario
		model.addObject("schDriver", arraySch);
		
		//Agrego los datos privados del driver egetNameTrack(d, sch, 0));n caso de que exista la asociacion
		if(isAssociation || myProfile){
			model.addObject("vehicle", d.getVehicle().getModel());
			model.addObject("licensePlate", d.getVehicle().getLicensePlate());
		}
	}
	
	private void addModelPedestrian(User u){
		Pedestrian p = u.getPedestrian();
		
		model.addObject("idPedestrian", p.getPedestrianId());
		model.addObject("ratingPedestrian", p.getRating());
		
		ArrayList arraySch = new ArrayList();
		for(int i = 0; i < p.getSchedule().size(); i++){
			Schedule sch = p.getSchedule().get(i);
			Map<String, Object> day = new HashMap();
			
			Stop stopInAux = getStopPed(p, sch, 0);
			Stop stopOutAux = getStopPed(p, sch, 1);
			//STOP IN
			day.put("dayPed", sch.getDay());
			day.put("stopLatIn", stopInAux.getLat());
			day.put("stopLonIn", stopInAux.getLon());
			day.put("hourInPed", sch.getHourIn());
			day.put("hasDriverIn", hasDriver(u, sch, 0));
			day.put("allowIn", allowRequest(u.getUserId(), sch.getDay(), 0));
			//STOP OUT
			day.put("stopLatOut", stopOutAux.getLat());
			day.put("stopLonOut", stopOutAux.getLon());
			day.put("hourOutPed", sch.getHourOut());
			day.put("hasDriverOut", hasDriver(u, sch, 1));
			day.put("allowOut", allowRequest(u.getUserId(), sch.getDay(), 1));

			arraySch.add(day);
		}
		model.addObject("schPed", arraySch);	
	}
	
	/*
	 * Funcion que va a calcular los asientos libres	
	 */
	private int calculateFreeSeats(User u, Schedule sch, int seats, int inout){
		List<Association> assoc = u.getAssociations();
		
		for (int j = 0; j <assoc.size(); j++){
			if (sch.getDay() == assoc.get(j).getDay() 
					&& assoc.get(j).getInout() == inout 
					&& assoc.get(j).getState().equals(State.ACCEPTED)) 
				seats--;
		}	
		return seats;
	}
	
	/*
	 * Funcion para ver si ese peaton ya tiene conductor en ese dia
	 */
	private boolean hasDriver(User u, Schedule sch, int inout){
		boolean hasDriver = false;
		List<Association> assoc = u.getAssociations();
		for (int j = 0; j <assoc.size(); j++){
			if (sch.getDay() == assoc.get(j).getDay() 
					&& assoc.get(j).getInout() == inout 
					&& assoc.get(j).getState().equals(State.ACCEPTED)) 
				hasDriver = true;
		}
		return hasDriver;
	}
	
	/*
	 * Funcion que va a devolver el nombre del track correspondiente a ese horario
	 * in -> 0
	 * out -> 1
	 */
	private String getNameTrack(Driver d, Schedule sch, int inout){
		String nameTrack = null;
		
		for (int i = 0; i < d.getTracks().size(); i++){
			Track track = d.getTracks().get(i);
			if (track.getDay() == sch.getDay()
				&& track.getInout() == inout){
				nameTrack = track.getPathFile();
				break;
			}
		}
		return nameTrack;
	}
	
	private Stop getStopPed(Pedestrian p, Schedule sch, int inout){
		for (int i = 0; i < p.getStops().size(); i++){
			Stop stop = p.getStops().get(i);
			if (stop.getDay() == sch.getDay() && stop.getInout() == inout){
				return stop;
			}
		}
		return null;
	}
	
	/*
	 * Metodo que verifica si ya hay asociacion entre esas dos personas para ese dia y horario; para que no se pueda mandar
	 * dos veces una misma asociacion
	 */
	private boolean allowRequest(long userId, int day, int inout){
		User user = userDAO.load(this.userLogInId);
		User userAssoc = userDAO.load(userId);
		
		if(userId == this.userLogInId){
			return false;
		}
		else{
			for(Association a : user.getAssociations()){
				if (a.getDay() == day && 
					a.getInout() == inout && 
					!(a.getState().equals(State.CANCELLED)) &&
					a.getApplicantID().getUserId() == userId){
						return false; 
				}
			}
			for(Association a : userAssoc.getAssociations()){
				if (a.getDay() == day && 
					a.getInout() == inout && 
					!(a.getState().equals(State.CANCELLED)) &&
					a.getApplicantID().getUserId() == this.userLogInId){
						return false;			
				}
			}	
			
			if (validateData(day, userLogInId, userId) == false){
				return false; 
			}
		}
		return true;
	}
	
	
	/*
	 * Metodo que se va a encargar de efectuar el calculo de la puntuacion de cada usuario cada vez que se
	 * lo puntee
	 * Si me pasa un 0 es un Peaton, sino es un Driver
	 */
	public boolean calculateRating(long requestUserID, long userID, int profile, int rating){
		User u = userDAO.load(userID);
		User requestUser = userDAO.load(requestUserID);
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
	
	private boolean validateData(int day, long userLogInId, long userId){
		User userLogIn = userDAO.load(this.userLogInId);
		User user = userDAO.load(userId);
		
		if (userLogIn.getPedestrian() != null){
			if (user.getDriver() != null){
				Pedestrian pedApplicant = userLogIn.getPedestrian();
				Driver driverSupplier = user.getDriver();
				/*
			 	* Si esto pasa, significa que para el dia en el que se solicito la invitacion, uno es un
			 	* pedestrian y otro es un driver, lo cual esta bien 
			 	*/
				if ( hasSchedule(pedApplicant, day, 0) && hasSchedule(driverSupplier, day, 1) ){
					return true;
				}
			}
		}
		if (userLogIn.getDriver() != null){
			if (user.getPedestrian() != null){
				Driver driverApplicant = userLogIn.getDriver();
				Pedestrian pedSupplier = user.getPedestrian();
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
	
	public String getNotifications(long userId){
		User u = userDAO.load(userId);
		boolean newNotification = false;
		
		//Lista de asociaciones que yo envie, o sea que me tienen que responder
		List<Association> myRequestList = userDAO.getMyRequests(u);
		JsonArray notifications = new JsonArray();
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
					uJson.addProperty("date", assoc.getDate().toString());
					notifications.add(uJson);
				}
			}
		}
			
		//Lista de asociaciones que me mandaron, o sea, que yo tengo que responder
		List<Association> assocList = u.getAssociations();
		
		for(int i = 0; i < assocList.size(); i++){
			if (assocList.get(i).getState().equals(State.PENDING)){
				//Si no respondi la nueva solicitud en 10 dias, se cancela automaticamente
				if (assocList.get(i).getDate().compareTo(new Date()) != 10){
					JsonObject uJson = new JsonObject();
					User uAssoc = assocList.get(i).getApplicantID();
					String fullName = uAssoc.getName() + " " + uAssoc.getSurname();
					uJson.addProperty("type", "request");
					uJson.addProperty("name", fullName);
					
					//Guardo la fecha sin horario y con otro formato
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(assocList.get(i).getDate());
					int year = calendar.get(Calendar.YEAR);
					int month = calendar.get(Calendar.MONTH);
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					
					String date = ""+ day + "/" + month + "/" + year;
					
					uJson.addProperty("date", date);
					notifications.add(uJson);
				}
				else{
					assocList.get(i).setState(State.CANCELLED);
				}
				if(assocList.get(i).getDate().after(u.getLastLoginDate())) newNotification = true;
			}
		}
		
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
			
		json.addProperty("newNotification", newNotification);
		json.add("notifications", notifications);
		
		return json.toString();
	}
	
}
