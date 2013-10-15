package com.shared.rides.service;

import java.util.ArrayList;
import java.util.List;

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
	private int freeSeats;
	private boolean myProfile;
	private ModelAndView model;
	
	public ModelAndView getProfile(int userId, HttpServletRequest req, boolean myProf){
		HttpSession s = req.getSession(false);
		User u = (User)s.getAttribute("user");
		myProfile = myProf;
		
		//Si no es mi profile, entonces veo si tengo asociacion con esa persona
		if(myProfile = false){
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
			addModelDriver(u);
		}
		
		//Agrego los datos del pedestrian si el usuario es uno.
		if(u.getPedestrian() != null){
			addModelPedestrian(u);
		}
		//Agrego los datos privados del usuario en caso de que existe la asociacion
		if(isAssociation || myProfile){
			model.addObject("telephone", u.getPhoneNumber());
			model.addObject("email", u.getEmail());
			model.addObject("picture", u.getPicture());
		}
	}
	
	private void addModelDriver(User u){
		Driver d = u.getDriver();
		model.addObject("idDriver", d.getDriverId());
		model.addObject("ratingDriver", d.getRating());

		//Agrego el horario al objecto jsonDriver
		ArrayList arraySch = new ArrayList();
		for(int i = 0; i < d.getSchedule().size(); i++){
			ArrayList<Object> arrayDay = new ArrayList<Object>();
			int freeSeatsIn = calculateFreeSeats(u, 0);
			int freeSeatsOut = calculateFreeSeats(u, 1);
			Schedule sch = d.getSchedule().get(i);
			
			arrayDay.add(0, sch.getScheduleId());
			arrayDay.add(1, sch.getDay());
			arrayDay.add(2, sch.getHourIn());
			arrayDay.add(3, freeSeatsIn);
			//ACA DEBERIA IR EL TRACK DE IDA
			
			arrayDay.add(4, sch.getHourOut());
			arrayDay.add(5, freeSeatsOut);
			//ACA VA EL TRACK DE VUELTA DE ESE DIA
			arrayDay.add(6, sch.getDay());
			
			
			arraySch.add(i, arrayDay);
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
			ArrayList<Object> arrayDay = new ArrayList<Object>();
			
			arrayDay.add(0, sch.getDay());
			arrayDay.add(1, sch.getHourIn());
			arrayDay.add(2, haveDriver(u, sch, 0));
			arrayDay.add(3, sch.getHourOut());
			arrayDay.add(4, haveDriver(u, sch, 1));
			
			arraySch.add(i, arrayDay);
		}
		
		model.addObject("schPed", arraySch);
		
		float latitude = u.getAddress().getMarker().getLatitude();
		float longitude = u.getAddress().getMarker().getLongitude();
		model.addObject("lat", latitude);
		model.addObject("long", longitude);	
	}
	
	//Funcion que va a calcular los asientos libres	
	private int calculateFreeSeats(User u, int inout){
		freeSeats = u.getDriver().getVehicle().getSeats();
		List<Schedule> sch = u.getDriver().getSchedule();
		List<Association> assoc = u.getAssociations();
		
		for (int i = 0; i < sch.size(); i++){
			for (int j = 0; j <assoc.size(); j++){
				if (sch.get(i).getDay() == assoc.get(j).getDay() 
						&& assoc.get(j).getInout() == inout 
						&& assoc.get(j).getState().getStateName().equals("Aceptado")) 
					freeSeats--;
			}
		}
		return freeSeats;
	}
	
	//Funcion para ver si ese peaton ya tiene conductor en ese dia
	private boolean haveDriver(User u, Schedule sch, int inout){
		boolean haveDriver = false;
		List<Association> assoc = u.getAssociations();
		
			for (int j = 0; j <assoc.size(); j++){
				if (sch.getDay() == assoc.get(j).getDay() 
						&& assoc.get(j).getInout() == inout 
						&& assoc.get(j).getState().getStateName().equals("Aceptado")) 
					haveDriver = true;
			}
		return haveDriver;
	}
}
