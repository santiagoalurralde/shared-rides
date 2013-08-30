package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.service.LoginService;


@Controller
public class LoginController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private LoginService loginService;
	
	
	@RequestMapping(value = "login.do")
	public ModelAndView userValidation()
            throws ServletException, IOException {
        logger.info("Returning hello view");
        
        String email = "leandro.bagur@hotmail.com";
        String pw = "leandro";
        
        if(loginService.validate(email, pw)){
        	System.out.println("ENTRO - USUARIO VALIDO");
        	return new ModelAndView("login");
        }
        System.out.println("USUARIO NO VALIDO");
     return new ModelAndView("login");
        
    }

}
