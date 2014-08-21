package com.example.lifehelp_main.express;

public class Company {

	private String name;
	private String code;
	private int favState;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getFavState() {
		return favState;
	}

	public void setFavState(int favState) {
		this.favState = favState;
	}

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(String name, String code, int favState) {
		super();
		this.name = name;
		this.code = code;
		this.favState = favState;
	}

}
