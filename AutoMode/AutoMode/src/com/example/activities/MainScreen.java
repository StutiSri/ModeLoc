package com.example.activities;

import com.example.services.AutoModeService;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainScreen extends Activity{
	
	
	public void startService(View view) 
	{
		startService(new Intent(this, AutoModeService.class));

	}

	public void stopService(View view) 
	{
		stopService(new Intent(this, AutoModeService.class));

	}
	

}
