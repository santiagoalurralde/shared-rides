package com.shared.rides.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shared.rides.dao.interfaces.IUserDAO;

@Controller
public class HandlerFiles {

	@Autowired
	private IUserDAO userDAO;
	
	@Autowired
	private ServletContext context;

    @RequestMapping(value = "printImgFile.do")
    protected void downloadImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String picName = request.getParameter("pic");
        
        try {        	
        	String filePath = context.getInitParameter("img-upload");
            FileInputStream inputStream = new FileInputStream(filePath + picName); 
            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            byte[] bbuf = new byte[512];
            int length;
            while ((length = inputStream.read(bbuf)) != -1) {
                out.write(bbuf,0,length);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }        
    }
    

    @RequestMapping(value = "getGPXFile.do")
    protected void getGPXFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gpxName = request.getParameter("gpx");
  
        try {
        	String filePath = context.getInitParameter("gpx-upload");
            FileInputStream inputStream = new FileInputStream(filePath + gpxName); 
            response.setContentType("text/xml");
            OutputStream out = response.getOutputStream();
            byte[] bbuf = new byte[512];
            int length;
            while ((length = inputStream.read(bbuf)) != -1) {
                out.write(bbuf,0,length);
            }
            inputStream.close();
            out.flush();
            out.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }        
    }
	
}
