package com.athleticgis.util.file.fit;

import com.garmin.fit.RecordMesg;
import com.garmin.fit.RecordMesgListener;

public class FitDecoder {
	
//	Decode decode = new Decode();
//    MesgBroadcaster mesgBroadcaster = new MesgBroadcaster(decode);
//    Listener listener = new Listener();
//    FileInputStream in;
	
	
	
	private static class Listener implements RecordMesgListener {

		@Override
		public void onMesg(RecordMesg mesg) {
			if(mesg.getPositionLat() != null) {
				System.out.println(mesg.getPositionLat());
			}
			
			if(mesg.getPositionLong() != null) {
				System.out.println(mesg.getPositionLong());
			}
			
			if(mesg.getAltitude() != null) {
				System.out.println(mesg.getAltitude());
			}
			
			if(mesg.getTimestamp() != null) {
				System.out.println(mesg.getTimestamp());
			}
		}
//	      public void onMesg(FileIdMesg mesg) {
//	         System.out.println("File ID:");
//
//	         if ((mesg.getType() != null) && (mesg.getType() != File.INVALID)) {
//	            System.out.print("   Type: ");
//	            System.out.println(mesg.getType().getValue());
//	         }
//
//	         if ((mesg.getManufacturer() != null) && (!mesg.getManufacturer().equals(Manufacturer.INVALID))) {
//	            System.out.print("   Manufacturer: ");
//	            System.out.println(mesg.getManufacturer());
//	         }
//
//	         if ((mesg.getProduct() != null) && (!mesg.getProduct().equals(Fit.UINT16_INVALID))) {
//	            System.out.print("   Product: ");
//	            System.out.println(mesg.getProduct());
//	         }
//
//	         if ((mesg.getSerialNumber() != null) && (!mesg.getSerialNumber().equals(Fit.UINT32Z_INVALID))) {
//	            System.out.print("   Serial Number: ");
//	            System.out.println(mesg.getSerialNumber());
//	         }
//
//	         if ((mesg.getNumber() != null) && (!mesg.getNumber().equals(Fit.UINT16_INVALID))) {
//	            System.out.print("   Number: ");
//	            System.out.println(mesg.getNumber());
//	         }
//	      }
//
//	      public void onMesg(RecordMesg mesg) {
//	         System.out.println("User profile:");
//
//	         if ((mesg.getFriendlyName() != null) && (!mesg.getFriendlyName().equals(Fit.STRING_INVALID))) {
//	            System.out.print("   Friendly Name: ");
//	            System.out.println(mesg.getFriendlyName());
//	         }
//
//	         if (mesg.getGender() != null) {
//	            if (mesg.getGender() == Gender.MALE)
//	               System.out.println("   Gender: Male");
//	            else if (mesg.getGender() == Gender.FEMALE)
//	               System.out.println("   Gender: Female");
//	         }
//
//	         if ((mesg.getAge() != null) && (!mesg.getAge().equals(Fit.UINT8_INVALID))) {
//	            System.out.print("   Age [years]: ");
//	            System.out.println(mesg.getAge());
//	         }
//
//	         if ((mesg.getWeight() != null) && (!mesg.getWeight().equals(Fit.FLOAT32_INVALID))) {
//	            System.out.print("   Weight [kg]: ");
//	            System.out.println(mesg.getWeight());
//	         }
//	      }
	   }
}
