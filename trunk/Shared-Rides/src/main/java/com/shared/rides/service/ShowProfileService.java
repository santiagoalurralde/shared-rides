package com.shared.rides.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.dao.interfaces.IDriverDAO;
import com.shared.rides.dao.interfaces.IPedestrianDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
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
public class ShowProfileService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IPedestrianDAO pedDAO;
	private boolean isAssociation = false;
	private boolean myProfile;
	private ModelAndView model;
	
	public ModelAndView getProfile(int userId, HttpServletRequest req, boolean myProf){
		HttpSession s = req.getSession(false);
		User u = (User)s.getAttribute("user");
		myProfile = myProf;
		
		//Si no es mi profile, entonces veo si tengo asociacion con esa persona
		if(!myProfile){
		for (Association assoc : u.getAssociations()){
			if (assoc.getApplier().getUserId() == userId && assoc.getState().getStateName().equals("Aceptado")){
				isAssociation = true;
			}
		}
		
		User userAssoc = new User();
		userAssoc.setUserId(userId);
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
		//Agrego los datos privados del usuario en caso de que existe la asociacion
		if(isAssociation || myProfile){
			model.addObject("telephone", u.getPhoneNumber());
			model.addObject("email", u.getEmail());
			model.addObject("picture", u.getPicture());
		}
		else{
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
		
		//Agrego los datos privados del driver en caso de que exista la asociacion
		if(isAssociation || myProfile){
			model.addObject("vehicle", d.getVehicle().getModel());
			model.addObject("licensePlate", d.getVehicle().getLicensePlate());
		}
	}
	
	private void addModelPedestrian(User u){
		Pedestrian p = u.getPedestrian();
		
		model.addObject("idPedestrian", p.getPedestrianID());
		model.addObject("ratingPedestrian", p.getRating());
		
		ArrayList arraySch = new ArrayList();
		for(int i = 0; i < p.getSchedule().size(); i++){
			Schedule sch = p.getSchedule().get(i);
			Map<String, Object> day = new HashMap();
			
			day.put("dayPed", sch.getDay());
			day.put("hourInPed", sch.getHourIn());
			day.put("hasDriverIn", hasDriver(u, sch, 0));
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
					&& assoc.get(j).getState().getStateName().equals("Aceptado")) 
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
					&& assoc.get(j).getState().getStateName().equals("Aceptado")) 
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
	
}
