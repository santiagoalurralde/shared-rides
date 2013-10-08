package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.service.LoginService;


@Controller
public class LoginController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "login.do")
	public ModelAndView loadLogin(HttpSession request)
            throws ServletException, IOException {

		return new ModelAndView("login");
	}
	
	
	@RequestMapping(value = "validate.do", method = RequestMethod.POST)
	public ModelAndView userValidation(@RequestParam("email") String email , @RequestParam("pwd") String pwd, HttpServletRequest request)
            throws ServletException, IOException {
        logger.info("Returning hello view");
        	
        if(loginService.validate(email, pwd, request)){
        	
        	return new ModelAndView("redirect:/main.do"); 
        }
     return new ModelAndView("login");
        
    }
	
	@RequestMapping(value="main.do")
	public ModelAndView loadMain(){
		
		return new ModelAndView("main");
	}

}
