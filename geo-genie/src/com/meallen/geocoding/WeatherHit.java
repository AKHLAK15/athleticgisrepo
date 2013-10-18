package com.meallen.geocoding;

import java.util.List;

public class WeatherHit {
	private Double message;
	private String cod;
	private String calctime;
	private Long cnt;
	private List<Weather> list;
	
	public Double getMessage() {
		return message;
	}
	public void setMessage(Double message) {
		this.message = message;
	}
	public String getCod() {
		return cod;
	}
	public void setCod(String cod) {
		this.cod = cod;
	}
	public String getCalctime() {
		return calctime;
	}
	public void setCalctime(String calctime) {
		this.calctime = calctime;
	}
	public Long getCnt() {
		return cnt;
	}
	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	public List<Weather> getList() {
		return list;
	}
	public void setList(List<Weather> list) {
		this.list = list;
	}
}
