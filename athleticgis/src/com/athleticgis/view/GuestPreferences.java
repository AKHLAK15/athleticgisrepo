package com.athleticgis.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class GuestPreferences {
	public String getTheme(){        
        //return the theme from the database
		return "excite-bike";
    }

    public void setTheme(String theme){
        //send the theme to the database
    }
}
