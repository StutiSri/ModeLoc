package com.example.automode;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class JSONParser extends AsyncTask<String, String, String>
{
 
	private HitUrl hit;
	
	private String Distance;

	@Override
	protected String doInBackground(String... urls) {
		
		hit = new HitUrl();
		
		Distance = null;
		try 
        {
            
            
            JSONObject json_data= new JSONObject(""+hit);
            
           	Distance = json_data.getString("distance");
           	
           
           	
        } 
		
        catch (Exception e)
        {
            Log.d("ReadWeatherJSONFeedTask", e.getLocalizedMessage());
        }
		return POST(Distance);    
		
		
	}

	private String POST(String distance2) {
		// TODO Auto-generated method stub
		return distance2;
	}

	

}
