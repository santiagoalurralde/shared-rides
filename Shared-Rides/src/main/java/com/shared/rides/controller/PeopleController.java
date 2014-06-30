package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.User;
import com.shared.rides.service.RequestAssociationService;
import com.shared.rides.service.ResponseAssociationService;
import com.shared.rides.service.PeopleService;

@Controller
public class PeopleController {
	
	@Autowired
	private ResponseAssociationService responseAssocService;	
	@Autowired
	private RequestAssociationService requestAssocService;
	@Autowired 
	private PeopleService peopleService;
	
	@RequestMapping(value = "/people.do")
	public ModelAndView showPeople(){
		return new ModelAndView ("people");
	}
	
	/*
	 * Metodo que se llama cuando se envia una nueva asociacion a un usuario
	 * IN --> 1
	 * OUT --> 2
	 */
	@RequestMapping(value = "/requestAssoc.do", method = RequestMethod.POST)
	public @ResponseBody String sendAssociationRequest(@RequestParam("day") int day,
								@RequestParam("inout") int inout,
								@RequestParam("idUser") long idUser,
								HttpServletRequest request){
		User requestUser = (User) request.getSession().getAttribute("user");
		String msg = null;
		if (idUser != requestUser.getUserId())
			msg = requestAssocService.sendAssocRequest(day, inout, idUser, requestUser.getUserId());
			
		return msg;
	}
	
	//Metodo que se llama cuando se accede a la seccion de "Personas" 
	@RequestMapping(value = "/loadAssociations", method = RequestMethod.POST)
	public @ResponseBody String loadAssoc(HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("user");
		return peopleService.getPeople(u.getUserId());
	}
	
	@RequestMapping(value = "/viewSchedule", method = RequestMethod.POST)
	public @ResponseBody String viewSchedule(HttpServletRequest request,
											@RequestParam("userId") long userId,
											@RequestParam("typeAssoc") int type){
	
		User u = (User) request.getSession().getAttribute("user");
		return responseAssocService.showAssociationSchedule(u, userId, type); 
	}
	
	//Metodo que se llama cuando el usuario responde a una solicitud pendiente
	@RequestMapping(value = "/responseAssoc.do", method = RequestMethod.POST)
	public @ResponseBody String sendAssociationResponse(@RequestParam("assocId") long assocId,
								@RequestParam("response") boolean resp,
								HttpServletRequest request){
		
		return responseAssocService.sendResponseAssoc(assocId, resp);
	}
	
}
