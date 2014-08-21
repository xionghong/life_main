package com.example.lifehelp_main.notebook.db;

import java.util.ArrayList;
import java.util.List;

import com.example.lifehelp_main.notebook.entity.NoteEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class NoteDBHelper extends SQLiteOpenHelper{
	public static final String DB_NAME = "note.db";
	public static final int VERSION = 1;

	public static final String TABLE_NAME = "note";
	public static final String _ID = "_id";
	public static final String TIME = "time";
	public static final String CONTENT = "content";

	public NoteDBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
				_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				TIME + " TEXT NOT NULL," +
				CONTENT + " TEXT NOT NULL)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void insert(String content){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CONTENT, content);
		values.put(TIME, System.currentTimeMillis());
		db.insert(TABLE_NAME, null, values );
		System.out.println("插入数据成功");
	}
	public Cursor getCursor(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}

	public void update(String content,String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NoteDBHelper.CONTENT, content);
		String whereClause = NoteDBHelper.TIME+"=?";
		String[] whereArgs = new String[]{time};
		db.update(TABLE_NAME, values , whereClause, whereArgs);
	}

	public void deleteMore(List<Integer> deleteIds) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (Integer integer : deleteIds) {
			String whereClause = _ID + "=?";
			String[] whereArgs = new String[]{integer+""};
			db.delete(TABLE_NAME, whereClause, whereArgs);
		}
		db.close();
	}

	public void deleteNoteById(int noteId) {
		SQLiteDatabase db = this.getWritableDatabase();
		String whereClause = _ID + "=?";
		String[] whereArgs = new String[]{noteId+""};
		db.delete(TABLE_NAME, whereClause, whereArgs);
		db.close();
	}

	public void updateNoteById(int noteId,String contentNew) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CONTENT, contentNew);
		String whereClause = _ID + "=?";
		String[] whereArgs = new String[]{noteId+""};
		db.update(TABLE_NAME, values , whereClause, whereArgs);
		db.close();
	}

	
	/**
	 * 从数据库获取数据，填充List<NoteEntity>
	 * @return
	 */
	public List<NoteEntity> getNotes(){
		List<NoteEntity> notes = new ArrayList<NoteEntity>();
		Cursor cursor = this.getCursor();
		while(cursor.moveToNext()){
			NoteEntity note = new NoteEntity();
			int id = cursor.getInt(cursor.getColumnIndex(NoteDBHelper._ID));
			String content = cursor.getString(cursor.getColumnIndex(NoteDBHelper.CONTENT));
			String time = cursor.getString(cursor.getColumnIndex(NoteDBHelper.TIME));
			note.setId(id);
			note.setTime(time);
			note.setTitle(content);
			notes.add(0, note);
		}
		return notes;
	}
}
