package com.example.automodechange.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.automodechange.CustomListView;
import com.example.automodechange.ListModel;
import com.example.automodechange.R;

public class CustomAdapter extends BaseAdapter implements OnClickListener {

	@Override
	public void onClick(DialogInterface dialog, int which) {

	}

	private Activity activity;
	private ArrayList data;
	private static LayoutInflater inflater = null;
	public Resources res;
	ListModel tempValues = null;
	int i = 0;

	/******* CustomAdapter Constructor **********/

	public CustomAdapter(Activity a, ArrayList d, Resources resLocal) {
		/****** Take Passed Value *******/

		activity = a;
		data = d;
		res = resLocal;

		/******** Layout Inflator *********/

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {

		if (data.size() <= 0)
			return 1;
		return data.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public static class ViewHolder {
		public TextView text;

		public TextView mode;

		public TextView radius;

		public ToggleButton togglebutton;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View vi = convertView;
		ViewHolder holder;
		
		

		if (convertView == null) {
			vi = inflater.inflate(R.layout.activity_viewalllocationitem, null);

			holder = new ViewHolder();
			holder.text = (TextView) vi.findViewById(R.id.locationName);
			holder.mode = (TextView) vi.findViewById(R.id.locationMode);
			holder.radius = (TextView) vi.findViewById(R.id.locationRadius);
			holder.togglebutton = (ToggleButton) vi.findViewById(R.id.onORoff);

			vi.setTag(holder);

		} else
			holder = (ViewHolder) vi.getTag();

		if (data.size() <= 0) {
			holder.text.setText("No data");
			holder.mode.setText("no mode");
			holder.radius.setText("no radius");
		} else {

			tempValues = null;
			tempValues = (ListModel) data.get(position);

			holder.text.setText(tempValues.getName());
			holder.mode.setText(tempValues.getMode());
			holder.radius.setText(tempValues.getRadius());
			
			

			vi.setOnClickListener(new OnItemClickListener(position));
		}

		return vi;
	}

	private class OnItemClickListener implements android.view.View.OnClickListener 
	{
		private int mPosition;
		private int flag = 0;

		public OnItemClickListener(int position) {

			mPosition = position;
		}

		@Override
		public void onClick(View v) {

			CustomListView sct = (CustomListView) activity;

			/****
			 * Call onItemClick Method inside CustomListViewAndroidExample Class
			 * ( See Below )
			 ****/
//
//			if (flag == 0)
//
//			{
//				v.setBackgroundColor(Color.parseColor("#f98a0a"));
//				flag = 1;
//			}
//
//			else {
//
//				v.setBackgroundColor(Color.parseColor("#ffffff"));
//				flag = 0;
//			}
			sct.onItemClick(mPosition);

		}

	}

}
