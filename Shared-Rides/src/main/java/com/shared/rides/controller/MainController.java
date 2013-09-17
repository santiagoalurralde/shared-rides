package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {	

	@RequestMapping(value = "find.htm")
	public ModelAndView search(@RequestParam("prof") int profile, 
								@RequestParam("shift") int shift, 
								@RequestParam("marker") String marker)
            throws ServletException, IOException {

		
		return new ModelAndView("login");
	}

}
