package com.shared.rides.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.dao.interfaces.IOrganizationDAO;
import com.shared.rides.domain.Organization;
import com.shared.rides.service.SignupUserService;

//import com.shared.rides.service.SignupUserService;

@Controller
public class SignupUserController {

	@Autowired
	private SignupUserService signupUserService;
	
	@Autowired
	private IOrganizationDAO orgDAO;
	
	@RequestMapping("/signup.do")
	public ModelAndView showContactView(){
		ModelAndView model = new ModelAndView();
		List <Organization> orgList = orgDAO.listAll();
		List orgNameList = new ArrayList();
		for(Organization o : orgList){
			Map<String, Object> org = new HashMap();
			
			org.put("orgId", o.getOrganizationId());
			org.put("orgName", o.getName());
		
			orgNameList.add(org);
//			model.addObject("organizations", o);
		}
		model.addObject("organizations", orgNameList);
		model.setViewName("signup");
		return model;
	}
	
	@RequestMapping("/validateNewUser.do")
	public @ResponseBody boolean validateNewUser(@RequestParam("personalId") String personalId,
								HttpServletRequest request){
		return signupUserService.validateNewUser(personalId);
	}

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public @ResponseBody String signupUser(@RequestParam("organization") long organization,
											@RequestParam("personalId") String personalId,
											@RequestParam("pw") String pw,
											@RequestParam("name") String name,
											@RequestParam("surname") String surname,
											@RequestParam("email") String email,
											@RequestParam("phone") long phoneNumber,
//											@RequestParam("picture") String pic,
											@RequestParam("street") String street,
											@RequestParam("number") int numberStreet,
											@RequestParam("neighborhood") String neighborhood,
											@RequestParam("shift") String shift,
											@RequestParam("userType") String userType,
											@RequestParam("brand") String brand,
											@RequestParam("modelVehicle") String modelVehicle,
											@RequestParam("plateLetters") String plateLetters,
											@RequestParam("plateNumbers") String plateNumbers,
											@RequestParam("numberSeats") int numberSeats,
											@RequestParam("days") String days,
											HttpServletRequest r
											){	
		String pic = r.getSession().getAttribute("pic").toString();
		String licensePlate = plateLetters + " " + plateNumbers;
		signupUserService.signupUser(organization, personalId, pw, name, surname, phoneNumber, email, street, numberStreet, neighborhood, shift, userType, brand, modelVehicle, licensePlate, numberSeats, days, pic);
		return "msg";
	}	
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody String uploadFileHandler(@RequestParam("picture") MultipartFile file,
    												HttpServletRequest request) {
		return signupUserService.uploadPicFile(file, request);
    }
}
