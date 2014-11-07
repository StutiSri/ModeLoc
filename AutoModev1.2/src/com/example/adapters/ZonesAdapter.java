package com.example.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.activities.ZonesActivity;
import com.example.automode.R;
import com.example.others.RowItems;

public class ZonesAdapter extends BaseAdapter implements OnClickListener {

	Activity activity;
	Context mContext;
	ArrayList<RowItems> zoneList;
	Resources resLocal;

	class ViewHolder {
		TextView locName, mode;
		CheckBox enable;
	}

	public ZonesAdapter(Activity activity, ArrayList<RowItems> objects,Resources resource) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		zoneList = objects;
		resLocal=resource;
		mContext=activity;
	}
	
	public void changeDataSet(ArrayList<RowItems> zoneList){
	
		this.zoneList = zoneList;
		notifyDataSetInvalidated();
		//tifyDataSetChanged();
	}

	@Override
	public int getCount() {

		Log.e("tag1",""+zoneList.size() );
		if (zoneList.size() == 0){
			Log.e("tag1","In"+zoneList.size() );
			return 1;
		}
		return zoneList.size();
	}

	@Override
	public RowItems getItem(int position) {

		return zoneList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;

		LayoutInflater inflator = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			
			convertView = inflator.inflate(R.layout.row_item, null);
			holder = new ViewHolder();
			holder.locName = (TextView) convertView
					.findViewById(R.id.rowLocNAme);
			holder.mode = (TextView) convertView.findViewById(R.id.rowMode);
			holder.enable = (CheckBox) convertView.findViewById(R.id.chEnabled);
			convertView.setTag(holder);
			
		} else
			
			holder = (ViewHolder) convertView.getTag();
		
		if (getCount()==1&&zoneList.size()==0) {
			Log.e("tag1","In set" );
			holder.locName.setText("No data");
			holder.mode.setText("no mode");
			holder.enable.setText("no radius");
			
		} else {
			
			RowItems rowItem = getItem(position);
			holder.locName.setText(rowItem.getLocName());
			holder.mode.setText(rowItem.getMode());
			holder.enable.setChecked((rowItem.getIsEnabled()));
			convertView.setOnClickListener(new OnItemClickListener(position));
			
		}
		return convertView;

	}
	private class OnItemClickListener implements android.view.View.OnClickListener {
		private int mPosition;
		private int flag = 0;

		public OnItemClickListener(int position) {

			mPosition = position;
		}

		@Override
		public void onClick(View v) {

			ZonesActivity sct = (ZonesActivity) activity;

	/****
	 * Call onItemClick Method inside CustomListViewAndroidExample Class
	 * ( See Below )
	 ****/

			if (flag == 0){
				
				v.setBackgroundColor(Color.parseColor("#f98a0a"));
				flag = 1;
			}

			else {

				v.setBackgroundColor(Color.parseColor("#ffffff"));
				flag = 0;
			}
			 sct.onItemClick(mPosition);

}


}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
