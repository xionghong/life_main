package com.example.lifehelp_main.weather.entity;

import java.io.Serializable;
import java.util.List;

public class Weather implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String city;
	private String updatetime;
	private String wendu;
	private String fengli;
	private String shidu;
	private String fengxiang;
	private List<WeatherSub> weatherSubs;
	private List<Zhishu> zhishus;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getWendu() {
		return wendu;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}

	public String getFengli() {
		return fengli;
	}

	public void setFengli(String fengli) {
		this.fengli = fengli;
	}

	public String getShidu() {
		return shidu;
	}

	public void setShidu(String shidu) {
		this.shidu = shidu;
	}

	public String getFengxiang() {
		return fengxiang;
	}

	public void setFengxiang(String fengxiang) {
		this.fengxiang = fengxiang;
	}

	public List<WeatherSub> getWeatherSubs() {
		return weatherSubs;
	}

	public void setWeatherSubs(List<WeatherSub> weatherSubs) {
		this.weatherSubs = weatherSubs;
	}

	public List<Zhishu> getZhishus() {
		return zhishus;
	}

	public void setZhishus(List<Zhishu> zhishus) {
		this.zhishus = zhishus;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Weather() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Weather(String city, String updatetime, String wendu, String fengli,
			String shidu, String fengxiang, List<WeatherSub> weatherSubs,
			List<Zhishu> zhishus) {
		super();
		this.city = city;
		this.updatetime = updatetime;
		this.wendu = wendu;
		this.fengli = fengli;
		this.shidu = shidu;
		this.fengxiang = fengxiang;
		this.weatherSubs = weatherSubs;
		this.zhishus = zhishus;
	}

	@Override
	public String toString() {
		return "Weather [city=" + city + ", updatetime=" + updatetime
				+ ", wendu=" + wendu + ", fengli=" + fengli + ", shidu="
				+ shidu + ", fengxiang=" + fengxiang + ", weatherSubs="
				+ weatherSubs + ", zhishus=" + zhishus + "]";
	}

}
