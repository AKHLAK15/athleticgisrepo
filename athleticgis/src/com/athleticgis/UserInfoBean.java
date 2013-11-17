package com.athleticgis;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
 
@ManagedBean
@SessionScoped
public class UserInfoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private Boolean rememberMe;
	private Boolean admin;
	private Boolean isGeneral;
	
	public Boolean getAdmin() {
		//return isAdmin;
		
		//hardcoded
		return "admin".equals(name);
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public Boolean getIsGeneral() {
		return isGeneral;
	}
	public void setIsGeneral(Boolean isGeneral) {
		this.isGeneral = isGeneral;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(Boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	
	public Boolean getLoggedIn() {
		return name != null;
	}
}
