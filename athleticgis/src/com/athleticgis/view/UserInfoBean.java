package com.athleticgis.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.athleticgis.domain.user.User;
import com.athleticgis.model.AthleticgisFacade;

import java.io.Serializable;
import java.util.Collection;
 
@ManagedBean
@SessionScoped
public class UserInfoBean implements Serializable {
	private static final long serialVersionUID = 5066455190365405534L;
	private String name;
	private String password;
	private Boolean rememberMe;
	private Boolean isAdmin;
	private Boolean isGeneral;
	private Long user_id;
	private User user;
	
	/**
	 * @return the user_id
	 */
	public Long getUser_id() {
		if(user_id == null) {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			name = userDetails.getUsername();
			user = AthleticgisFacade.findUserByUsername(name);
		}
		return user.getUser_id();
	}
//	/**
//	 * @param user_id the user_id to set
//	 */
//	public void setUser_id(Long user_id) {
//		this.user_id = user_id;
//	}
	
//	public Boolean getAdmin() {
//		//return isAdmin;
//		
//		//hardcoded
//		return "admin".equals(name);
//	}
//	public void setAdmin(Boolean admin) {
//		this.admin = admin;
//	}
	public Boolean getIsGeneral() {
		return isGeneral;
	}
	public void setIsGeneral(Boolean isGeneral) {
		this.isGeneral = isGeneral;
	}
	
	
	public String getName() {
		if(name == null) {
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			name = userDetails.getUsername();
		}
		return name;
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
	/**
	 * @return the isAdmin
	 */
	public Boolean getIsAdmin() {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)userDetails.getAuthorities();
		for(GrantedAuthority ga : authorities) {
			if("ROLE_ADMIN".equals(ga.getAuthority())) {		
				return true;
			}
		}
		return false;
	}
	/**
	 * @param isAdmin the isAdmin to set
	 */
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
}
