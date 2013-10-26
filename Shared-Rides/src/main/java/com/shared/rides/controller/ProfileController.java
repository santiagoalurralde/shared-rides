package com.shared.rides.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.domain.User;
import com.shared.rides.service.ShowProfileService;

/*
 * Controlador que se encarga de recibir el id del perfil a buscar y luego llama
 * al servicio para obtener el json. Dependiendo si el usuario quiere ver su propio
 * perfil o no; se le pasa el 3° parametro como true o false.
 */

@Controller
//@RequestMapping(value = "profile")
public class ProfileController {
	
		@Autowired
		private ShowProfileService showProfileService;
	
		@RequestMapping(value = "/profile.do")
		public ModelAndView showProfile(@RequestParam("user") int id, 
										HttpServletRequest request){
			HttpSession s = request.getSession();
			User u = (User)s.getAttribute("user");
			ModelAndView model = new ModelAndView();
			
			if (id == u.getUserId()){
				model = showProfileService.getProfile(id, request, true);
				model.setViewName("profile");
				return model;
			}
			
			model = showProfileService.getProfile(id, request, false);
			model.setViewName("profile");
			
			return model;
		}

}
