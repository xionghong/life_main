package com.example.lifehelp_main.contents;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContentsNumberDBHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "phonenumber.db";
	public static final int VERSION = 1;
	public static final String TABLE_NAME = "tele";
	public static final String _ID = "id";
	public static final String NAME = "name";
	public static final String TELE = "tele";
	public static final String CATEGORY = "category";

	public ContentsNumberDBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public List<Entity> getList(String category) {
		System.out.println("getList == category" + category);
		SQLiteDatabase db = getReadableDatabase();
		String selection = CATEGORY + " = ?";

		String[] selectionArgs = { category };
		Cursor cursor = db.query("tele", null, selection, selectionArgs, null,
				null, null);
		List<Entity> list = new ArrayList<Entity>();
		while (cursor.moveToNext()) {
			String name = cursor.getString(cursor.getColumnIndex(NAME));
			String tele = cursor.getString(cursor.getColumnIndex(TELE));
			Entity entity = new Entity(name, tele);
			list.add(entity);
		}
		return list;
	}

	public Cursor getCursorNormal() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}

	public Cursor getCursonByName(String name) {
		SQLiteDatabase db = this.getReadableDatabase();
		String selection = NAME + " like ?";
		String[] selectionArgs = new String[] { "%" + name + "%" };
		Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs,
				null, null, null);
		return cursor;
	}
}
