package com.example.automodechange;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;



public class LocationService extends Service implements Constants
{

	Context context;
	private LocationManager locationManager;
	private boolean isGPSEnabled;
	private double latitude;
	private GPSListener myLocationListener;
	private AudioManager mAudioManager;
	public double longitude;

	public GPSCords gpsCords;
	private String url;
	private double distance;
	private String dis;
	
	
	
	// Author - Vishal
	
	@Override
	public void onCreate() {
		
		Log.d("<<Service>>", "Service Started!");
		super.onCreate();
		context = this.getBaseContext();
	}
		
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Thread timer= new Thread()
		{
			public void run()
			{
				try
				{
					sleep(5000*60);
				}
				catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				finally
				{
					doAfterEveryFiveMin();
				}
			}	
		};
		timer.start();
	    return 1;
	}
	
	public void onStop()
	{
		onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		
		return null;
	}	
	

	
	
	
	
	protected void doAfterEveryFiveMin()
	{
		GPSCords newCords=getGPSCords();
		Zones zones=new Zones(context);
		
		//loading data from sqlite to arraylist
		
		
		
		ArrayList<ModelZones> zoneList = zones.getZoneList();
		
		for(ModelZones zone : zoneList)
		{ 
			
		
			url= "http://maps.googleapis.com/maps/api/directions/json?origin="+newCords.latitude+","+newCords.longitude+"&destination="+zone.latitude+","+zone.longitude+"&sensor=true";
			
	
			
			try 
			{
				dis = new JSONParser().execute(url).get();
			} 
			catch (InterruptedException e) 
			{
						e.printStackTrace();
			} 
			catch (ExecutionException e) 
			{
				
				e.printStackTrace();
			}
			
			distance = Integer.valueOf(dis);
			
//			if(distance<20)
//			{
//				switch(zone.mode)
//				{
//				case SILENT:
//					setToSilent();
//					break;
//				case VIBRATION:
//					setToVibration();
//					break;
//				case NORMAL:
//					setToNormal();
//					break;
//				}
//				return;
//			}
		}
		setToNormal();
	}
	
	/*
	 * @author: Sumit
	 */
	protected GPSCords getGPSCords()
	{
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		// getting GPS status
		isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (isGPSEnabled) 
		{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0, myLocationListener);
			Log.d("GPS Enabled", "GPS Enabled");
		} 
		else
		{
			Toast.makeText(getApplicationContext(), "Gps not enabled", Toast.LENGTH_SHORT).show();
		}

		return gpsCords;
	}
	private class GPSListener implements LocationListener 
	{
		public void onLocationChanged(Location location) 
		{
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			
			gpsCords= new GPSCords();
			gpsCords.latitude=latitude;
			gpsCords.longitude=longitude;
		}
		
	

		public void onProviderDisabled(String provider) 
		{
			
		}

		public void onProviderEnabled(String provider) 
		{
			
		}

		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
			
		}
	}
	
	/*
	 * @author: Sumit
	 */
	protected void setToSilent()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
	}
	
	/*
	 * @author: Sumit
	 */
	protected void setToVibration()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
	}
	/*
	 * @author: Sumit
	 */
	protected void setToNormal()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
	/*
	 * @author: Sumit
	 */
	protected void setToDegault()
	{
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL); 
	}
}
