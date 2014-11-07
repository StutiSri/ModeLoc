package com.example.automodechange;

import java.util.ArrayList;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.automodechange.adapters.CustomAdapter;

public class CustomListView extends ActionBarActivity implements Constants {
	AutoModeDatabase database;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.location_list_options, menu);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_delete:

			// deleteSelectedItems();

			return true;
		case R.id.action_edit:

			// composeMessage();

			return true;
		default:
			return super.onOptionsItemSelected(item);

		
			// return super.onOptionsItemSelected(item);
		}
	}

	private void deleteSelectedItems(String name) {
		try {
			Log.e("Check", "Database is not open yet");

			database.openDatabase();
			Log.e("check", "database is open");

			String query = "DELETE FROM " + TABLE_ZONES + " WHERE " + KEY_NAME
					+ " = " + name;
			Log.e("check", query);
			database.executeSQLQuery(query);

			Log.e("check", "Deleted");

			database.closeDatabase();
		} catch (Exception e) {
			Log.e("<<Database error>>", "Error: " + e);
		}

	}

	ListView list;
	CustomAdapter adapter;
	public CustomListView CustomListView = null;
	public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
	private ArrayList<ModelZones> zoneList;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_viewalllocation);

		CustomListView = this;
		database = new AutoModeDatabase(this.getBaseContext());
		setListData();

		Resources res = getResources();

		list = (ListView) findViewById(R.id.listView1);
		button = (Button) findViewById(R.id.addMore);

		adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr,
				res);

		list.setAdapter(adapter);
		//
		//
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		list.setOnItemClickListener(new OnItemClickListener() {

			private int save = -1;

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3)

			{

				parent.getChildAt(position).setBackgroundColor(Color.BLUE);

//				if (save != -1 && save != position) {
//					list.getChildAt(position).setBackgroundColor(Color.BLACK);
//				}

				save = position;

			}
		});

	}

	private void setListData()

	{

		Zones zones = new Zones(CustomListView.this);

		// loading data from sqlite to arraylist

		zoneList = zones.getZoneList();

		for (ModelZones zone : zoneList) {

			final ListModel sched = new ListModel();

			/*** Add data to the model object ****/

			sched.setName(zone.name);
			sched.setMode(zone.mode);
			sched.setRadius(zone.radius + "m.");

			/**** Add object to the arrayList ******/

			CustomListViewValuesArr.add(sched);

		}

	}

	public void onItemClick(int mPosition) {
		ListModel tempValues = (ListModel) CustomListViewValuesArr
				.get(mPosition);

		Toast.makeText(CustomListView,
				"" + tempValues.getName() + "" + tempValues.getMode(),
				Toast.LENGTH_LONG).show();
	}

	public void addMore(View v) {
		Intent intent = new Intent(CustomListView.this,
				AddNewLocationActivity.class);
		startActivity(intent);
	}

}
