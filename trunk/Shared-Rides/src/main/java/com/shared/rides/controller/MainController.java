package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.shared.rides.service.FindUserService;


@Controller
public class MainController {	

	@Autowired
	private FindUserService findUserService;
	
	/*
	@RequestMapping(value = "/find.do")
	public @ResponseBody String search(@RequestParam("user") int profile, 
								@RequestParam("shift") int shift, 
								@RequestParam("lon") double longitude,
								@RequestParam("lat") double latitude)
            throws ServletException, IOException {

		//Obtengo la lista de usuarios que se encuentran a 10 cuadras alrededor
		String listUsers = findUserService.findUsers(profile, shift, longitude, latitude);
		return listUsers;
	}
	
	
	@RequestMapping(value = "/find.do")
	public @ResponseBody String search(@RequestBody String searchParameters){
		
		System.out.println(searchParameters.toString());
		//Obtengo la lista de usuarios que se encuentran a 10 cuadras alrededor
		//String listUsers = findUserService.findUsers(profile, shift, longitude, latitude);
		//return listUsers;		
		return null;
	}
	*/
	@RequestMapping(value = "/prueba.do")
	public @ResponseBody String prueba(){
		
		JsonObject json = new JsonObject();
		json.addProperty("id", 1);
		json.addProperty("name", "SEEER");
		
		return json.toString();
	}
	
}
