package com.example.lifehelp_main.contents;

public class Entity {

	private String name;
	private String phoneNum;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Entity(String name, String phoneNum) {
		super();
		this.name = name;
		this.phoneNum = phoneNum;
	}
	@Override
	public String toString() {
		return "Entity [name=" + name + ", phoneNum=" + phoneNum + "]";
	}
	
	
}
