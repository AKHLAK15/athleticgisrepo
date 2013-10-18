package com.meallen.geocoding;

public class Geometry {
	Bound bounds;
	Coordinate location;
	String location_type;
	Viewport viewport;
	
	public Bound getBounds() {
		return bounds;
	}
	public void setBounds(Bound bounds) {
		this.bounds = bounds;
	}
	public Coordinate getLocation() {
		return location;
	}
	public void setLocation(Coordinate location) {
		this.location = location;
	}
	public String getLocation_type() {
		return location_type;
	}
	public void setLocation_type(String location_type) {
		this.location_type = location_type;
	}
	public Viewport getViewport() {
		return viewport;
	}
	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}
}
