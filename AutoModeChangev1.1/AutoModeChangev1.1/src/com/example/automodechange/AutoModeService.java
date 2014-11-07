package com.example.automodechange;

import java.util.ArrayList;

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
	
	private AudioManager mAudioManager ;
	
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
		
		
		
	double currentLatitude = gps.getLatitude();
	double currentLongitude = gps.getLongitude();
		
		Location originLocation = new Location("gps");
		Location destinationLocation = new Location("gps");
		
		originLocation.setLatitude(currentLatitude);
		originLocation.setLongitude(currentLongitude);
		
		Log.e("Check",""+currentLatitude);
		Log.e("Check",""+currentLongitude);
		
		ArrayList<ModelZones> zoneList = zones.getZoneList();
		
		Log.e("Check",""+"ArrayList is ready");
		
		for(ModelZones zone: zoneList)
			
		{
			Log.e("Check",""+zone.latitude);
			Log.e("Check",""+zone.longitude);
			Log.e("Check",""+zone.radius);
			
			destinationLocation.setLatitude(zone.latitude);
			destinationLocation.setLongitude(zone.longitude);
			
			float distance = originLocation.distanceTo(destinationLocation);
			
			
			
			
			if(distance < Integer.valueOf(zone.radius))
				
			{
				if(zone.mode == "Silent Mode")
					setToSilent();
					
				else if(zone.mode == "Vibration Mode")
					setToVibration();
				else
					setToNormal();
				
					
				}
				
			}
			
		
		}
		
	
	
	protected void setToSilent()
	{
		 mAudioManager = (AudioManager)getSystemService(AutoModeService.AUDIO_SERVICE); ;
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}
	
	
	protected void setToVibration()
	{	 
		mAudioManager = (AudioManager)getSystemService(AutoModeService.AUDIO_SERVICE); ;
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}
	
	protected void setToNormal()
	{
		 mAudioManager = (AudioManager)getSystemService(AutoModeService.AUDIO_SERVICE); ;
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
	
	protected void setToDefault()
	{
		 mAudioManager = (AudioManager)getSystemService(AutoModeService.AUDIO_SERVICE); ;
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL); 
	}
	
	

}
