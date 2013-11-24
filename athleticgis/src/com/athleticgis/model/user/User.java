package com.athleticgis.model.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.athleticgis.model.gis.Activity;

@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = -1042291323559226622L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long user_id;

	@OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
	private List<UserRole> userroles;
	
	@OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
	private List<Activity> activities;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private int enabled;

	/**
	 * @return the activities
	 */
	public List<Activity> getActivities() {
		return activities;
	}

	/**
	 * @param activities the activities to set
	 */
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

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

	/**
	 * @return the userroles
	 */
	public List<UserRole> getUserroles() {
		return userroles;
	}

	/**
	 * @param userroles
	 *            the userroles to set
	 */
	public void setUserroles(List<UserRole> userroles) {
		this.userroles = userroles;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the enabled
	 */
	public int getEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
}
