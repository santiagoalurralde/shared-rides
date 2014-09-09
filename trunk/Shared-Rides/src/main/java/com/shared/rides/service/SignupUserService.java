package com.shared.rides.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shared.rides.dao.interfaces.IAddressDAO;
import com.shared.rides.dao.interfaces.IDriverDAO;
import com.shared.rides.dao.interfaces.IOrganizationDAO;
import com.shared.rides.dao.interfaces.IPedestrianDAO;
import com.shared.rides.dao.interfaces.IScheduleDAO;
import com.shared.rides.dao.interfaces.IStopDAO;
import com.shared.rides.dao.interfaces.ITrackDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.dao.interfaces.IVehicleDAO;
import com.shared.rides.domain.Address;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Organization;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.Shift;
import com.shared.rides.domain.Stop;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;
import com.shared.rides.domain.Vehicle;
import com.shared.rides.util.CreateGPXFile;
import com.shared.rides.util.UploadFile;

@Service
public class SignupUserService {
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IOrganizationDAO orgDAO;
	
	@Autowired
	private IAddressDAO addressDAO;
	
	@Autowired
	private IPedestrianDAO pedDAO;
	
	@Autowired
	private IDriverDAO driverDAO;
	
	@Autowired
	private IScheduleDAO schDAO;
	
	@Autowired
	private IVehicleDAO vehicleDAO;
	
	@Autowired
	private IStopDAO stopDAO;
	
	@Autowired
	private ITrackDAO trackDAO;
	
	public boolean validateNewUser(String personalId){
		boolean isValidate = true;
		List<User> userList = userDAO.listAll();
		
		for(User u : userList){
			if (u.getPersonalId().equals(personalId)) isValidate = false;
		}
		return isValidate;
	}
	
	public void signupUser(long organization, String personalId, String pw, String name, String surname,
							long phoneNumber, String email, String street, int numberStreet, String neighborhood,
							String shift, String typeUser, String brand, String model, String licensePlate, int numberSeats, String days, String pic){
		User u = new User();
		Organization org = orgDAO.load(organization);
		
		Address address = new Address();
		address.setStreet(street);
		address.setNumber(numberStreet);
		address.setNeighborhood(neighborhood);
		address.setCity(org.getAddress().getCity());
				
		u.setPersonalId(personalId);
		u.setPw(pw);
		u.setName(name);
		u.setSurname(surname);
		u.setPhoneNumber(phoneNumber);
		u.setAddress(address);
		u.setOrganization(org);
		u.setEmail(email);
		
		if (shift.equalsIgnoreCase(Shift.MORNING.toString())){
			u.setShift(Shift.MORNING);
		}else if (shift.equalsIgnoreCase(Shift.AFTERNOON.toString())){
			u.setShift(Shift.AFTERNOON);
			}else{
				u.setShift(Shift.EVENING);	
			}
		
		u.setPicture(pic);
		u.setLastLoginDate(new Date());
		
		userDAO.save(u);
		
		if(typeUser.equalsIgnoreCase("pedestrian")){
			Pedestrian ped = new Pedestrian();
			pedDAO.save(ped);
			
			ped = pedDAO.getLastPedestrian();
			userDAO.newPed(u, ped);
		}else if(typeUser.equalsIgnoreCase("driver")){
			
				Vehicle vehicle = new Vehicle();
				
				vehicle.setBrand(brand);
				vehicle.setLicensePlate(licensePlate);
				vehicle.setModel(model);
				vehicle.setSeats(numberSeats);
			
				Driver driver = new Driver();
				driver.setVehicle(vehicle);

				driverDAO.save(driver);
				vehicleDAO.save(vehicle);
				
				driver = driverDAO.getLastDriver();
				userDAO.newDriver(u, driver);
			}else{
				
				Pedestrian ped = new Pedestrian();
				Vehicle vehicle = new Vehicle();
				
				vehicle.setBrand(brand);
				vehicle.setLicensePlate(licensePlate);
				vehicle.setModel(model);
				vehicle.setSeats(numberSeats);
			
				Driver driver = new Driver();
				driver.setVehicle(vehicle);

				pedDAO.save(ped);
				driverDAO.save(driver);
				vehicleDAO.save(vehicle);
				
				ped = pedDAO.getLastPedestrian();
				userDAO.newPed(u, ped);			
				driver = driverDAO.getLastDriver();
				userDAO.newDriver(u, driver);
			}
		
		parseMarkers(days, typeUser);
		
	}
	
