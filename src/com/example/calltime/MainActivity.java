package com.example.calltime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;
import android.provider.CallLog;

public class MainActivity extends ActionBarActivity {
	static String TAG = "calltime" ;
	CallTimeDetailFragment callTimeDetailFragment;
	PlaceholderFragment placeholderFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callTimeDetailFragment = new CallTimeDetailFragment();
        placeholderFragment = new PlaceholderFragment();
        if (savedInstanceState == null) {
        	addPlaceholderFragment();
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	private Button mCallTime1,mReceiveTime1,mCallTime2,mReceiveTime2,mDataFlow1,mDataFlow2;
    	private TextView mTextCallTime1,mTextCallTime2,mTextReceiveTime1,mTextReceiveTime2,mTextDataFlow1,mTextDataFlow2;
    	private long mSumCallTime1 = 0;
    	private long mSumCallTime2 = 0;
    	private long mSumReceiveTime1 = 0;
    	private long mSumReceiveTime2 = 0;
    	private long mSumDataFlow1 = 0;
    	private long mSumDataFlow2 = 0;
    	private List<CallTimeItem> mCallTimeDetail1 = new ArrayList<CallTimeItem>();
    	private List<CallTimeItem> mCallTimeDetail2 = new ArrayList<CallTimeItem>();
    	private List<CallTimeItem> mReceiveTimeDetail1 = new ArrayList<CallTimeItem>();
    	private List<CallTimeItem> mReceiveTimeDetail2 = new ArrayList<CallTimeItem>();
    	private List<CallTimeItem> mDataFlowDetail1 = new ArrayList<CallTimeItem>();
    	private List<CallTimeItem> mDataFlowDetail2 = new ArrayList<CallTimeItem>();
    	
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            mCallTime1 = (Button)rootView.findViewById(R.id.sim1_calltime_button);
        	mReceiveTime1 = (Button)rootView.findViewById(R.id.sim1_receivtime_button);
        	mCallTime2 = (Button)rootView.findViewById(R.id.sim2_calltime_button);
        	mReceiveTime2 = (Button)rootView.findViewById(R.id.sim2_receivtime_button);
        	mDataFlow1 = (Button)rootView.findViewById(R.id.sim1_dataflow_button);
        	mDataFlow2 = (Button)rootView.findViewById(R.id.sim2_dataflow_button);
        	mTextCallTime1 = (TextView)rootView.findViewById(R.id.sim1_calltime_content);
        	mTextCallTime2 = (TextView)rootView.findViewById(R.id.sim2_calltime_content);
        	mTextReceiveTime1 = (TextView)rootView.findViewById(R.id.sim1_receivetime_content);
        	mTextReceiveTime2 = (TextView)rootView.findViewById(R.id.sim2_receivetime_content);
        	mTextDataFlow1 = (TextView)rootView.findViewById(R.id.sim1_dataflow_content);
        	mTextDataFlow2 = (TextView)rootView.findViewById(R.id.sim2_dataflow_content);
        	initView();
            return rootView;            
        }
        
        
        @Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			updateView();  
		}

		private void initView(){        	 
        	
        	mCallTime1.setOnClickListener(new Button.OnClickListener(){
    		      @Override
    		      public void onClick(View arg0){
    		    	  ((MainActivity)getActivity()).startCallTimeActivity(mCallTimeDetail1);
    		      }
    		 });
        	mReceiveTime1.setOnClickListener(new Button.OnClickListener(){
    		      @Override
    		      public void onClick(View arg0){
    		    	  ((MainActivity)getActivity()).startCallTimeActivity(mReceiveTimeDetail1);
    		      }
    		 });
        	mCallTime2.setOnClickListener(new Button.OnClickListener(){
    		      @Override
    		      public void onClick(View arg0){
    		    	  ((MainActivity)getActivity()).startCallTimeActivity(mCallTimeDetail2);
    		      }
    		 });
        	mReceiveTime2.setOnClickListener(new Button.OnClickListener(){
    		      @Override
    		      public void onClick(View arg0){
    		    	  ((MainActivity)getActivity()).startCallTimeActivity(mReceiveTimeDetail2);
    		      }
    		 });
        	mDataFlow1.setOnClickListener(new Button.OnClickListener(){
    		      @Override
    		      public void onClick(View arg0){
    		    	  
    		      }
    		 });
        	mDataFlow2.setOnClickListener(new Button.OnClickListener(){
    		      @Override
    		      public void onClick(View arg0){
    		    	  
    		      }
    		 });    		
    	}
        
        private void updateView(){
        	mSumCallTime1 = 0;
        	mSumCallTime2 = 0;
        	mSumReceiveTime1 = 0;
        	mSumReceiveTime2 = 0;
        	mSumDataFlow1 = 0;
        	mSumDataFlow2 = 0;
        	mCallTimeDetail1.clear();
        	mCallTimeDetail2.clear();
        	mReceiveTimeDetail1.clear();
        	mReceiveTimeDetail2.clear();
        	mDataFlowDetail1.clear();
        	mDataFlowDetail2.clear();
        	Date nowDate = Calendar.getInstance().getTime();
	    	String strNumber,strName = "";
	    	int type;
	    	long callTime;
	    	Date date;
	    	String nowTime,time= "";
	    	int slot = 0;
	    	ContentResolver cr = getActivity().getContentResolver();
	    	final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,
	    	new String[]{CallLog.Calls.NUMBER,CallLog.Calls.CACHED_NAME,CallLog.Calls.TYPE, CallLog.Calls.DATE,CallLog.Calls.DURATION,"simid"},
	    	null, null,CallLog.Calls.DEFAULT_SORT_ORDER);        	
	
	    	Log.i(MainActivity.TAG,"time"+time);
	    	for(int i=0; i<cursor.getCount(); i++){
	    		cursor.moveToPosition(i);
	    		type = cursor.getInt(2);//来电:1,拨出:2,未接:3 public static final int INCOMING_TYPE = 1; public static final int OUTGOING_TYPE = 2; public static final int MISSED_TYPE = 3;
	    		strNumber = cursor.getString(0); //呼叫号码
	        	strName = cursor.getString(1); //联系人姓名
	        	callTime = cursor.getLong(4);
	        	SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	        	date = new Date(Long.parseLong(cursor.getString(3)));
	        	time = sfd.format(date);
	        	nowTime = sfd.format(nowDate);
	        	if(!isSameMonth(time,nowTime)){
	        		continue;
	        	}
	        	slot = cursor.getInt(5); //卡槽号 0:单卡手机  1: 双卡手机卡槽1 2: 双卡手机卡槽2
	    		if((2==type)&&(0!=callTime)){
	                long l = callTime/60;
	                l=l+1;
	                CallTimeItem tCallTimeItem = new CallTimeItem(strNumber,strName,l,time);
	                if(1==slot){
	                	mSumCallTime1 = mSumCallTime1 + l;	                	
	                	mCallTimeDetail1.add(tCallTimeItem);
	                }else if(2==slot){
	                	mSumCallTime2 = mSumCallTime2 + l;
	                	mCallTimeDetail2.add(tCallTimeItem);
	                }
	    		}else if((1==type)&&(0!=callTime)){
	    			long l = callTime/60;
	    			l=l+1;
	                CallTimeItem tCallTimeItem = new CallTimeItem(strNumber,strName,l,time);
	                if(1==slot){
	                	mSumReceiveTime1 = mSumReceiveTime1 + l;
	                	mReceiveTimeDetail1.add(tCallTimeItem);
	                }else if(2==slot){
	                	mSumReceiveTime2 = mSumReceiveTime2 + l;
	                	mReceiveTimeDetail2.add(tCallTimeItem);
	                }
	    		}
	    	
	    	}
	    	
	    	
	    	mTextCallTime1.setText(Long.toString(mSumCallTime1));
	    	mTextCallTime2.setText(Long.toString(mSumCallTime2));
	    	mTextReceiveTime1.setText(Long.toString(mSumReceiveTime1));
	    	mTextReceiveTime2.setText(Long.toString(mSumReceiveTime2));
	    	mTextDataFlow1.setText(Long.toString(mSumDataFlow1));
	    	mTextDataFlow2.setText(Long.toString(mSumDataFlow2));
    	}
        
        private boolean isSameMonth(String time, String nowTime){
        	boolean iReval = true;
        	String month = time.substring(0, 7);
        	String nowMonth = nowTime.substring(0, 7);
        	if(month.equals(nowMonth)){
        		iReval = true;
        	}else{
        		iReval = false;
        	}
        	return iReval;
        }
        
        
    }
    
   public void addPlaceholderFragment(){
		
		FragmentTransaction mFragmentTransaction= this.getSupportFragmentManager()
				.beginTransaction();
		mFragmentTransaction.add(R.id.container, placeholderFragment);
		mFragmentTransaction.commit();
	}

    public void removePlaceholderFragment(){		
		FragmentTransaction mFragmentTransaction= this.getSupportFragmentManager()
				.beginTransaction();
		mFragmentTransaction.remove(placeholderFragment);
		mFragmentTransaction.commit();
	}
 
   public void addCallTimeDetailControl(){
		
		FragmentTransaction mFragmentTransaction= this.getSupportFragmentManager()
				.beginTransaction();
		mFragmentTransaction.replace(R.id.container_calltimedetail, callTimeDetailFragment);
		mFragmentTransaction.commit();
		removePlaceholderFragment();
	}

    public void removeCallTimeDetailControl(){		
		FragmentTransaction mFragmentTransaction= this.getSupportFragmentManager()
				.beginTransaction();
		mFragmentTransaction.remove(callTimeDetailFragment);
		mFragmentTransaction.commit();
	}
    
    private void startCallTimeActivity(List<CallTimeItem> callTimeList){
    	ArrayList<String> detailname = new ArrayList<String>();
    	ArrayList<String> detailnumber = new ArrayList<String>();
    	ArrayList<String> detailcalltime = new ArrayList<String>();
    	ArrayList<String> detailtime = new ArrayList<String>();
    	Intent  intent=new Intent();
        intent.setClass(MainActivity.this, CallTimeDetailActivity.class);
        Bundle bundle = new Bundle();
        if(null!=callTimeList){
        	for(int i=0; i<callTimeList.size(); i++){
        		detailname.add(callTimeList.get(i).getmName());
        		detailnumber.add(callTimeList.get(i).getmNumber());
        		detailcalltime.add(Long.toString(callTimeList.get(i).getmCallTime()));
        		detailtime.add(callTimeList.get(i).getmTime());
        	}
	        bundle.putStringArrayList("detailname", detailname);
	        bundle.putStringArrayList("detailnumber", detailnumber);
	        bundle.putStringArrayList("detailcalltime", detailcalltime);
	        bundle.putStringArrayList("detailtime", detailtime);
	        intent.putExtras(bundle);
	        startActivity(intent);
        }
    }

}
