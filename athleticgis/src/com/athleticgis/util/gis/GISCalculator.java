package com.athleticgis.util.gis;

import java.util.List;

import com.athleticgis.domain.activity.*;

;

public class GISCalculator {

	// activityPoints should be ordered from earliest point to latest
	public double computePathDistance(List<ActivityPoint> aps) {
		double distance = 0;
		for (int i = 0; i < aps.size() - 1; i++) {
			distance = distance
					+ distance(aps.get(i).getLatitude(), aps.get(i + 1)
							.getLatitude(), aps.get(i).getLongitude(),
							aps.get(i + 1).getLongitude(), aps.get(i)
									.getElevation(), aps.get(i + 1)
									.getElevation());
		}
		return distance;
	}

	/*
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 * 
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 */
	private double distance(double lat1, double lat2, double lon1, double lon2,
			double el1, double el2) {

		final int R = 6371; // Radius of the earth

		Double latDistance = deg2rad(lat2 - lat1);
		Double lonDistance = deg2rad(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c * 1000; // convert to meters

		double height = el1 - el2;
		distance = Math.pow(distance, 2) + Math.pow(height, 2);
		return Math.sqrt(distance);
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	
	public void centerMap() {
//		int minLat = Integer.MAX_VALUE;
//		int maxLat = Integer.MIN_VALUE;
//		int minLon = Integer.MAX_VALUE;
//		int maxLon = Integer.MIN_VALUE;

//		for (GeoPoint item : items) 
//		{ 
//
//		      int lat = item.getLatitudeE6();
//		      int lon = item.getLongitudeE6();
//
//		      maxLat = Math.max(lat, maxLat);
//		      minLat = Math.min(lat, minLat);
//		      maxLon = Math.max(lon, maxLon);
//		      minLon = Math.min(lon, minLon);
//		 }

//		mapController.zoomToSpan(Math.abs(maxLat - minLat), Math.abs(maxLon - minLon));
//		mapController.animateTo(new GeoPoint( (maxLat + minLat)/2, 
//		(maxLon + minLon)/2 )); 
//		
//		
//		double fitFactor = 1.5;
//		mapController.zoomToSpan((int) (Math.abs(maxLat - minLat) * fitFactor), (int)(Math.abs(maxLon - minLon) * fitFactor));
	}
}
