package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.dao.persistence.VehicleDAOImplMySql;
import com.shared.rides.domain.Vehicle;
import com.shared.rides.service.VehicleService;

@Controller
public class HomeController {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private VehicleService vehicleService;
	
    @RequestMapping(value="/login.do")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Returning hello view");
        
        System.out.println(vehicleService.listAllVehicles()); 
        
        return new ModelAndView("login");
    }
    

    
    
}