package com.meallen.geocoding;

import java.util.List;

public class Weather {
	private Long id;
	private String name;
	private WeatherCoordinate coord;
	private Double distance;
	private WeatherMain main;
	private Long dt;
	private Wind wind;
	private Rain rain;
	private Cloud clouds;
	private List<WeatherDetail> weather;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public WeatherCoordinate getCoord() {
		return coord;
	}
	public void setCoord(WeatherCoordinate coord) {
		this.coord = coord;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public WeatherMain getMain() {
		return main;
	}
	public void setMain(WeatherMain main) {
		this.main = main;
	}
	public Long getDt() {
		return dt;
	}
	public void setDt(Long dt) {
		this.dt = dt;
	}
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
	public Rain getRain() {
		return rain;
	}
	public void setRain(Rain rain) {
		this.rain = rain;
	}
	public Cloud getClouds() {
		return clouds;
	}
	public void setClouds(Cloud clouds) {
		this.clouds = clouds;
	}
	public List<WeatherDetail> getWeather() {
		return weather;
	}
	public void setWeather(List<WeatherDetail> weather) {
		this.weather = weather;
	}
}
