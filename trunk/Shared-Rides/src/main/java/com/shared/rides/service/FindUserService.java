package com.shared.rides.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Address;
import com.shared.rides.domain.Shift;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;
import com.shared.rides.util.DistanceHaversine;
import com.shared.rides.util.ReadGPXFile;

/*
 Servicio que se encarga de buscar todas los usuarios que pasan por el marker seleccionado
 por el usuario. 
 Si el profile es 1 --> El usuario busca un peaton
 Si el profile es 2 --> El usuario busca un conductor
 shift 1 --> turno mañana
 shift 2 --> turno tarde
 */

@Service
public class FindUserService {

	@Autowired
	private IUserDAO userDAO;
	private List<User> userList;
	private Address addressUser;
	private double latitudeUser;
	private double longitudeUser;
	private double [][] markers;
	private int dist;
	
	public String findUsers(int profile, int shift, String m){

		parseMarkers(m);
		userList = userDAO.listAll();		
		//Filtro la lista de acuerdo al perfil y el turno
		filterList(profile, shift);
		//Con la lista filtrada, vemos que usuarios tienen una distancia menor a 10 cuadras = 1000 mts.
		boolean needRemove = true;
		if (profile == 1){
			//Si estoy buscando un peatón; quiere decir que soy un conductor y que en la vista marque un
			//track; por ende tengo varios markers y los tengo que comparar con la direccion de cada peaton
			for(int i = 0; i < userList.size() ; i++){
				addressUser = userList.get(i).getAddress();
				latitudeUser = addressUser.getMarker().getLatitude();
				longitudeUser = addressUser.getMarker().getLongitude();
				
				for(int j = 0; j < markers.length; j++){
					dist = DistanceHaversine.calculateDistance(markers[j][1], markers[j][0], latitudeUser, longitudeUser);
					if (dist < 1000) needRemove = false;
				}	
				if (needRemove) userList.remove(i);
			}
		}
		else{
			//Sino estamos buscando un conductor; por ende tenemos que comparar todos tracks de los conductores
			//con el único marker que se encuentra en la matriz "markers" 
			for(int i = 0; i < userList.size(); i++){
				List<Track> trackList = userList.get(i).getDriver().getTracks();
				//Por cada track asociado, tengo que buscar todos los puntos de ese track
				for(int j = 0; j < trackList.size(); j++){
					double [][] trackPoints = ReadGPXFile.readFile(trackList.get(j).getPathFile());
					
					for (int k = 0; k < trackPoints.length; k++){
						dist = DistanceHaversine.calculateDistance(markers[0][1], markers[0][0], trackPoints[k][0], trackPoints[k][1]);
						if (dist < 1000) needRemove = false;	
					}
					if (needRemove) trackList.remove(j);
				}
				//Si no hay ningun track que pase cerca elimino el usuario de la lista
				if(trackList.isEmpty()) userList.remove(i);
			}
		}
	
		return createJson(userList).toString();
	}
	
	//Funcion que filtra la lista de usuarios dependiendo el perfil y el turno
	private void filterList(int profile, int shift){
		boolean isDelete = false;
		//Peaton y turno mañana
		if (profile == 1 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getPedestrian() == null || !(u.getShift().equals(Shift.MOORNING))){
					userList.remove(i);
					isDelete = true;
				}
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}		
		//Peaton y turno tarde
		if (profile == 1 && shift == 2){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getPedestrian() == null || !(u.getShift().equals(Shift.AFTERNOON))){
					userList.remove(i);
					isDelete = true;
				}
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}		
		//Conductor y turno mañana
		if (profile == 2 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getDriver() == null || !(u.getShift().equals(Shift.MOORNING))){
					userList.remove(i);
					isDelete = true;				
				}			
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}
		//Conductor y turno tarde
		if (profile == 2 && shift == 2){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getDriver() == null || !(u.getShift().equals(Shift.AFTERNOON))){
					userList.remove(i);
					isDelete = true;
				}
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}
	}
	
	//Funcion que me convierte la lista de usuarios en un JSONArray
	private JsonArray createJson(List<User> list){
		JsonArray jsonList = new JsonArray();
		
		for (int i = 0; i < list.size(); i++){
			JsonObject jsonUser = new JsonObject();
			
			jsonUser.addProperty("id", list.get(i).getUserId());;
			jsonUser.addProperty("name", list.get(i).getName());;
			jsonUser.addProperty("surname", list.get(i).getSurname());
			jsonUser.addProperty("picture", list.get(i).getPicture());;
			
			jsonList.add(jsonUser);
		}		
		return jsonList;
	}	
	
	//Función que se encarga de convertir el String de los markers recibido en la vista a una matriz
	private void parseMarkers(String m){
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(m);		
		JsonArray arrayMarkers = (JsonArray) obj;
		markers = new double [arrayMarkers.size()][2];
		
		for (int i = 0; i < arrayMarkers.size(); i++){
			JsonObject jsonMarker = (JsonObject) arrayMarkers.get(i);
			markers[i][0] = jsonMarker.get("lon").getAsFloat();
			markers[i][1] = jsonMarker.get("lat").getAsFloat();
		}
	}
		
}

	