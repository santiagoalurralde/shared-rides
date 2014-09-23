package com.shared.rides.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;
import com.shared.rides.util.EmailSender;

@Service
public class RequestAssociationService {
	
	@Autowired
	private IAssociationDAO assocDAO;
	@Autowired
	private IUserDAO userDAO;
	
	private User applicantUser;
	private User supplierUser;
	private String message;
		
	/*
	 * Funcion que se usa cuando el usuario envia una peticion de asociacion a otro usuario
	 * A la hora de setear el inout; se setea un 0 si es in o un 1 si es out
	 */
	public String sendAssocRequest(int day, int inout, long idUser, long idApplicant){
		Date date = new Date();
		//Persona que hace la peticion
		applicantUser = userDAO.load(idApplicant);
		
		//Persona que tiene que responder
		supplierUser = userDAO.load(idUser);
		
		Association assoc = new Association();
		assoc.setDay(day);
		assoc.setInout(inout);
		assoc.setApplicantID(applicantUser);
		assoc.setState(State.PENDING);
		assoc.setDate(date);
		assocDAO.save(assoc);
		userDAO.newAssoc(supplierUser, assoc);
		/*
		try{
			EmailSender emailSender = new EmailSender();
			emailSender.sendEmail(supplierUser, applicantUser, 0);					
		}
		catch(Exception e){
			System.out.println(e);
		}*/	
		return message;
	}
}
