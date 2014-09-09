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
import com.shared.rides.service.FindUserService;
import com.shared.rides.service.NotificationService;
import com.shared.rides.service.ProfileService;


@Controller
public class MainController {	

	@Autowired
	private FindUserService findUserService;

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private NotificationService notificationService;
	
	
	@RequestMapping(value="main.do")
	public ModelAndView loadMain(HttpServletRequest request){
		return new ModelAndView("main");
	}
	
	@RequestMapping(value="contact.do")
	public ModelAndView showContact(HttpServletRequest request){
		return new ModelAndView("contact");
	}

	@RequestMapping(value="about.do")
	public ModelAndView showAbout(HttpServletRequest request){
		return new ModelAndView("about");
	}
	
	@RequestMapping(value="contactNew.do")
	public ModelAndView showContactNew(HttpServletRequest request){
		return new ModelAndView("contactNew");
	}

	@RequestMapping(value="aboutNew.do")
	public ModelAndView showAboutNew(HttpServletRequest request){
		return new ModelAndView("aboutNew");
	}	
	
	@RequestMapping(value="mapdriver.do")
	public ModelAndView showMapDriver(HttpServletRequest request){
		return new ModelAndView("mapdriver");
	}
	
	@RequestMapping(value="mappedestrian.do")
	public ModelAndView showMapPedestrian(HttpServletRequest request){
		return new ModelAndView("mappedestrian");
	}
	
	@RequestMapping(value = "/getNotifications.do", method = RequestMethod.POST)
	public @ResponseBody String getNotifications(HttpServletRequest request){
	   	User u = (User) request.getSession().getAttribute("user");
	   	return notificationService.getNotifications(u.getUserId());
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
	
	@RequestMapping(value = "/defaultFind.do", method = RequestMethod.POST)
	public @ResponseBody String search(@RequestParam("user") int prof,
									@RequestParam("shift") int shift,
									HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("user");
		return findUserService.defaultFindUser(u.getUserId(), prof, shift);
	}
	
	@RequestMapping(value = "/validateDefaultFind.do", method = RequestMethod.POST)
	public @ResponseBody String validateFind(@RequestParam("user") int typeUser,
								HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("user");
		return findUserService.validateDefaultFind(typeUser, u);
	}
	
}
