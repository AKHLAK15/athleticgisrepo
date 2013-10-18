package com.meallen.geocoding;

public class Bound {
	Coordinate northeast;
	Coordinate southwest;
	
	public Coordinate getNortheast() {
		return northeast;
	}
	public void setNortheast(Coordinate northeast) {
		this.northeast = northeast;
	}
	public Coordinate getSouthwest() {
		return southwest;
	}
	public void setSouthwest(Coordinate southwest) {
		this.southwest = southwest;
	}
}
