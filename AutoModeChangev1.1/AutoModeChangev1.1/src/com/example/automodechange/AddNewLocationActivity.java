package com.example.automodechange;

import android.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddNewLocationActivity extends ActionBarActivity implements
		Constants

{

	private Context mContext;
	private ImageView ivEditRadius;
	private EditText etRadius;
	AutoModeDatabase database;
	private GPSTracker gps;
	private double latitude;
	private double longitude;
	private EditText etLocationName;

	private RadioButton rbMode;
	private RadioGroup rgModesGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addnew_location);
		mContext = this;

		getCurrentPosition();

		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		rgModesGroup = (RadioGroup) findViewById(R.id.rgModesGroup);

		etLocationName = (EditText) findViewById(R.id.etLocationName);
		etRadius = (EditText) findViewById(R.id.etRadius);
		ivEditRadius = (ImageView) findViewById(R.id.ivEditRadius);
		etRadius.setClickable(false);
		etRadius.setFocusable(false);
		database = new AutoModeDatabase(this.getBaseContext());
		ivEditRadius.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.e("check", "clicked");
				// if(etRadius.isClickable()||)
				etRadius.setClickable(true);
				etRadius.setFocusable(true);
				etRadius.setEnabled(true);
				etRadius.setFocusableInTouchMode(true);
				etRadius.setCursorVisible(false);
				etRadius.clearFocus();

			}
		});

	}

	/*
	 * To add the new Location in the database
	 */

	protected void saveLocationInDatabase(double latitude, double longitude,
			String name, String mode, String radius) {
		try {
			Log.e("Check", "Database is not open yet");

			database.openDatabase();
			Log.e("check", "database is open");

			String query = "INSERT INTO " + TABLE_ZONES + "(" + KEY_LATITUDE
					+ "," + KEY_LONGITUDE + "," + KEY_NAME + "," + KEY_MODE
					+ "," + KEY_RADIUS + ") VALUES('" + latitude + "','"
					+ longitude + "','" + name + "','" + mode + "','" + radius
					+ "')";
			Log.e("check", query);
			database.executeSQLQuery(query);

			Log.e("check", "saved");

			Toast.makeText(this.getBaseContext(), "Location added.",
					Toast.LENGTH_SHORT).show();
			database.closeDatabase();
		} catch (Exception e) {
			Log.e("<<Database error>>", "Error: " + e);
		}
	}

	/*
	 * To get the current location
	 */

	public void getCurrentPosition()

	{
		gps = new GPSTracker(AddNewLocationActivity.this);

		// check if GPS enabled
		if (gps.canGetLocation()) {
			Log.e("Check", "Got Location" + "" + gps.getLocation());

			latitude = gps.getLatitude();
			longitude = gps.getLongitude();

			Log.e("Check", "" + latitude);
			Log.e("Check", "" + longitude);

			// \n is for new line
			Toast.makeText(
					getApplicationContext(),
					"Your Location is - \nLat: " + latitude + "\nLong: "
							+ longitude, Toast.LENGTH_LONG).show();
		} else {
			// can't get location
			// GPS or Network is not enabled
			// Ask user to enable GPS/network in settings
			gps.showSettingsAlert();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_new_location_options, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();

		/*
		 * save in database
		 */
		String name = etLocationName.getText().toString();
		String mode = getMode();
		double lati = latitude;
		double longi = longitude;
		String radius = etRadius.getText().toString();

		saveLocationInDatabase(lati, longi, name, mode, radius);

		return super.onOptionsItemSelected(item);
	}

	private String getMode() {

		int selectedId = rgModesGroup.getCheckedRadioButtonId();

		rbMode = (RadioButton) findViewById(selectedId);

		return rbMode.getText().toString();
	}
}
