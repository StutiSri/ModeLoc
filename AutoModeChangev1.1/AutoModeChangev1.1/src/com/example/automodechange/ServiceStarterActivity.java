package com.example.automodechange;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ServiceStarterActivity extends Activity
{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_activity);
		
	}
	
	public void startService(View v)
	
	{
		startService(new Intent(this, AutoModeService.class));
		
		
	}
	

	public void stopService(View v)
	
	{
		stopService(new Intent(this, AutoModeService.class));
		
		
	}
	
	public void nextActivity (View v)
	{
		Intent intent = new Intent(ServiceStarterActivity.this, CustomListView.class); 
		startActivity(intent);
	}

}
