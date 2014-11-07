package com.example.automodechange;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AutoMode extends Activity implements Constants
{

	AutoModeDatabase database;
	private Intent objIntent;
    
	private String mode;
	
	private Cursor result;
	private GPSTracker gps;
	private EditText name;
	private Spinner modeSpinner;
	String[] modes = new String[]{"SILENT","VIBRATION","NORMAL"};
	
	
	private String lname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		spinnerPopulate();
		database = new AutoModeDatabase(this.getBaseContext());
	}

	private void spinnerPopulate() 
	{
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item,modes);
				   dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					  modeSpinner.setAdapter(dataAdapter1);
	}

	private void initialize()
	{
	modeSpinner = (Spinner) findViewById(R.id.modeSpinner);
//		longitude = (EditText) findViewById(R.id.longitude);
		name = (EditText) findViewById(R.id.name);
		//mode = (EditText) findViewById(R.id.mode);
		
		}

	protected void startService() {
		objIntent = new Intent(this, LocationService.class);
		startService(objIntent);
	}

	protected void stopService() {
		// objIntent = new Intent(this, LocationService.class);
		stopService(objIntent);
	}

	public void buttonSave(View v) {

		
		//details.latitude = Integer.valueOf(latitude.getText().toString());
	//	details.longitude = Integer.valueOf(longitude.getText().toString());
        mode = modeSpinner.getSelectedItem().toString();
		lname = name.getText().toString();

		//addLocation(latitude, longitude, lname,mode);

		Toast.makeText(getApplicationContext(), "Values Entered",
				Toast.LENGTH_SHORT).show();

	}

	public void buttonShow(View v)

	{

		Log.e("check", "clicked");
		try {
			database.openDatabase();
			String query = "SELECT * from " + TABLE_ZONES;
			result = database.executeRawQuery(query);

			/*
			 * get indexes of every column
			 */
			int lati = result.getColumnIndexOrThrow(KEY_LATITUDE);
			int longi = result.getColumnIndexOrThrow(KEY_LONGITUDE);
			int name = result.getColumnIndexOrThrow(KEY_NAME);
			int mode = result.getColumnIndexOrThrow(KEY_MODE);
			result.moveToLast();

			/*
			 * get string using those indexes and use them as you wish
			 */
			String databaseLatitude = result.getString(lati);
			String databaseLongitude = result.getString(longi);
			String databaseName = result.getString(name);
			String databaseMode = result.getString(mode);
			
			Log.e("check", databaseName);
			Log.e("check", databaseMode);
			Log.e("check", databaseLatitude);
			Log.e("check", databaseLongitude);
			
			database.closeDatabase();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	protected void addLocation(double latitude, double longitude, String name,String mode) 
	{
		try 
		{
			Log.e("Check", "Database is not open yet");
			database.openDatabase();
			Log.e("check", "database is open");

			String query = "INSERT INTO " + TABLE_ZONES + "(" + KEY_LATITUDE
					+ "," + KEY_LONGITUDE + "," + KEY_NAME + "," + KEY_MODE
					+ ") VALUES('" + latitude + "','" + longitude + "','"
					+ name + "','" + mode + "')";
			database.executeSQLQuery(query);

			Toast.makeText(this.getBaseContext(), "Location added.",
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
		gps = new GPSTracker(AutoMode.this);
		 
        // check if GPS enabled     
        if(gps.canGetLocation()){
             
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
             
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
         }
	}

	public void startService(View view) 
	{
		startService(new Intent(this, AutoModeService.class));

	}

	public void stopService(View view) 
	{
		stopService(new Intent(this, AutoModeService.class));

	}

	

}
