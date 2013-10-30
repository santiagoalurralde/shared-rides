package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shared.rides.service.FindUserService;


@Controller
public class MainController {	

	@Autowired
	private FindUserService findUserService;
	
	@RequestMapping(value = "/prueba.do")
	public @ResponseBody String prueba(){
		
		JsonArray jsonA = new JsonArray();
		
		JsonObject json1 = new JsonObject();
		json1.addProperty("id", 1);
		json1.addProperty("name", "Steve");
		json1.addProperty("surname", "Jobs");
		json1.addProperty("pic", "http://www.igdigital.com/wp-content/uploads/2013/03/steve_jobs_apple1-1.jpeg");
		
		JsonObject json2 = new JsonObject();
		json2.addProperty("id", 2);
		json2.addProperty("name", "Pablo");
		json2.addProperty("surname", "Picasso");
		json2.addProperty("pic", "http://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Pablo_picasso_1.jpg/192px-Pablo_picasso_1.jpg");
		
		JsonObject json3 = new JsonObject();
		json3.addProperty("id", 3);
		json3.addProperty("name", "Clint");
		json3.addProperty("surname", "Eastwood");
		json3.addProperty("pic", "http://2.bp.blogspot.com/-Pleua1JUrJg/UajruKT0gaI/AAAAAAAABy4/TDbntFwudPM/s640/Clint-Eastwood-.jpg");
		
		jsonA.add(json1);
		jsonA.add(json2);
		jsonA.add(json3);
		
		return jsonA.toString();
	}
	
	@RequestMapping(value = "/find.do", method = RequestMethod.POST)
	public @ResponseBody String search(@RequestParam("user") int u,										@RequestParam("shift") int s){
		
		return null;
	} 
	
}
