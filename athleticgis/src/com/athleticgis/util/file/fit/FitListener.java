package com.athleticgis.util.file.fit;

import com.garmin.fit.RecordMesg;
import com.garmin.fit.RecordMesgListener;

public class FitListener implements RecordMesgListener {
	@Override
	public void onMesg(RecordMesg mesg) {
		if(mesg.getPositionLat() != null) {
			System.out.println("lat: " + mesg.getPositionLat());
		}
		
		if(mesg.getPositionLong() != null) {
			System.out.println("lng: " + mesg.getPositionLong());
		}
		
		if(mesg.getAltitude() != null) {
			System.out.println("alt: " + mesg.getAltitude());
		}
		
		if(mesg.getTimestamp() != null) {
			System.out.println("time: " + mesg.getTimestamp());
		}
	}
}
