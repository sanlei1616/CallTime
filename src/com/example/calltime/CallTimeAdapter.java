package com.example.calltime;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CallTimeAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	ArrayList<String> detailname = new ArrayList<String>();
	ArrayList<String> detailnumber = new ArrayList<String>();
	ArrayList<String> detailcalltime = new ArrayList<String>();
	ArrayList<String> detailtime = new ArrayList<String>();

	public CallTimeAdapter(Context context, ArrayList<String> name,ArrayList<String> number,
			ArrayList<String> calltime,ArrayList<String> time) {
		mInflater = LayoutInflater.from(context);
		detailname = name;
		detailnumber = number;
		detailcalltime = calltime;
		detailtime = time;
	}

	@Override
	public int getCount() {
		return detailname.size();
	}

	@Override
	public Object getItem(int position) {
		return detailname.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_layout, null);
			holder = new ViewHolder();
			holder.nameText = (TextView) convertView
					.findViewById(R.id.nameText);
			holder.numberText = (TextView) convertView
					.findViewById(R.id.numberText);
			holder.callTimeText = (TextView) convertView
					.findViewById(R.id.callTimeText);
			holder.timeText = (TextView) convertView
					.findViewById(R.id.timeText);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
        if((null!=detailname)&&(null!=detailnumber)&&(null!=detailcalltime)&&(null!=detailtime)){
			holder.nameText.setText(detailname.get(position));
			holder.numberText.setText(detailnumber.get(position));
			holder.callTimeText.setText(detailcalltime.get(position));
			holder.timeText.setText(detailtime.get(position));
        }

		return convertView;
	}

	private class ViewHolder {
		TextView nameText;
		TextView numberText;
		TextView callTimeText;
		TextView timeText;
	}
}
