package com.example.automodechange;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class AutoModeDatabase implements Constants
{
	private final Context appContext;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase sqliteDatabase;

	private class DatabaseHelper extends SQLiteOpenHelper 
	{

		public DatabaseHelper(Context context) 
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{
			db.execSQL("CREATE TABLE " + TABLE_ZONES + " (" 
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ KEY_LATITUDE + " TEXT NOT NULL, " 
	                + KEY_LONGITUDE + " TEXT NOT NULL, " 
					+ KEY_NAME + " TEXT NOT NULL, " 
	                + KEY_MODE +  " TEXT NOT NULL, " 
					+ KEY_RADIUS + " TEXT NOT NULL" + ");");
			
			Log.e("check","creating database");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
		{
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_ZONES);
			onCreate(db);
		}
	}
	
	/*
	 * Pass the base context to this constructor.
	 */
	AutoModeDatabase(Context context) 
	{
		appContext = context;
	}

	/*
	 * Function opens a database with writable permission.
	 */
	public AutoModeDatabase openDatabase() throws Exception 
	{
		
		dbHelper = new DatabaseHelper(appContext);
		
		sqliteDatabase = dbHelper.getWritableDatabase();
	
		
		return this;
		
	}

	/*
	 * Function closes the opened database.
	 */
	public void closeDatabase() throws Exception 
	{
		dbHelper.close();
	}

	/*
	 * Use this function to execute queries that return some data. Such as
	 * SELECT.
	 */
	public Cursor executeRawQuery(String query) throws Exception 
	{
		return sqliteDatabase.rawQuery(query, null);
	}

	/*
	 * Use this function to execute queries that do not return any data. Such as
	 * INSERT, UPDATE, DELETE etc.
	 */
	public void executeSQLQuery(String query) 
	{
		
		sqliteDatabase.execSQL(query + ";");
		
		Log.e("check", "executeQueryRUN");
	}
}
