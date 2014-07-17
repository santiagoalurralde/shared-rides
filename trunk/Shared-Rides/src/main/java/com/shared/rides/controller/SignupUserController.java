package com.shared.rides.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignupUserController {

	@RequestMapping("/signup.do")
	public ModelAndView showContactView(){
		return new ModelAndView("signup");
	}
	
	@RequestMapping("/registerNewUser.do")
	public void registerNewUser(){
		
	}
}
