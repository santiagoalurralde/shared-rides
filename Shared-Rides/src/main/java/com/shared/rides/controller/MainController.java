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


@Controller
public class MainController {	

	@Autowired
	private FindUserService findUserService;
	@Autowired
	private RequestAssociationService requestAssocService;
	
	
	@RequestMapping(value="main.do")
	public ModelAndView loadMain(HttpServletRequest request){
		return new ModelAndView("main");
	}
	
	//Metodo que se llama para ver si el usuario tiene nuevas asociaciones
	@RequestMapping(value = "/hasAssociation.do", method = RequestMethod.POST)
	public @ResponseBody String hasAssoc(HttpServletRequest request){
	   	User u = (User) request.getSession().getAttribute("user");
	   	return requestAssocService.hasAssociation(u.getUserId());
	}
	
	//Metodo que se encarga de realizar la busqueda de los usuarios de acuerdo a los parametros recibidos
	@RequestMapping(value = "/find.do", method = RequestMethod.POST)
	public @ResponseBody String search(@RequestParam("user") String user,
								@RequestParam("shift") int shift,
								@RequestParam("mapData") String map){
		
		int prof = Integer.parseInt(user);
		return findUserService.findUsers(prof, shift, map);
	} 
	
}
