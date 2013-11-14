package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.shared.rides.domain.User;
import com.shared.rides.service.AssociationService;

public class PeopleController {
	
	@Autowired
	AssociationService assocService;
	
	@RequestMapping(value = "/hasAssoc.do", method = RequestMethod.POST)
	public @ResponseBody String hasAssociation(HttpServletResponse response){
		//User u = (User) request.getAttribute("user");
		//return assocService.hasAssociation(u);
	
		JsonObject json = new JsonObject();
		json.addProperty("hasAssoc", true);
		return json.toString();
		
	} 
	
	@RequestMapping(value = "/requestAssoc.do", method = RequestMethod.POST)
	public ModelAndView sendAssociationRequest(@RequestParam("day") int day,
								@RequestParam("inout") int inout,
								@RequestParam("idUser") long idUser,
								HttpServletRequest request){
		
		
		return null;
	}
	
}
