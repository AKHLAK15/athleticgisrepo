package com.athleticgis.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.athleticgis.model.AthleticgisFacade;

@ManagedBean
@SessionScoped
public class GuestPreferences implements Serializable {
	private static final long serialVersionUID = -2704507019467340824L;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	private String theme = "bootstrap";
	
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

	public String getTheme(){        
        //return the theme from the database
		if(userInfoBean.getLoggedIn() && userInfoBean.getUser().getUserDetail() != null && userInfoBean.getUser().getUserDetail().getTheme() != null) {
			theme = userInfoBean.getUser().getUserDetail().getTheme();
		}
		return theme;
    }

    public void setTheme(String theme){
        //send the theme to the database
    	AthleticgisFacade.updateTheme(theme, userInfoBean.getUser_id());
    }
}
