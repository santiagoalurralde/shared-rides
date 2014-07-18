package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/signupUser.do")
	public void signupUser(@RequestParam("organization") String organization,
							@RequestParam("personalId") String personalId,
							@RequestParam("organization") String pw,
							@RequestParam("organization") String name,
							@RequestParam("organization") String surname,
							@RequestParam("organization") long phoneNumber,
							@RequestParam("organization") String email,
							@RequestParam("organization") String street,
							@RequestParam("organization") int numberStreet,
							@RequestParam("organization") String neighborhood,
							@RequestParam("organization") String shift,
							@RequestParam("typeUser") String typeUser,
							@RequestParam("brand") String brand,
							@RequestParam("model") String model,
							@RequestParam("plateNumbers") String plateNumbers,
							@RequestParam("plateLetters") String plateLetters,
							@RequestParam("numberSeats") int numberSeats){
		
		String licensePlate = plateLetters + " " + plateNumbers;
		signupUserService.signupUser(organization, personalId, pw, name, surname, phoneNumber, email, street, numberStreet, neighborhood, shift, typeUser, brand, model, licensePlate, numberSeats);
	}
	
}
