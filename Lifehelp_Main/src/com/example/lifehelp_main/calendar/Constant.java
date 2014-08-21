package com.example.lifehelp_main.calendar;

import android.app.Activity;
import android.util.DisplayMetrics;

public final class Constant {

	
	public static int screenWidth;
	public static int screenHeight;
	public static int itemWidth;
	public static int weekViewHight;
	public static int monthViewHight;
	public static int monthViewItemHight;
	
	public static final void init(Activity a){
		DisplayMetrics dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.heightPixels;
		screenWidth = dm.widthPixels;
		itemWidth = screenWidth/7;
		weekViewHight = 25;
		
		monthViewItemHight = 80;
		monthViewHight = monthViewHight*6;
	}
}
