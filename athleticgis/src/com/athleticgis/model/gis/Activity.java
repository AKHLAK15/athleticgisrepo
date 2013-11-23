package com.athleticgis.model.gis;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy="activity")
	private List<Waypoint> waypoints;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
