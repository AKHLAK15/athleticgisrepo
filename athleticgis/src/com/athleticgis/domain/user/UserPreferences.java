package com.athleticgis.domain.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="userpreferences")
public class UserPreferences implements Serializable {
	private static final long serialVersionUID = -5787327626871467868L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long userpreferences_id;

	/**
	 * @return the userpreferences_id
	 */
	public Long getUserpreferences_id() {
		return userpreferences_id;
	}

	/**
	 * @param userpreferences_id the userpreferences_id to set
	 */
	public void setUserpreferences_id(Long userpreferences_id) {
		this.userpreferences_id = userpreferences_id;
	}
}
