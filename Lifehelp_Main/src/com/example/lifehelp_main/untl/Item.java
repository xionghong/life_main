package com.example.lifehelp_main.untl;

public class Item {
	private String text;
	private int drawableId;
	private int color;
	private Class<?> c;
	private int state;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getDrawableId() {
		return drawableId;
	}
	public void setDrawableId(int drawableId) {
		this.drawableId = drawableId;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public Class<?> getC() {
		return c;
	}
	public void setC(Class<?> c) {
		this.c = c;
	}
	
	public Item(String text, int drawableId, int color, Class<?> c, int state) {
		super();
		this.text = text;
		this.drawableId = drawableId;
		this.color = color;
		this.c = c;
		this.state = state;
	}
	public Item() {
		super();
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
}
