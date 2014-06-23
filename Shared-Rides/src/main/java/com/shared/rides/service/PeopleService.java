package com.shared.rides.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

/*
 * Este servicio se encarga de devolver la lista de personas asociadas y pendientes de recibir respuesta
 * de un usuario en particular.
 * El metodo getPeople retorna un json formado por dos jsonArray; uno formado por las personas ya asociadas,
 * donde se muestra el nombre de la persona y la foto de perfil (ya que ya estan asociados y no es mas un
 * dato privado); y otra lista donde se encuentran todas las personas pendientes de responder una solicitud
 * enviada por el usuario o aquellas personas que le han enviado una solicitud a la persona logueada y debe
 * responder; en este caso, se envia el nombre de la persona, sin la foto de perfil y tambien si el usuario
 * es el suplicante o el aplicante de la asociacion.
 */

@Service
public class PeopleService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAssociationDAO assocDAO;
	
	public String getPeople(long userId){
		User u = new User(userId);
		u = userDAO.load(u);
		
		List <Association> applicantAssocList = u.getAssociations();
		List <Association> supplierAssocList = userDAO.getMyRequests(u);	
		List <Long> acceptedIdList = new ArrayList<Long>();
		List <Long> pendingIdList = new ArrayList<Long>();
		JsonArray jsonList = new JsonArray();
		JsonArray pendingList = new JsonArray();
		JsonArray acceptedList = new JsonArray();
		
		//Aca estoy viendo directamente las asociaciones que yo recibi que pude o no haber aceptado
		for(int i = 0; i < applicantAssocList.size(); i++){
			Association assoc = applicantAssocList.get(i);
			User applier = assoc.getApplier();
			if (assoc.getState().equals(State.PENDING) && pendingIdList.contains(applier.getUserId())==false){
				String fullNameApplier = applier.getName() + " " + applier.getSurname();
						
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("userId", applier.getUserId());
				jsonUser.addProperty("name", fullNameApplier);
				jsonUser.addProperty("side", "supplier");
				jsonUser.addProperty("pic", "user.jpg");
				
				pendingIdList.add(applier.getUserId());
				pendingList.add(jsonUser);
			}
			if (assoc.getState().equals(State.ACCEPTED) && acceptedIdList.contains(applier.getUserId())==false){
				String fullNameApplier = applier.getName() + " " + applier.getSurname();
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("userId", applier.getUserId());
				jsonUser.addProperty("name", fullNameApplier);
				jsonUser.addProperty("pic", applier.getPicture());
						
				acceptedIdList.add(applier.getUserId());
				acceptedList.add(jsonUser);
			}
		}

		//Aca estoy buscando aquellas asociaciones que yo he enviado a otras personas
		for (int j = 0; j < supplierAssocList.size(); j++){
			Association assoc = supplierAssocList.get(j);
			long idSupplier = assocDAO.getSupplierId(assoc);
			
			User supplier = new User(idSupplier);
			supplier = userDAO.load(supplier);
		
			if (assoc.getState().equals(State.PENDING) && pendingIdList.contains(supplier.getUserId())==false){
				String fullNameSupplier = supplier.getName() + " " +  supplier.getSurname();				
				
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("userId", supplier.getUserId());
				jsonUser.addProperty("name", fullNameSupplier);
				jsonUser.addProperty("side", "applicant");
				jsonUser.addProperty("pic", "user.jpg");
				
				pendingIdList.add(supplier.getUserId());
				pendingList.add(jsonUser);
			}
			if (assoc.getState().equals(State.ACCEPTED) && acceptedIdList.contains(supplier.getUserId())==false){
				String fullNameSupplier = supplier.getName() + " " + supplier.getSurname();
				String pictureSupplier = supplier.getPicture();
				
				JsonObject jsonUser = new JsonObject();
				jsonUser.addProperty("userId", supplier.getUserId());
				jsonUser.addProperty("name", fullNameSupplier);
				jsonUser.addProperty("pic", pictureSupplier);
				
				acceptedIdList.add(supplier.getUserId());
				acceptedList.add(jsonUser);
			}
		}
		
		
		jsonList.add(pendingList);
		jsonList.add(acceptedList);		
		return jsonList.toString();
		
	}
}
