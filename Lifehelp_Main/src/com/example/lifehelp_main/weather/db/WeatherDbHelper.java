package com.example.lifehelp_main.weather.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WeatherDbHelper extends SQLiteOpenHelper {
	public static final int VERSION = 2;
	public static final String DB_NAME = "weathercity.db";
	public static final String TABLE_MY_CITY_NAME = "my_city";
	public static final String TABLE_MY_CITY_ID = "_id";
	public static final String TABLE_MY_CITY_CITY = "city";
	public static final String TABLE_MY_CITY_CODE = "city_code";
	public static final String TABLE_MY_CITY_LAST_UPDATE = "last_update";
	public static final String TABLE_MY_CITY_LAST_WEATHER_STR = "weather_str";

	public static final String TABLE_AREA_NAME = "area_table";
	public static final String TABLE_AREA_AREA_NAME = "areaName";
	public static final String TABLE_AREA_PY_AREA_NAME = "pycityName";
	public static final String TABLE_AREA_PY_SHORT = "pyShort";
	public static final String TABLE_AREA_WEATHER_CODE = "weatherCode";

	public WeatherDbHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("DB", "onCreate---");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("DB", "onUpgrade---");

	}

	/** 判断my_city表是否有数据 **/
	public boolean existCity() {
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query(WeatherDbHelper.TABLE_MY_CITY_NAME, null,
				null, null, null, null, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count != 0;
	}

	/** 添加一个城市 **/
	public void addCity(String city) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TABLE_MY_CITY_CITY, city);
		db.insert(TABLE_MY_CITY_NAME, null, values);
		db.close();
	}

	/** 根据城市名查询到对应的天气编码 **/
	public String getCityCode(String city) {
		SQLiteDatabase db = getReadableDatabase();
		String selection = TABLE_AREA_AREA_NAME + "=?";
		String[] selectionArgs = { city };
		Cursor cursor = db.query(TABLE_AREA_NAME, null, selection,
				selectionArgs, null, null, null);
		String cityCode = null;
		if (cursor.moveToNext()) {
			cityCode = cursor.getString(cursor
					.getColumnIndex(TABLE_AREA_WEATHER_CODE));
		}
		return cityCode;
	}

	/** 获取到所有添加的城市 **/
	public List<String> getCitys() {
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = { TABLE_MY_CITY_CITY };
		Cursor cursor = db.query(TABLE_MY_CITY_NAME, columns, null, null, null,
				null, null);
		List<String> citys = new ArrayList<String>();
		while (cursor.moveToNext()) {
			citys.add(cursor.getString(cursor
					.getColumnIndex(TABLE_MY_CITY_CITY)));
		}
		return citys;
	}

	/** 判断该城市有没有被添加过 **/
	public boolean existCity(String city) {
		SQLiteDatabase db = getReadableDatabase();
		String selection = TABLE_MY_CITY_CITY + "=?";
		String[] selectionArgs = { city };
		Cursor cursor = db.query(TABLE_MY_CITY_NAME, null, selection,
				selectionArgs, null, null, null);
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count != 0;
	}
}
