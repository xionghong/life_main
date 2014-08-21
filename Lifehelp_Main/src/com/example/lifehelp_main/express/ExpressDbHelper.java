package com.example.lifehelp_main.express;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExpressDbHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "express.db";
	public static final int VERSION = 1;
	public static final String TABLE_COMPANY_NAME = "company";
	public static final String TABLE_COMPANY_ID = "_id";
	/** 快递公司名字 **/
	public static final String TABLE_COMPANY_COMPANY_NAME = "company_name";
	/** 快递公司对应code **/
	public static final String TABLE_COMPANY_COMPANY_CODE = "company_code";
	/** 公司名字对应的首字母 **/
	public static final String TABLE_COMPANY_COMPANY_INITIAL = "initial";
	/** 常用 **/
	public static final String TABLE_COMPANY_COMMON = "common";

	public ExpressDbHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + TABLE_COMPANY_NAME + "("
				+ TABLE_COMPANY_ID + " integer primary key autoincrement,"
				+ TABLE_COMPANY_COMPANY_NAME + " text,"
				+ TABLE_COMPANY_COMPANY_CODE + " text,"
				+ TABLE_COMPANY_COMPANY_INITIAL + " text,"
				+ TABLE_COMPANY_COMMON + " integer not null default 0)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public Cursor getCursor(String string) {
		SQLiteDatabase db = getReadableDatabase();
		String selection = TABLE_COMPANY_COMPANY_NAME + " like ? or "
				+ TABLE_COMPANY_COMPANY_CODE + " like ?";
		String[] selectionArgs = { "%" + string + "%", "%" + string + "%" };
		Cursor cursor = db.query(TABLE_COMPANY_NAME, null, selection,
				selectionArgs, null, null, null);
		return cursor;
	}

	// shentong ems
	// zhongtong ems

	public void updateFavs(List<String> tagSrcFavs, List<String> tagFinallyFavs) {
		SQLiteDatabase db = getWritableDatabase();
		for (String code : tagSrcFavs) {
			if (!tagFinallyFavs.contains(code)) {
				ContentValues values = new ContentValues();
				values.put(TABLE_COMPANY_COMMON, 0);
				String whereClause = TABLE_COMPANY_COMPANY_CODE + " = ?";
				String[] whereArgs = { code };
				db.update(TABLE_COMPANY_NAME, values, whereClause, whereArgs);
			}
		}

		for (String code : tagFinallyFavs) {
			if (!tagSrcFavs.contains(code)) {
				ContentValues values = new ContentValues();
				values.put(TABLE_COMPANY_COMMON, 1);
				String whereClause = TABLE_COMPANY_COMPANY_CODE + " = ?";
				String[] whereArgs = { code };
				db.update(TABLE_COMPANY_NAME, values, whereClause, whereArgs);
			}
		}
	}

}
