package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Address;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;
import com.shared.rides.util.DistanceHaversine;
import com.shared.rides.util.ReadGPXFile;

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
	private Address addressUser;
	private double latitudeUser;
	private double longitudeUser;
	private int dist;
	
	public List findUsers(int profile, int shift, double longitudeSelected, double latitudeSelected){
		
		userList = userDAO.listAll();
		//Filtro la lista de acuerdo al perfil y el turno
		cleanList(profile, shift);
		//Con la lista filtrada, vemos que usuarios tienen una distancia mejor a 10 cuadras = 1000 mts.
		if (profile == 0){
			for(int i = 0; i < userList.size() ; i++){
				addressUser = userList.get(i).getAddress();
				latitudeUser = addressUser.getMarker().getLatitude();
				longitudeUser = addressUser.getMarker().getLongitude();
				dist = DistanceHaversine.calculateDistance(latitudeSelected, longitudeSelected, latitudeUser, longitudeUser);		
				//Si la distancia es mayor a 1000 mts, lo saco de la lista
				if (dist > 1000) userList.remove(i);
			}
		}
		else{
			boolean needRemove = true;
			
			//Recorro todos los tracks de cada persona
			//Recordemos que un driver puede tener varios track de acuerdo al dia
			for(int i = 0; i < userList.size(); i++){
				List<Track> trackList = userList.get(i).getDriver().getTracks();
				
				//Por cada track asociado, tengo que buscar todos los puntos de ese track
				for(int j = 0; j < trackList.size(); j++){
					double [][] pointsList = ReadGPXFile.readFile(trackList.get(j).getPathFile());
					
					for (int k = 0; k < pointsList.length; k++){
						dist = DistanceHaversine.calculateDistance(latitudeSelected, longitudeSelected, pointsList[k][0], pointsList[k][1]);
						if (dist < 1000) needRemove = false;
					}
					//Si no ningun punto del track esta a una distancia menor de 1000 mts
					//entonces elimino ese track
					if (needRemove) trackList.remove(j);
				}
				//Si no hay ningun track que pase cerca elimino el usuario de la lista
				if(trackList.isEmpty()) userList.remove(i);
			}
		}
		
		//if (userList.isEmpty()) return null;
		return userList;
	}
	
	//Funcion que filtra la lista de usuarios dependiendo el perfil y el turno
	private void cleanList(int profile, int shift){
		//Peaton y turno mañana
		if (profile == 0 && shift == 0){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getPedestrian().equals(null) || !(u.getShift().getShiftName().equals("Mañana"))){
					userList.remove(i);
				}			
			}
		}		
		//Peaton y turno tarde
		if (profile == 0 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getPedestrian().equals(null) || !(u.getShift().getShiftName().equals("Tarde"))){
					userList.remove(i);
				}			
			}
		}		
		//Conductor y turno mañana
		if (profile == 1 && shift == 0){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getDriver().equals(null) || !(u.getShift().getShiftName().equals("Mañana"))){
					userList.remove(i);
				}			
			}
		}
		//Conductor y turno tarde
		if (profile == 1 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getDriver().equals(null) || !(u.getShift().getShiftName().equals("Tarde"))){
					userList.remove(i);
				}			
			}
		}
	}
	
	/*
	//Funcion que me convierte la lista de usuarios en un JSONArray
	private JSONArray createJSON(List list){
		JSONArray listJSONArray = new JSONArray();
		
		for (int i = 0; i < list.size(); i++){
			JSONObject user = new JSONObject(list.get(i));
			listJSONArray.put(user);
		}
		return listJSONArray;
	}	
	*/
}

	