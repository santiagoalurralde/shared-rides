package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.domain.User;
import com.shared.rides.service.ShowProfileService;


@Controller
@RequestMapping(value = "profile")
public class ProfileController {
	
		@Autowired
		private ShowProfileService showProfileService;
	
		@RequestMapping(value = "/{userId}.do")
		public ModelAndView compareId(@PathVariable("userId") int id, 
										HttpServletRequest request){
			HttpSession s = request.getSession();
			User u = (User)s.getAttribute("user");
			
			if (id == u.getUserId()) return new ModelAndView("redirect:/{userId}?ref=o");
			
			return new ModelAndView("redirect:/{userId}?ref=a");
		}
	
		@RequestMapping(value = "/{userId}.do?ref=o")
		public ModelAndView showOwnProfile(){
		
			return null;
			
		}
		
		@RequestMapping(value = "/{userId}.do?ref=a")
		public ModelAndView showProfile(){
			return null;
			
		}

}
