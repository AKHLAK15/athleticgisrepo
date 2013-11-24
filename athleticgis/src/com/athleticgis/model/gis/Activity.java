package com.athleticgis.model.gis;

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

@Entity
@Table
public class Activity implements Serializable {
	private static final long serialVersionUID = -1601934048327683904L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Long activity_id;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy="activity")//, fetch=FetchType.EAGER)
	private List<Waypoint> waypoints;
	
	/**
	 * @return the activity_id
	 */
	public Long getActivity_id() {
		return activity_id;
	}

	/**
	 * @param activity_id the activity_id to set
	 */
	public void setActivity_id(Long activity_id) {
		this.activity_id = activity_id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the waypoints
	 */
	public List<Waypoint> getWaypoints() {
		return waypoints;
	}
	
	/**
	 * @param waypoints the waypoints to set
	 */
	public void setWaypoints(List<Waypoint> waypoints) {
		this.waypoints = waypoints;
	}
}
