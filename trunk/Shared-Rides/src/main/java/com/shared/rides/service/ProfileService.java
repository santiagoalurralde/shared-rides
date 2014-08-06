package com.shared.rides.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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
	
	public ModelAndView getProfile(long userId, HttpServletRequest req, boolean myProf){
		HttpSession s = req.getSession(false);
		User u = (User)s.getAttribute("user");
		myProfile = myProf;
		
		//Si no es mi profile, entonces veo si tengo asociacion con esa persona
		if(!myProfile){
			for (Association assoc : u.getAssociations()){
				if (assoc.getApplicantID().getUserId() == userId && assoc.getState().equals(State.ACCEPTED)){
					isAssociation = true;
					break;
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
		}
			
		User userAssoc = new User(userId);
		userAssoc = userDAO.load(userAssoc);
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
			model.addObject("picture", "user.jpg");
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
			
			day.put("dayDriver", sch.getDay());
			day.put("hourInDriver", sch.getHourIn());
			day.put("freeSeatsIn", freeSeatsIn);
			day.put("trackIn", getNameTrack(d, sch, 0));
			day.put("hourOutDriver", sch.getHourOut());
			day.put("freeSeatsOut", freeSeatsOut);
			day.put("trackOut", getNameTrack(d, sch, 1));
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
			
			day.put("dayPed", sch.getDay());
			day.put("stopLatIn", stopInAux.getLat());
			day.put("stopLonIn", stopInAux.getLon());
			day.put("hourInPed", sch.getHourIn());
			day.put("hasDriverIn", hasDriver(u, sch, 0));
			day.put("stopLatOut", stopOutAux.getLat());
			day.put("stopLonOut", stopOutAux.getLon());
			day.put("hourOutPed", sch.getHourOut());
			day.put("hasDriverOut", hasDriver(u, sch, 1));

			arraySch.add(day);
		}
		model.addObject("schPed", arraySch);
			
		float latitude = (float) u.getAddress().getMarker().getLatitude();
		float longitude = (float) u.getAddress().getMarker().getLongitude();
		model.addObject("latPed", latitude);
		model.addObject("lonPed", longitude);	
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
	 * Metodo que se va a encargar de efectuar el calculo de la puntuacion de cada usuario cada vez que se
	 * lo puntee
	 * Si me pasa un 0 es un Peaton, sino es un Driver
	 */
	public boolean calculateRating(long requestUserID, long userID, int profile, int rating){
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
