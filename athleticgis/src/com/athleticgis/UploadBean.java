package com.athleticgis;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import javax.xml.parsers.ParserConfigurationException;

import org.alternativevision.gpx.GPXParser;
import org.alternativevision.gpx.beans.GPX;
import org.alternativevision.gpx.beans.Track;
import org.alternativevision.gpx.beans.Waypoint;
import org.xml.sax.SAXException;

import com.athleticgis.model.AthleticgisFacade;

@ManagedBean
@RequestScoped
public class UploadBean implements Serializable {
	private static final long serialVersionUID = -290191374294978569L;
	private Part file;
	private String fileContent;
	private String activityName;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;

	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
//	public void testHibernate() {

//	}

	public String upload() throws ParserConfigurationException, SAXException {
		//testHibernate();
		//userDao.persist(new Users());
		System.out.println("call upload...");      
		System.out.println("content-type: " + file.getContentType());
		System.out.println("filename: " + file.getName());
		System.out.println("size: " + file.getSize());
        try {
           // byte[] results=new byte[(int)file.getSize()];
//            for(int i = 0; i < results.length; i++) {
//            	System.out.print(results[i]);
//            }
            
            InputStream in=file.getInputStream();
            //System.out.println(in.read(results)); 
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String sCurrentLine;
//            while ((sCurrentLine = br.readLine()) != null) {
//				System.out.println(sCurrentLine);
//			}
            GPXParser p = new GPXParser();
            GPX gpx = p.parseGPX(in);
            com.athleticgis.model.gis.Activity a = new com.athleticgis.model.gis.Activity();
            a.setName(activityName);
            
            List<com.athleticgis.model.gis.Waypoint> waypoints = new ArrayList<com.athleticgis.model.gis.Waypoint>();
            for(Track t : gpx.getTracks()) {
            	for(Waypoint  wp : t.getTrackPoints()) {
            		//System.out.println(wp.getLatitude() + "," + wp.getLongitude());
            		com.athleticgis.model.gis.Waypoint waypoint = new com.athleticgis.model.gis.Waypoint();
            		waypoint.setLatitude(wp.getLatitude());
            		waypoint.setLongitude(wp.getLongitude());
            		waypoints.add(waypoint);
            	}
            }
            a.setWaypoints(waypoints);
            
            AthleticgisFacade af = new AthleticgisFacade();
            af.persistActivityAndWaypoints(a, waypoints);
            
            in.close();
            //br.close();
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Uploaded!"));
        return null;
	}
	
//	public String save() {
//		
//		if(file != null && file.getSize() > 0 && activityName != null) {
//			InputStream in;
//		try {
//			in = file.getInputStream();
//			setFileContent(new Scanner(in)
//					.useDelimiter("\\A").next());
//			in.close();
//		} catch (IOException e) {
//			// Error handling
//		}
//		
//		
//			Activity a = new Activity();
//			a.setDate(new Timestamp(new Date().getTime()));
//			a.setName(activityName);
//			a.setUserName(userInfoBean.getName());
//			activityModel.addActivity(a);
//			activityName = null;
//			
//			return "dashboard?faces-redirect=true";
//		} else {
//			return null;
//		}
//	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		//kmlObject = value;
		file = (Part) value;
//		if (file.getSize() > 1024) {
//			msgs.add(new FacesMessage("file too big"));
//		}
//		if (!"text/plain".equals(file.getContentType())) {
//			msgs.add(new FacesMessage("not a text file"));
//		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	

}