	private void parseMarkers(String m, String type){
		User u = userDAO.getLastUser();
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(m);		
		JsonArray arrayMarkers = (JsonArray) obj;
		
		for (int i = 0; i < arrayMarkers.size(); i++){
			if(!(arrayMarkers.get(i).toString().equalsIgnoreCase('"'+"Unsubscribed"+'"'))){
				JsonObject jsonDay = (JsonObject) arrayMarkers.get(i);
			
				Schedule sch = new Schedule();
		
				sch.setDay(i+1);
				String hourIn = jsonDay.get("hourIn").toString();
				hourIn = hourIn.replace('"', ' ');
				sch.setHourIn(hourIn);
				String hourOut = jsonDay.get("hourOut").toString();
				hourOut = hourOut.replace('"', ' ');
				sch.setHourOut(hourOut);
			
				schDAO.save(sch);
				sch = schDAO.getLastSchedule();
				
				if(type.equalsIgnoreCase("pedestrian")){			
					Pedestrian ped = pedDAO.getLastPedestrian();	
					pedDAO.newSch(ped.getPedestrianId(), sch.getScheduleId());
				
					saveStop(jsonDay, ped, i, u.getPersonalId(), "stopIn", "in");
					saveStop(jsonDay, ped, i, u.getPersonalId(), "stopOut", "out");
				}
				else if (type.equalsIgnoreCase("driver")){
					Driver driver = driverDAO.getLastDriver();
					driverDAO.newSch(driver.getDriverId(), sch.getScheduleId());
				
					saveTrack(jsonDay, driver, i, u.getPersonalId(), "trackIn", "in");
					saveTrack(jsonDay, driver, i, u.getPersonalId(), "trackOut", "out");
					}
					else{	//Sino quiere decir que es conductor y peaton y tengo que averiguar cual es en ese dia
						if (jsonDay.has("stopIn")){
							Pedestrian ped = pedDAO.getLastPedestrian();
							pedDAO.newSch(ped.getPedestrianId(), sch.getScheduleId());
						
							saveStop(jsonDay, ped, i, u.getPersonalId(), "stopIn", "in");
							saveStop(jsonDay, ped, i, u.getPersonalId(), "stopOut", "out");

						}
						else{
							Driver driver = driverDAO.getLastDriver();
							driverDAO.newSch(driver.getDriverId(), sch.getScheduleId());
							
							saveTrack(jsonDay, driver, i, u.getPersonalId(), "trackIn", "in");
							saveTrack(jsonDay, driver, i, u.getPersonalId(), "trackOut", "out");
						}
					}
			}
		}
	}
	
	private void saveStop(JsonObject jsonDay, Pedestrian ped, int day, String personalId, String tagName, String inout){
		JsonObject stopIn = jsonDay.getAsJsonObject(tagName);
		JsonElement lat = stopIn.get("lat");
		JsonElement lon = stopIn.get("lon");
		
		Stop stop = new Stop();
		
		stop.setDay(day + 1);
		
		if (inout.equalsIgnoreCase("in")) stop.setInout(0);
		else stop.setInout(1);
		
		stop.setLat(lat.getAsDouble());
		stop.setLon(lon.getAsDouble());
		
		stopDAO.save(stop);
		stop = stopDAO.getLastStop();
		pedDAO.newStop(ped.getPedestrianId(), stop.getStopId());
	}
	
	private void saveTrack(JsonObject jsonDay, Driver driver, int day, String personalId, String tagName, String inout){
		JsonArray trackArray = jsonDay.getAsJsonArray(tagName);
		Double[][] markers = new Double[trackArray.size()][2];
		JsonElement lat;
		JsonElement lon;
		
		for(int j = 0; j < trackArray.size(); j++){
			JsonObject trk = (JsonObject) trackArray.get(j);
			lat = trk.get("lat");
			lon = trk.get("lon");
			markers[j][0] = lat.getAsDouble();
			markers[j][1] = lon.getAsDouble();
		}
		
		Track track = new Track();
		//El nombre del archivo esta formado por el identificador personal del usuario, mas el dia de semana que es y si es in o out 
		String fileName = personalId + "_" + day + "_" + inout;
		
		CreateGPXFile.createGPX(fileName, markers);
		track.setPathFile(fileName);
		track.setDay(day + 1);
		if(inout.equalsIgnoreCase("in")) track.setInout(0);
		else track.setInout(1);
		
		trackDAO.save(track);
		track = trackDAO.getLastTrack();
		driverDAO.newTrack(driver.getDriverId(), track.getTrackId());
	}
	
	public String uploadPicFile(MultipartFile file, HttpServletRequest request){
		Random ran = new Random();
		String fileName = ran.nextInt(999) + "_" + file.getOriginalFilename();
		//Seteo el nuevo nombre de la imagen en la sesion para luego obtenerlo de nuevo a la hora de darle de alta al usuario
		HttpSession s = request.getSession();
		s.setAttribute("picName", fileName);	
		return UploadFile.uploadFile(file, fileName);			
	}
	
}
