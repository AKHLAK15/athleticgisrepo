package com.athleticgis.view;

import java.io.Serializable;

import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;  
import javax.faces.event.ActionEvent;  
  





import org.primefaces.model.map.DefaultMapModel;  
import org.primefaces.model.map.LatLng;  
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

@ManagedBean
@ViewScoped
public class CreatePathBean implements Serializable {
	private static final long serialVersionUID = -8907242457718889989L;
	private MapModel emptyModel = new DefaultMapModel();
	Polyline polyline;
	
    
    private String title;  
      
    private double lat;  
      
    private double lng;  
  
    public CreatePathBean() {  
    	//emptyModel = new DefaultMapModel(); 
    	polyline = new Polyline();
    	//are these necessary?
        polyline.setStrokeWeight(2);
		polyline.setStrokeColor("#FF0000");
		polyline.setStrokeOpacity(1.0);
    }  
      
    public MapModel getEmptyModel() {  
        return emptyModel;  
    }  
      
    public void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }  
      
    public String getTitle() {  
        return title;  
    }  
  
    public void setTitle(String title) {  
        this.title = title;  
    }  
  
    public double getLat() {  
        return lat;  
    }  
  
    public void setLat(double lat) {  
        this.lat = lat;  
    }  
  
    public double getLng() {  
        return lng;  
    }  
  
    public void setLng(double lng) {  
        this.lng = lng;  
    }  
      
    public void addMarker(ActionEvent actionEvent) {  
        //Marker marker = new Marker(new LatLng(lat, lng), title);  
        //emptyModel.addOverlay(marker);  
    	LatLng coord = new LatLng(lat, lng);
    	
    	System.out.println("lat: " + lat + "," + "lng: " + lng);
    	polyline.getPaths().add(coord);
          
        addMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Point Added", "Lat:" + lat + ", Lng:" + lng));  
    }
    
    public String getCenter() {
		// did an average of the coordinates in getCoordinates
		return "43.83193516,-91.22337865";
	}
}
