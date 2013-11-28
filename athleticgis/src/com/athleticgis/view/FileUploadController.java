package com.athleticgis.view;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;  
  

import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.event.FileUploadEvent;  
import org.primefaces.model.UploadedFile;  
import org.xml.sax.SAXException;

import com.athleticgis.model.AthleticgisFacade;
import com.athleticgis.model.gis.ActivityPoint;
import com.athleticgis.util.file.gpx.GPXParser;
import com.athleticgis.util.file.gpx.beans.GPX;
import com.athleticgis.util.file.gpx.beans.Track;
import com.athleticgis.util.file.gpx.beans.Waypoint;

@ManagedBean
@RequestScoped
public class FileUploadController { 
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
  
    /**
	 * @return the userInfoBean
	 */
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	/**
	 * @param userInfoBean the userInfoBean to set
	 */
	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public void handleFileUpload(FileUploadEvent event) throws ParserConfigurationException, SAXException {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage(null, msg);  
        upload(event.getFile());
    } 
    
public void upload(UploadedFile file) throws ParserConfigurationException, SAXException {
		
        try {
            
            InputStream in=file.getInputstream();
            
            GPXParser p = new GPXParser();
            GPX gpx = p.parseGPX(in);
            com.athleticgis.model.gis.Activity a = new com.athleticgis.model.gis.Activity();
            a.setName(file.getFileName());
            //AthleticgisFacade af = new AthleticgisFacade();
            a.setUser(AthleticgisFacade.findUserByUsername(userInfoBean.getName()));
            
            List<ActivityPoint> activityPoints = new ArrayList<ActivityPoint>();
            for(Track t : gpx.getTracks()) {
            	for(Waypoint  wp : t.getTrackPoints()) {
            		//System.out.println(wp.getLatitude() + "," + wp.getLongitude());
            		com.athleticgis.model.gis.ActivityPoint activityPoint = new ActivityPoint();
            		activityPoint.setLatitude(wp.getLatitude());
            		activityPoint.setLongitude(wp.getLongitude());
            		activityPoint.setTime(new Timestamp(wp.getTime().getTime()));
            		activityPoint.setElevation(wp.getElevation());
            		activityPoints.add(activityPoint);
            	}
            }
            a.setWaypoints(activityPoints);
            
            
            AthleticgisFacade.persistActivityAndActivityPoints(a, activityPoints);
            
            in.close();
            //br.close();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
	}
} 