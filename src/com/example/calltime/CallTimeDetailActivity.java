package com.example.calltime;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

public class CallTimeDetailActivity extends ListActivity {
	ArrayList<String> detailname = new ArrayList<String>();
	ArrayList<String> detailnumber = new ArrayList<String>();
	ArrayList<String> detailcalltime = new ArrayList<String>();
	ArrayList<String> detailtime = new ArrayList<String>();

	@SuppressWarnings("static-access")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_calltimedetail);
		Bundle bundle = this.getIntent().getExtras();
		if (null != bundle) {
			detailname = bundle.getStringArrayList("detailname");
			detailnumber = bundle.getStringArrayList("detailnumber");
			detailcalltime = bundle.getStringArrayList("detailcalltime");
			detailtime = bundle.getStringArrayList("detailtime");
			setListAdapter(new CallTimeAdapter(this, detailname, detailnumber,
					detailcalltime, detailtime));
		}
	}
}
