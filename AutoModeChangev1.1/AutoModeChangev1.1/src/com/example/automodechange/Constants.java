package com.example.automodechange;

public interface Constants 
{
	public final int SILENT=1;
	public final int VIBRATION=2;
	public final int NORMAL=3;
	public final int DEFAULT=NORMAL;
	
	public static final String KEY_ID = "_ID";
	public static final String KEY_LATITUDE = "Latitude";
	public static final String KEY_LONGITUDE = "Longitude";
	public static final String KEY_NAME = "Name";
	public static final String KEY_MODE = "Mode";
	public static final String KEY_RADIUS = "Radius";
	
	public static final String DATABASE_NAME = "AutomodeDatabase.db";
	public static final String TABLE_ZONES = "ModeZones";
	public static final int DATABASE_VERSION = 1;
}
