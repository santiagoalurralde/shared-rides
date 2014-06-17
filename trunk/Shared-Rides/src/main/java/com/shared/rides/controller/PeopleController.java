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
import com.shared.rides.service.PeopleService;

@Controller
public class PeopleController {
	
	@Autowired
	private AssociationService assocService;
	@Autowired 
	private PeopleService peopleService;
	
	@RequestMapping(value = "/people.do")
	public ModelAndView showPeople(){
		return new ModelAndView ("people");
	}
	
	@RequestMapping(value = "/hasAssociation.do", method = RequestMethod.POST)
	public @ResponseBody String hasAssoc(HttpServletRequest request){
	   	User u = (User) request.getSession().getAttribute("user");
    	return assocService.hasAssociation(u);
	}
	
	@RequestMapping(value = "/requestAssoc.do", method = RequestMethod.POST)
	public @ResponseBody String sendAssociationRequest(@RequestParam("day") int day,
								@RequestParam("inout") int inout,
								@RequestParam("idUser") long idUser,
								HttpServletRequest request){
		User requestUser = (User) request.getSession().getAttribute("user");
		String msg = null;
		if (idUser != requestUser.getUserId())
			msg = assocService.sendAssocRequest(day, inout, idUser, requestUser.getUserId());
			
		return msg;
	}
	
	@RequestMapping(value = "/loadAssociations", method = RequestMethod.POST)
	public @ResponseBody String loadAssoc(HttpServletRequest r){
		User u = (User) r.getAttribute("user");
		return peopleService.getPeople(u.getUserId());
	}
	
}
