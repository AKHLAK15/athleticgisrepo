package com.athleticgis.util.file.fit;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.athleticgis.domain.activity.ActivityPoint;
import com.athleticgis.util.gis.GISCalculator;
import com.garmin.fit.RecordMesg;
import com.garmin.fit.RecordMesgListener;

public class FitListener implements RecordMesgListener {
	List<ActivityPoint> activityPoints = new ArrayList<ActivityPoint>();
	
	/**
	 * @return the activityPoints
	 */
	public List<ActivityPoint> getActivityPoints() {
		return activityPoints;
	}

	@Override
	public void onMesg(RecordMesg mesg) {
		
		
		GISCalculator calc = new GISCalculator();
		
		ActivityPoint activityPoint = new ActivityPoint();
		
		
		
		
		
		
		if(mesg.getPositionLat() != null) {
			//System.out.println("lat: " + mesg.getPositionLat());
			activityPoint.setLatitude(calc.convertFromDegreesToWGS84(mesg.getPositionLat()*1.0));
		}
		
		if(mesg.getPositionLong() != null) {
			//System.out.println("lng: " + mesg.getPositionLong());
			activityPoint.setLongitude(calc.convertFromDegreesToWGS84(mesg.getPositionLong()*1.0));
		}
		
		if(mesg.getAltitude() != null) {
			//System.out.println("alt: " + mesg.getAltitude());
			activityPoint.setElevation(mesg.getAltitude()*1.0);
		}
		
		if(mesg.getTimestamp() != null) {
			//System.out.println("time: " + mesg.getTimestamp());
			activityPoint.setTime(new Timestamp(mesg.getTimestamp().getTimestamp()));
		}
		activityPoints.add(activityPoint);
	}
}
