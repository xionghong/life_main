package com.example.lifehelp_main.notebook.entity;

import java.io.Serializable;

public class NoteEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String time;
	private String title;
	private boolean isChecked;
	public NoteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "NoteEntity [time=" + time + ", title=" + title + "]";
	}
	
}
