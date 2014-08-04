package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
import com.shared.rides.domain.User;
import com.shared.rides.service.FindUserService;
import com.shared.rides.service.RequestAssociationService;
import com.shared.rides.service.ResponseAssociationService;


@Controller
public class MainController {	

	@Autowired
	private FindUserService findUserService;
	@Autowired
	private RequestAssociationService requestAssocService;
	@Autowired
	private ResponseAssociationService responseAssocService;
	
	
	@RequestMapping(value="main.do")
	public ModelAndView loadMain(HttpServletRequest request){
		return new ModelAndView("main");
	}
	
	@RequestMapping(value="contact.do")
	public ModelAndView showContact(HttpServletRequest request){
		return new ModelAndView("contact");
	}
	
	@RequestMapping(value="notifications.do")
	public ModelAndView showNotifications(HttpServletRequest request){
		return new ModelAndView("notifications");
	}
	
	@RequestMapping(value="about.do")
	public ModelAndView showAbout(HttpServletRequest request){
		return new ModelAndView("about");
	}
	
	//Metodo que se llama para ver si el usuario tiene nuevas asociaciones
	@RequestMapping(value = "/hasAssociation.do", method = RequestMethod.POST)
	public @ResponseBody String hasAssoc(HttpServletRequest request){
	   	User u = (User) request.getSession().getAttribute("user");
	   	return requestAssocService.hasAssociation(u.getUserId());
	}
	
	@RequestMapping(value = "/hasResponse.do", method = RequestMethod.POST)
	public @ResponseBody String hasResponse(HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("user");
		return responseAssocService.hasResponse(u.getUserId());
	}
	
	//Metodo que se encarga de realizar la busqueda de los usuarios de acuerdo a los parametros recibidos
	@RequestMapping(value = "/find.do", method = RequestMethod.POST)
	public @ResponseBody String search(@RequestParam("user") int prof,
								@RequestParam("shift") int shift,
								@RequestParam("mapData") String map,
								HttpServletRequest request){
		
		User u = (User) request.getSession().getAttribute("user");
		return findUserService.findUsers(u.getUserId(), prof, shift, map);
	} 
	
}
