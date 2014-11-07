package com.example.activities;

import com.example.automode.R;
import com.example.dbpackage.AutoMode;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.others.Constants;


public class GetDataActivity extends Activity implements Constants {

	private EditText eTLocName;
	private Button bSaveMode;
	private RadioGroup rGMode;
	String locName;
	private EditText eTRadius;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		eTLocName = (EditText)findViewById(R.id.eTLocName);
		eTRadius = (EditText)findViewById(R.id.eTradius);
		bSaveMode=(Button)findViewById(R.id.bSaveMode);
		rGMode = (RadioGroup)findViewById(R.id.radioGroup1);
		
		bSaveMode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String locName = eTLocName.getText().toString();
				int radius = Integer.parseInt(eTRadius.getText().toString());
				if(locName.length()==0)
					Toast.makeText(getApplicationContext(), "Please Enter a Location Name First", Toast.LENGTH_LONG).show();
				else
					saveMode(locName,radius);
			}
		});
		
	}

	protected void saveMode(String locName,int radius) {
		// TODO Auto-generated method stub
		String mode=Constants.NORMAL;
		switch(rGMode.getCheckedRadioButtonId()){
			case R.id.rNormal:
				mode=Constants.NORMAL;
				break;
			case R.id.rSilent:
				mode=Constants.SILENT;
				break;
			case R.id.rVibrate:
				mode=Constants.VIBRATION;
				break;		
		}
		Toast.makeText(getApplicationContext(), "Save Modes, Mode = "+mode, Toast.LENGTH_LONG).show();
		
		AutoMode autoMode = new AutoMode(this.getBaseContext());
		autoMode.addLocation(45.5,67.8,locName,mode,2);
		
	}
}
