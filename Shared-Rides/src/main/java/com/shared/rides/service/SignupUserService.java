package com.shared.rides.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

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
import com.shared.rides.domain.Marker;
import com.shared.rides.domain.Organization;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Schedule;
import com.shared.rides.domain.Shift;
import com.shared.rides.domain.Stop;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;
import com.shared.rides.domain.Vehicle;
import com.shared.rides.util.CreateGPXFile;

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
							String shift, String typeUser, String brand, String model, String licensePlate, int numberSeats, String days){
		User u = new User();
		
		Organization org = new Organization();
		org.setOrganizationId(organization);
		
		org = orgDAO.load(org);
		
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
		
		if (shift.equalsIgnoreCase(Shift.MOORNING.toString())){
			u.setShift(Shift.MOORNING);
		}else if (shift.equalsIgnoreCase(Shift.AFTERNOON.toString())){
			u.setShift(Shift.AFTERNOON);
			}else{
				u.setShift(Shift.EVENING);	
			}

		userDAO.save(u);
		
		if(typeUser.equalsIgnoreCase("pedestrian")){
			Pedestrian ped = new Pedestrian();
			pedDAO.save(ped);
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
			}
		
		parseMarkers(days, typeUser);
		
	}
	
	private void parseMarkers(String m, String type){
		
		User u = userDAO.getLastUser();
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(m);		
		JsonArray arrayMarkers = (JsonArray) obj;
		
		String fileName;
		Double[][] markers = null;
		JsonElement lat;
		JsonElement lon;
		
		for (int i = 0; i < arrayMarkers.size(); i++){
			JsonObject jsonDay = (JsonObject) arrayMarkers.get(i);
			
			Schedule sch = new Schedule();
		
			sch.setDay(i+1);
			sch.setHourIn(jsonDay.get("hourIn").toString());
			sch.setHourOut(jsonDay.get("hourOut").toString());
			
			schDAO.save(sch);
			
			if(type.equalsIgnoreCase("pedestrian")){			
				Pedestrian ped = pedDAO.getLastPedestrian();
				sch = schDAO.getLastSchedule();
				pedDAO.newSch(ped, sch);
				markers = new Double[1][2];
				
				//STOP IN
				JsonObject stopIn = jsonDay.getAsJsonObject("stopIn");
				lat = stopIn.get("lat");
				lon = stopIn.get("lon");
				markers[0][0] = lat.getAsDouble();
				markers[0][1] = lon.getAsDouble();
				
				Stop stop = new Stop();
				//El nombre del archivo esta formado por el identificador personal del usuario, mas el dia de semana que es y si es in o out 
				fileName = u.getPersonalId() + i + "in";
				
				CreateGPXFile.createGPX(fileName, markers);
				stop.setPathFile(fileName);
				
				stopDAO.save(stop);
				stop = stopDAO.getLastStop();
				pedDAO.newStop(ped, stop);
				
				//STOP OUT
				JsonObject stopOut = jsonDay.getAsJsonObject("stopOut");
				lat = stopOut.get("lat");
				lon = stopOut.get("lon");
				markers[0][0] = lat.getAsDouble();
				markers[0][1] = lon.getAsDouble();

				fileName = u.getPersonalId() + i + "out";
				
				CreateGPXFile.createGPX(fileName, markers);
				stop.setPathFile(fileName);
				stopDAO.save(stop);
				
				stop = stopDAO.getLastStop();
				pedDAO.newStop(ped, stop);
			}
			else if (type.equalsIgnoreCase("driver")){
				Driver driver = driverDAO.getLastDriver();
				driverDAO.newSch(driver, sch);
				
				//TRACK IN
				JsonArray trackIn = jsonDay.getAsJsonArray("trackIn");
				markers = new Double[trackIn.size()][2];
				
				for(int j = 0; j < trackIn.size(); j++){
					JsonObject trk = (JsonObject) trackIn.get(j);
					lat = trk.get("lat");
					lon = trk.get("lon");
					markers[j][0] = lat.getAsDouble();
					markers[j][1] = lon.getAsDouble();
				}
				
				Track track = new Track();
				//El nombre del archivo esta formado por el identificador personal del usuario, mas el dia de semana que es y si es in o out 
				fileName = u.getPersonalId() + i + "in";
				
				CreateGPXFile.createGPX(fileName, markers);
				track.setPathFile(fileName);
				
				trackDAO.save(track);
				track = trackDAO.getLastTrack();
				driverDAO.newTrack(driver, track);
				
				//TRACK OUT
				JsonArray trackOut = jsonDay.getAsJsonArray("trackOut");
				markers = new Double[trackOut.size()][2];
				
				for(int j = 0; j < trackOut.size(); j++){
					JsonObject trk = (JsonObject) trackOut.get(j);
					lat = trk.get("lat");
					lon = trk.get("lon");
					markers[j][0] = lat.getAsDouble();
					markers[j][1] = lon.getAsDouble();
				}
				
				track = new Track();
				//El nombre del archivo esta formado por el identificador personal del usuario, mas el dia de semana que es y si es in o out 
				fileName = u.getPersonalId() + i + "out";
				
				CreateGPXFile.createGPX(fileName, markers);
				track.setPathFile(fileName);
				
				trackDAO.save(track);
				track = trackDAO.getLastTrack();
				driverDAO.newTrack(driver, track);
			}
			else{
				//TODO: aca falta hacer cuando es los dos tipos de usuarios
			}
		}
	}	
}
