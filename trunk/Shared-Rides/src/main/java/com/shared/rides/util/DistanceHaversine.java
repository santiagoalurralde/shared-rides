package com.shared.rides.util;

/*
 * Clase que calcula la distancia entre dos puntos de los cuales tengo sus longitudes y 
 * latitudes. Usamos la función de Haversine
 */

public class DistanceHaversine {
	
		//Constante
		private static final double earthRadius = 6371; // km
		
		public static int calculateDistance(double lat1, double long1, double lat2, double long2){			
			lat1 = Math.toRadians(lat1);
			long1 = Math.toRadians(long1);
			lat2 = Math.toRadians(lat2);
			long2 = Math.toRadians(long2);

			double dlon = (long2 - long1);
			double dlat = (lat2 - lat1);

			double sinlat = Math.sin(dlat / 2);
			double sinlon = Math.sin(dlon / 2);

			double a = (sinlat * sinlat) + Math.cos(lat1)*Math.cos(lat2)*(sinlon*sinlon);
			double c = 2 * Math.asin (Math.min(1.0, Math.sqrt(a)));

			double distanceInMeters = earthRadius * c * 1000;

			return (int)distanceInMeters;
		}
}
