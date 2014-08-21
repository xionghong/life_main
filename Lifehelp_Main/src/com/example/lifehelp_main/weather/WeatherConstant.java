package com.example.lifehelp_main.weather;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class WeatherConstant {

	public static final String CITY_KEY = "city_key";
	public static final String WEATHER_URL = "http://wthrcdn.etouch.cn/WeatherApi?citykey="
			+ CITY_KEY;
	public static int screenWidth;
	public static int screenHeight;
	public static int heightContainerViewPager;
	public static int heightContainerListView;
	public static int heightContainerBottom;
	public static int heightListView;
	public static int itemHeight;
	public static int heightStatusBar;
	public static int height;

	public static void init(Activity a) {
		// 获取屏幕的宽高
		DisplayMetrics dm = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		heightStatusBar = getStatusBarHeight(a);
		height = screenHeight - heightStatusBar;

		heightContainerBottom = height * 13 / 100;
		heightListView = height / 2;
		itemHeight = heightListView / 5;
		heightContainerViewPager = height - heightContainerBottom
				- heightListView * 3 / 5;
		heightContainerListView = height - heightContainerBottom;
	}

	/** 获取状态栏的高度 **/
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		java.lang.reflect.Field field = null;
		int x = 0;
		int statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
			return statusBarHeight;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusBarHeight;
	}
}
