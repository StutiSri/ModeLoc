package com.example.services;

import java.util.ArrayList;

import com.example.automode.GPSTracker;
import com.example.dbpackage.Zones;
import com.example.others.Constants;
import com.example.others.ModelZones;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AutoModeService extends Service implements Constants
{

	private GPSTracker gps;
	private double currentLatitude;
	private double currentLongitude;
	private AudioManager mAudioManager;

	// Method to start the service...
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		Toast.makeText(getApplicationContext(),"Service is Running",Toast.LENGTH_SHORT).show();
		
		// Thread Running after every Five minutes...We need to switch GPS off in this thread (Yet to be verified)..
	
		Thread timer= new Thread()
		{
			public void run()
			{
				try
				{
					sleep(1000*60);
				}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			finally
		{	
					Log.e("Check","service is executing");
					doAfterEveryFiveMin();
					gps.stopUsingGPS();
					
			}
		}	
	};
	timer.start();
	    return 1;
		
		
	}

	@Override
	public void onCreate() 
	{
		 Toast.makeText(this, "The new Service was Created", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDestroy()
	{
		
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	
		 

	}

	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		 throw new UnsupportedOperationException("Not yet implemented");
	}
	
	// This Method is called inside the Timer Thread...
	
	/*
	 * Getting Current location and calculating the distance with the locations saved in the database.
	 * if distance is less than 200 meters, then the Ringing Mode is set to the saved ringing mode at this position.
	 */
	public void doAfterEveryFiveMin()
	{
		Log.e("Check","Entered doAfterEveryFiveMin()");
		
		Zones zones=new Zones(AutoModeService.this);
		gps = new GPSTracker(AutoModeService.this);
		
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
			
			double distanceLimit = 10.00;
			
			Log.e("Check", ""+distance);
			
			if(distance < distanceLimit)
				
			{
				if(zone.mode == "SILENT")
					setToSilent();
				else if(zone.mode == "VIBRATION")
					setToVibration();
				else
					setToNormal();
					
				}
				
			}
			
		//	setToNormal();
		}
		
	
	
	protected void setToSilent()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}
	
	
	protected void setToVibration()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}
	
	protected void setToNormal()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
	
	protected void setToDefault()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL); 
	}
	
	

}
