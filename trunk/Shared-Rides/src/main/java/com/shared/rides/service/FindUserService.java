package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.User;

/*
 Servicio que se encarga de buscar todas los usuarios que pasan por el marker seleccionado
 por el usuario. 
 Si el profile es 0 --> El usuario busca un peaton
 Si el profile es 1 --> El usuario busca un conductor
 shift 0 --> turno mañana
 shift 1 --> turno tarde
 */


@Service
public class FindUserService {

	@Autowired
	private IUserDAO userDAO;
	private List<User> userList;
	
	public List<User> findUsers(int profile, int shift, String markerSelected){
		
		userList = userDAO.listAll();
		cleanList(profile, shift);
		
		return null;
	}
	
	private void cleanList(int profile, int shift){
		//Peaton y turno mañana
		if (profile == 0 && shift == 0){
			for(int i = 0; i < userList.size(); i++){
				if(userList.get(i).isPedestrian()==false || userList.get(i).getShift() == 0){
					userList.remove(i);
				}			
			}
		}
				
		//Peaton y turno tarde
		if (profile == 0 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				if(userList.get(i).isPedestrian()==false || userList.get(i).getShift() == 1){
					userList.remove(i);
				}			
			}
		}
				
		//Conductor y turno mañana
		if (profile == 1 && shift == 0){
			for(int i = 0; i < userList.size(); i++){
				if(userList.get(i).isDriver()==false || userList.get(i).getShift() == 0){
					userList.remove(i);
				}			
			}
		}
				
		//Conductor y turno tarde
		if (profile == 1 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				if(userList.get(i).isDriver()==false || userList.get(i).getShift() == 1){
					userList.remove(i);
				}			
			}
		}
	}
	
	
	
}

	