package com.example.automodechange;

import java.util.ArrayList;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class Zones implements Constants
{
	
	
	AutoModeDatabase database;
	
	
	Zones(Context context)
	{
		database=new AutoModeDatabase(context);
	}
	

	public ArrayList<ModelZones> getZoneList()
	{
		ArrayList<ModelZones> zoneList=new ArrayList<ModelZones>();
		Cursor result;
		String query;
		try
		{
			database.openDatabase();
			query = "SELECT * FROM " + TABLE_ZONES;
			result = database.executeRawQuery(query);
			if(result.getCount()>0)
			{
				int iLat = result.getColumnIndexOrThrow(KEY_LATITUDE);
				int iLon = result.getColumnIndexOrThrow(KEY_LONGITUDE);
				int iName = result.getColumnIndexOrThrow(KEY_NAME);
				int iMode=result.getColumnIndexOrThrow(KEY_MODE);
				int iRadius = result.getColumnIndexOrThrow(KEY_RADIUS);
				
				do
				{
					if(result.isBeforeFirst())
						result.moveToFirst();
					else
						result.moveToNext();
					
					ModelZones zone=new ModelZones();
					
					zone.latitude=Double.parseDouble(result.getString(iLat));
					zone.longitude=Double.parseDouble(result.getString(iLon));
					zone.name=result.getString(iName);
					zone.mode=result.getString(iMode);
					zone.radius = result.getString(iRadius);
					zoneList.add(zone);
				}
				while(!result.isLast());
			}

			database.closeDatabase();
		}
		catch(Exception e)
		{
			Log.e("<<Database error>>","Error: "+e);
		}
		return zoneList;
	}
}
