package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.service.SignupUserService;

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
}
