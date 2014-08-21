package com.example.lifehelp_main.weather.entity;

public class WeatherSub {
	private String date;
	private String temHigh;
	private String temLow;
	private String weatherDay;
	private String weatherNight;
	private String fengxiang;
	private String fengli;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTemHigh() {
		return temHigh;
	}
	public void setTemHigh(String temHigh) {
		this.temHigh = temHigh;
	}
	public String getTemLow() {
		return temLow;
	}
	public void setTemLow(String temLow) {
		this.temLow = temLow;
	}
	public String getWeatherDay() {
		return weatherDay;
	}
	public void setWeatherDay(String weatherDay) {
		this.weatherDay = weatherDay;
	}
	public String getWeatherNight() {
		return weatherNight;
	}
	public void setWeatherNight(String weatherNight) {
		this.weatherNight = weatherNight;
	}
	public String getFengxiang() {
		return fengxiang;
	}
	public void setFengxiang(String fengxiang) {
		this.fengxiang = fengxiang;
	}
	public String getFengli() {
		return fengli;
	}
	public void setFengli(String fengli) {
		this.fengli = fengli;
	}
	@Override
	public String toString() {
		return "WeatherSub [date=" + date + ", temHigh=" + temHigh
				+ ", temLow=" + temLow + ", weatherDay=" + weatherDay
				+ ", weatherNight=" + weatherNight + ", fengxiang=" + fengxiang
				+ ", fengli=" + fengli + "]";
	}
	
}
