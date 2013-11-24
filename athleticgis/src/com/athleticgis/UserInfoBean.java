package com.athleticgis;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
 
@ManagedBean
@SessionScoped
public class UserInfoBean implements Serializable {
	private static final long serialVersionUID = 5066455190365405534L;
	private String name;
	private String password;
	private Boolean rememberMe;
	private Boolean admin;
	private Boolean isGeneral;
	private Long user_id;
	
	/**
	 * @return the user_id
	 */
	public Long getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	
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
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}
//	public void setName(String name) {
//		this.name = name;
//	}
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
