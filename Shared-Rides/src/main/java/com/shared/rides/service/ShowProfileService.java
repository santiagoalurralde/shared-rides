package com.shared.rides.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
	private IDriverDAO driverDAO;
	@Autowired
	private IPedestrianDAO pedDAO;
	private boolean isAssociation = false;
	private int freeSeats;
	private boolean myProfile;
	
	public String getProfile(int userId, HttpServletRequest req, boolean myProf){
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
		return createJsonUser(userAssoc).toString();
		}
		
		return createJsonUser(u).toString();	
	}
	
	private JsonObject createJsonUser(User u){
		//json object para el usuario general
		JsonObject jsonUser = new JsonObject();

		jsonUser.addProperty("id", u.getUserId());
		jsonUser.addProperty("name", u.getName());
		jsonUser.addProperty("surname", u.getSurname());
		jsonUser.addProperty("shift", u.getShift().getShiftName());
		
		String street = u.getAddress().getStreet();
		int numberStreet = u.getAddress().getNumber();
		jsonUser.addProperty("address", street + numberStreet);
		jsonUser.addProperty("neighborhood", u.getAddress().getNeighborhood());
		
		//Agrego los datos del driver si el usuario es uno.
		if(!u.getDriver().equals(null)){
			jsonUser.add("driver", createJsonDriver(u));
		}
		//Agrego los datos del pedestrian si el usuario es uno.
		if(!u.getPedestrian().equals(null)){
			jsonUser.add("pedestrian", createJsonPedestrian(u));
		}
		
		//Agrego los datos privados del usuario en caso de que existe la asociacion
		if(isAssociation || myProfile){
			jsonUser.addProperty("telephone", u.getPhoneNumber());
			jsonUser.addProperty("email", u.getEmail());
			jsonUser.addProperty("picture", u.getPicture());
		}
		return jsonUser;
	}
	
	private JsonObject createJsonDriver(User u){
		Driver d = u.getDriver();
		JsonObject jsonDriver = new JsonObject();
		jsonDriver.addProperty("idDriver", d.getDriverId());
		jsonDriver.addProperty("ratingDriver", d.getRating());

		//Agrego el horario al objecto jsonDriver
		JsonArray jsonArraySch = new JsonArray();
		for(int i = 0; i < d.getSchedule().size(); i++){
			JsonObject jsonSched = new JsonObject();
			int freeSeatsIn = calculateFreeSeats(u, 0);
			int freeSeatsOut = calculateFreeSeats(u, 1);
			Schedule sch = d.getSchedule().get(i);
			
			jsonSched.addProperty("idSched", sch.getScheduleId());
			jsonSched.addProperty("day", sch.getDay());
			jsonSched.addProperty("hourIn", sch.getHourIn());
			jsonSched.addProperty("freeIn", freeSeatsIn);
			jsonSched.addProperty("hourOut", sch.getHourOut());
			jsonSched.addProperty("freeOut", freeSeatsOut);
			
			//ACA AGREGARIA EL TRACK TAMBIEN, EN LA BASE DE DATOS TENGO QUE AGREGAR
			//A LA TABLA DRIVER_SCHEDULE LOS FREESEATS Y EL TRACK DE ESE HORARIO
			//QUEDARIA LA TABLA CON "driverID, scheduleID
			//pathFileTrackIn, pathFileTrackOut"
			
			jsonArraySch.add(jsonSched);
		}
		//Agrego el horario
		jsonDriver.add("schedule", jsonArraySch);
		
		//Agrego los datos privados del driver en caso de que exista la asociacion
		if(isAssociation || myProfile){
			jsonDriver.addProperty("vehicle", d.getVehicle().getModel());
			jsonDriver.addProperty("licensePlate", d.getVehicle().getLicensePlate());
		}
		return jsonDriver;
	}
	
	private JsonObject createJsonPedestrian(User u){
		Pedestrian p = u.getPedestrian();
		JsonObject jsonPedestrian = new JsonObject();
	
		jsonPedestrian.addProperty("idPedestrian", p.getPedestrianID());
		jsonPedestrian.addProperty("ratingPedestrian", p.getRating());
		
		JsonArray jsonArraySch = new JsonArray();
		for(int i = 0; i < p.getSchedule().size(); i++){
			JsonObject jsonSch = new JsonObject();
			Schedule sch = p.getSchedule().get(i);
			
			jsonSch.addProperty("idSchedule", sch.getScheduleId());
			jsonSch.addProperty("day", sch.getDay());
			jsonSch.addProperty("hourIn", sch.getHourIn());
			jsonSch.addProperty("haveDriverIn", haveDriver(u, sch, 0));
			jsonSch.addProperty("hourOut", sch.getHourOut());
			jsonSch.addProperty("haveDriverOut", haveDriver(u, sch, 1));
			
			jsonArraySch.add(jsonSch);
		}
		
		jsonPedestrian.add("schedule", jsonArraySch);
		
		float latitude = u.getAddress().getMarker().getLatitude();
		float longitude = u.getAddress().getMarker().getLongitude();
		jsonPedestrian.addProperty("lat", latitude);
		jsonPedestrian.addProperty("long", longitude);
		
		return jsonPedestrian;
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
