package com.shared.rides.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.domain.User;
import com.shared.rides.service.FindUserService;


@Controller
public class MainController {	

	@Autowired
	private FindUserService findUserService;
	
	private List<User> listUsers;
	
	@RequestMapping(value = "find.htm")
	public ModelAndView search(@RequestParam("prof") int profile, 
								@RequestParam("shift") int shift, 
								@RequestParam("long") double longitude,
								@RequestParam("lat") double latitude)
            throws ServletException, IOException {

		//Obtengo la lista de usuarios que se encuentran a 10 cuadras alrededor
		listUsers = findUserService.findUsers(profile, shift, longitude, latitude);
		
		ModelAndView modelAndView = new ModelAndView("userList");
		modelAndView.addObject(listUsers);
		modelAndView.setViewName("main");
		
		return modelAndView;
	}

}
