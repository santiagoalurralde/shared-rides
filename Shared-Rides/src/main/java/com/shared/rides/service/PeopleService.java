package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

@Service
public class PeopleService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAssociationDAO assocDAO;
	
	public String getPeople(long userId){
		ModelAndView model = null;
		User u = new User(userId);
		u = userDAO.load(u);
		
		List <Association> supplierAssoc = userDAO.getMyRequests(u);
		List <Association> applicantList = u.getAssociations();
		JsonArray jsonList = new JsonArray();
		JsonArray pendingList = new JsonArray();
		JsonArray acceptedList = new JsonArray();
		
		
		//Aca estoy buscando aquellas asociaciones que yo he enviado a otras personas
		for (int j = 0; j < supplierAssoc.size(); j++){
			Association assoc = supplierAssoc.get(j);
			long idSupplier = assocDAO.getSupplierId(assoc);
			User supplier = new User(idSupplier);
			
			if (assoc.getState().equals(State.PENDING)){
				String fullNameSupplier = supplier.getName() + supplier.getSurname();				
				
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("assocId", assoc.getAssociationId());
				jsonUser.addProperty("supplierName", fullNameSupplier);
				
				pendingList.add(jsonUser);
			}
			if (assoc.getState().equals(State.ACCEPTED)){
				String fullNameSupplier = supplier.getName() + supplier.getSurname();
				String pictureSupplier = supplier.getPicture();
				
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("assocId", assoc.getAssociationId());
				jsonUser.addProperty("supplierName", fullNameSupplier);
				jsonUser.addProperty("supplierPic", pictureSupplier);
				
				acceptedList.add(jsonUser);
			}
			
		}
		
		//Aca estoy viendo directamente las asociaciones que yo recibi que pude o no haber aceptado
		for(int i = 0; i < applicantList.size(); i++){
			Association assoc = applicantList.get(i);
			if (assoc.getState().equals(State.PENDING)){
				String fullNameApplier = assoc.getApplier().getName() + assoc.getApplier().getSurname();
				
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("assocId", assoc.getAssociationId());
				jsonUser.addProperty("applicantName", fullNameApplier);
				
				pendingList.add(jsonUser);
			}
			if (assoc.getState().equals(State.ACCEPTED)){
				String fullNameApplier = assoc.getApplier().getName() + assoc.getApplier().getSurname();
				String pictureApplier = assoc.getApplier().getPicture();
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("assocId", assoc.getAssociationId());
				jsonUser.addProperty("applicantName", fullNameApplier);
				jsonUser.addProperty("applicantPic", pictureApplier);
				
				acceptedList.add(jsonUser);
			}
		}
	
		jsonList.add(pendingList);
		jsonList.add(acceptedList);
		
		return jsonList.toString();
	}
}
