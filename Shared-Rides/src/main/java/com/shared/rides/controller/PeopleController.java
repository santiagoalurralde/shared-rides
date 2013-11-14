package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.domain.User;
import com.shared.rides.service.AssociationService;

@Controller
public class PeopleController {
	
	@Autowired
	AssociationService assocService;
	
	@RequestMapping(value = "/hasAssociation.do", method = RequestMethod.POST)
	public @ResponseBody String hasAssoc(HttpServletRequest request){
	   	User u = (User) request.getSession().getAttribute("user");
    	return assocService.hasAssociation(u);
	}
	
	
	@RequestMapping(value = "/requestAssoc.do", method = RequestMethod.POST)
	public ModelAndView sendAssociationRequest(@RequestParam("day") int day,
								@RequestParam("inout") int inout,
								@RequestParam("idUser") long idUser,
								HttpServletRequest request){
		System.out.println(day);
		System.out.println(idUser);
		//User u = (User) request.getSession().getAttribute("user");
		//assocService.sendAssocRequest(day, inout, idUser, u.getUserId());
		
		return null;
	}
	
}
