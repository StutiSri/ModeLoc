package com.example.dbpackage;

import java.util.ArrayList;

import com.example.automode.GPSTracker;
import com.example.others.Constants;
import com.example.others.ModelZones;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AutoMode  implements Constants
{
	Context mContext;

	AutoModeDatabase database;
	private Cursor result;
	
	private Intent objIntent;
    
	private GPSTracker gps;
	private double currentLatitude;
	private double currentLongitude;
	
	String[] modes = new String[]{"SILENT","VIBRATION","NORMAL"};

	public AutoMode(Context context){
		mContext = context;
		database = new AutoModeDatabase(mContext);
	}


	public void addLocation(double latitude, double longitude, String name,String mode,int radius) 
	{
		try 
		{
			Log.e("Check", "Database is not open yet");
			database.openDatabase();
			Log.e("check", "database is open");

			String query = "INSERT INTO " + TABLE_ZONES + "(" + KEY_LATITUDE
					+ "," + KEY_LONGITUDE + "," + KEY_NAME + "," + KEY_MODE
					+ "," + KEY_RADIUS + "," + KEY_ISENABLED + ") VALUES('" + latitude + "','" + longitude + "','"
					+ name + "','" + mode + "','" + radius  + "','" + "true"  + "')";
			database.executeSQLQuery(query);

			Toast.makeText(mContext, "Location added.",
					Toast.LENGTH_SHORT).show();
			database.closeDatabase();
		}
		catch (Exception e)
		{
			Log.e("<<Database error>>", "Error: " + e);
		}
	}
	
	public void getCurrentPosition (View v)
	
	{
		gps = new GPSTracker(mContext);
		 
        // check if GPS enabled     
        if(gps.canGetLocation()){
             
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
             
            // \n is for new line
            Toast.makeText(mContext, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
         }
	}
	/*
	 * return true if this region is already in our database
	 */
	public boolean checkPosition(){
		
		Zones zones=new Zones(mContext);
		gps = new GPSTracker(mContext);
		
		Location originLocation = new Location("gps");
		Location destinationLocation = new Location("gps");
		
		currentLatitude = gps.getLatitude();
		currentLongitude = gps.getLongitude();
		
		originLocation.setLatitude(currentLatitude);
		originLocation.setLongitude(currentLongitude);
		
		Log.e("Check",""+currentLatitude+""+currentLongitude);
		
		ArrayList<ModelZones> zoneList = zones.getZoneList();
		
		Log.e("Check",""+zoneList.toString());
		
		for(ModelZones zone: zoneList)
			
		{
			Log.e("Check",""+zone.latitude);
			Log.e("Check",""+zone.longitude);
			
			destinationLocation.setLatitude(zone.latitude);
			destinationLocation.setLongitude(zone.longitude);
			
			float distance = originLocation.distanceTo(destinationLocation);
			
			Log.e("Check", ""+distance);
			
			if(distance < zone.radius){
				return true;
			}
				
			}
		return  false;
		}

}
