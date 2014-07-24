package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.service.SignupUserService;

//import com.shared.rides.service.SignupUserService;

@Controller
public class SignupUserController {

	@Autowired
	private SignupUserService signupUserService;
	
	@RequestMapping("/signup.do")
	public ModelAndView showContactView(){
		return new ModelAndView("signup");
	}
	
	@RequestMapping("/validateNewUser.do")
	public boolean validateNewUser(@RequestParam("personalId") String personalId,
								HttpServletRequest request){
		return signupUserService.validateNewUser(personalId);
	}

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public @ResponseBody String signupUser(@RequestParam("organization") String organization,
											@RequestParam("personalId") String personalId,
											@RequestParam("pw") String pw,
											@RequestParam("name") String name,
											@RequestParam("surname") String surname,
											@RequestParam("email") String email,
											@RequestParam("phone") long phoneNumber,
											@RequestParam("street") String street,
											@RequestParam("number") int numberStreet,
											@RequestParam("neighborhood") String neighborhood,
											@RequestParam("shift") String shift,
											@RequestParam("userType") String userType,
											@RequestParam("brand") String brand,
											@RequestParam("modelVehicle") String modelVehicle,
											@RequestParam("plateLetters") String plateLetters,
											@RequestParam("plateNumbers") String plateNumbers,
											@RequestParam("numberSeats") int numberSeats
											){	

		return "Mensaje";
//String licensePlate = plateLetters + " " + plateNumbers;
//signupUserService.signupUser(organization, personalId, pw, name, surname, phoneNumber, email, street, numberStreet, neighborhood, shift, userType, brand, model, licensePlate, numberSeats);
	}	
}
