package com.meallen.geocoding;

import java.util.List;

public class Result {
	List<AddressComponent> address_components;
	String formatted_address;
	Geometry geometry;
	List<String> types;
	
	public List<AddressComponent> getAddress_components() {
		return address_components;
	}
	public void setAddress_components(List<AddressComponent> address_components) {
		this.address_components = address_components;
	}
	public Geometry getGeometry() {
		return geometry;
	}
	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
}
