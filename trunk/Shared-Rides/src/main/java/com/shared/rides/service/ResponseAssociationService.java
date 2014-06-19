package com.shared.rides.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

@Service
public class ResponseAssociationService {

	@Autowired
	private IAssociationDAO assocDAO;
	
	public String sendResponseAssoc(long assocId, boolean response){
		Association assoc = new Association(assocId);
		assoc = assocDAO.load(assoc);
		
		String msg = null;
		
		if (response == true){
			assoc.setState(State.ACCEPTED);
			msg = "El usuario ha aceptado la solicitud";
		}
		else{
			assoc.setState(State.CANCELLED);
			msg = "El usuario ha rechazado la solicitud";
		}
		assocDAO.update(assoc);
		
		return msg;
	}
}
