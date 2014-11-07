package com.example.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapters.ZonesAdapter;
import com.example.automode.R;
import com.example.dbpackage.AutoModeDatabase;
import com.example.dbpackage.Zones;
import com.example.others.Constants;
import com.example.others.ModelZones;
import com.example.others.RowItems;

public class ZonesActivity extends Activity implements Constants {

	String locNames;
	String modes;
	boolean isEnabled;

	ArrayList<Integer> mPositions = new ArrayList<Integer>();

	AutoModeDatabase database;

	ZonesAdapter mAdapter;
	ArrayList<ModelZones> zones;
	ArrayList<RowItems> zonesList = new ArrayList<RowItems>();

	private ListView listView;
	private Intent objIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		database = new AutoModeDatabase(this.getBaseContext());

		/*
		 * Set Footer View for the list
		 */
		/*
		 * .setFooterDividersEnabled(true); LayoutInflater mInflater =
		 * (LayoutInflater) getApplicationContext()
		 * .getSystemService(Context.LAYOUT_INFLATER_SERVICE); View footerView =
		 * mInflater.inflate(R.layout.footer_view, null);
		 * listView.addFooterView(footerView);
		 * 
		 * footerView.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub AutoMode autoModeObj = new AutoMode(ZonesActivity.this
		 * .getBaseContext()); if (autoModeObj.checkPosition()) {
		 * Toast.makeText(getApplicationContext(),
		 * "This location is already saved with us.", Toast.LENGTH_LONG).show();
		 * return; } Intent i = new Intent(ZonesActivity.this,
		 * GetDataActivity.class); startActivity(i); } });
		 */

		getValuesFromDatabase(); // Populate arrayList zonesList

		setContentView(R.layout.custom_list);
		listView = (ListView) findViewById(R.id.lVZones);
		Resources res = getResources();
		mAdapter = new ZonesAdapter(this, zonesList, res);
		listView.setAdapter(mAdapter);
		//listView.postInvalidate();
	}

	/*
	 * get the values from Zones class that will populate the arraylist
	 * zonesList
	 */
	private void getValuesFromDatabase() {
		// TODO Auto-generated method stub
		Log.e("check", "clicked");

		/*
		 * RowItems rowItem = new RowItems("1","Mode",true);
		 * zonesList.add(rowItem);
		 * 
		 * rowItem = new RowItems("2","Mode",true); zonesList.add(rowItem);
		 * 
		 * rowItem = new RowItems("3","Mode",false); zonesList.add(rowItem);
		 * 
		 * rowItem = new RowItems("4","Mode",false); zonesList.add(rowItem);
		 * 
		 * rowItem = new RowItems("5","Mode",true); zonesList.add(rowItem);
		 * 
		 * rowItem = new RowItems("6","Mode",true); zonesList.add(rowItem);
		 * 
		 * rowItem = new RowItems("7","Mode",true); zonesList.add(rowItem);
		 */

		zones = new Zones(this).getZoneList();

		RowItems rowItem = null;

		if (zones != null) {
			for (ModelZones zone : zones){
				rowItem = new RowItems(zone.name, zone.mode,
						(zone.isEnabled).equalsIgnoreCase("true"));
				Log.e("tag3","item");
				zonesList.add(rowItem);
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		zonesList.clear();
		getValuesFromDatabase();
		mAdapter.changeDataSet(zonesList);
		//Resources res = getResources();
		//mAdapter = new ZonesAdapter(this, zonesList, res);
		//listView.setAdapter(mAdapter);
		//listView.postInvalidate();
		//mAdapter.notifyDataSetChanged();
	
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public void onItemClick(int pos) {

		mPositions.add(pos);
		if (mPositions.size() > 1)
			findViewById(R.id.edit).setVisibility(View.GONE);
		else
			findViewById(R.id.edit).setVisibility(View.VISIBLE);
	}

	public void edit(View v) {
		Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT)
				.show();
	}

	public void delete(View v) {
		Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT)
				.show();
		int pos;
		int n = mPositions.size();
		if (n > 0) {
			AutoModeDatabase db = new AutoModeDatabase(this.getBaseContext());
			try {
				RowItems zone;
				db.openDatabase();
				for (int i = n - 1; i >= 0; i--) {
					pos = mPositions.get(i);
					Log.e("tag", "" + pos);
					zone = zonesList.get(pos);
					String locName = zone.getLocName();
					
					String query = "DELETE FROM " + TABLE_ZONES + " WHERE "
							+ KEY_NAME + " = \'" + locName + "\'";

					Toast.makeText(getBaseContext(), ""+query,
							Toast.LENGTH_SHORT).show();
					
					db.executeSQLQuery(query);
					
					Toast.makeText(getBaseContext(), "Location deleted.",
							Toast.LENGTH_SHORT).show();
				}
				db.closeDatabase();
			} catch (Exception e) {
				Toast.makeText(getBaseContext(), "Error. ",
						Toast.LENGTH_SHORT).show();
				Log.e("<<Database error>>", "Error: " + e);
			}

			zonesList.clear();
			getValuesFromDatabase();
			mAdapter.changeDataSet(zonesList);
			//Resources res = getResources();
			//mAdapter = new ZonesAdapter(this, zonesList, res);
			//listView.setAdapter(mAdapter);
			//listView.postInvalidate();
			//mAdapter.notifyDataSetChanged();
			findViewById(R.id.edit).setVisibility(View.VISIBLE);
			mPositions = new ArrayList<Integer>();
			
		}
	}

	public void addMore(View v) {
		Intent i = new Intent(this, GetDataActivity.class);
		startActivity(i);
	}

}
