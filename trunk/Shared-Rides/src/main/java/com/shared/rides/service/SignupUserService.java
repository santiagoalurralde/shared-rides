package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IAddressDAO;
import com.shared.rides.dao.interfaces.IOrganizationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Address;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Organization;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.Shift;
import com.shared.rides.domain.User;
import com.shared.rides.domain.Vehicle;

@Service
public class SignupUserService {
	
	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private IOrganizationDAO orgDAO;
	
	@Autowired
	private IAddressDAO addressDAO;
	
	public boolean validateNewUser(String personalId){
		boolean isValidate = true;
		List<User> userList = userDAO.listAll();
		
		for(User u : userList){
			if (u.getPersonalId().equals(personalId)) isValidate = false;
		}
		return isValidate;
	}
	
	public void signupUser(String organization, String personalId, String pw, String name, String surname,
							long phoneNumber, String email, String street, int numberStreet, String neighborhood,
							String shift, String typeUser, String brand, String model, String licensePlate, int numberSeats){
		User u = new User();
		
		Organization org = new Organization();
		org.setName(organization);
		
		org = orgDAO.loadByName(org);
		
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
		
		if (shift.equalsIgnoreCase(Shift.MOORNING.toString())) u.setShift(Shift.MOORNING);
		if (shift.equalsIgnoreCase(Shift.AFTERNOON.toString())) u.setShift(Shift.AFTERNOON);
		if (shift.equalsIgnoreCase(Shift.EVENING.toString())) u.setShift(Shift.EVENING);
		
		if(typeUser.equalsIgnoreCase("pedestrian")){
			Pedestrian ped = new Pedestrian();
			
			//TODO: falta setear los stops y los schedule
		}
		
		if(typeUser.equalsIgnoreCase("driver")){
			Vehicle vehicle = new Vehicle();
			
			vehicle.setBrand(brand);
			vehicle.setLicensePlate(licensePlate);
			vehicle.setModel(model);
			vehicle.setSeats(numberSeats);
			
			Driver driver = new Driver();
			driver.setVehicle(vehicle);
			
			//TODO: falta setear los tracks y los schedule
			
		}
	
	}

}
