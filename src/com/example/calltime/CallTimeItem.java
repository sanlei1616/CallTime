package com.example.calltime;

class CallTimeItem {
	String mNumber;
	String mName;
	long mCallTime;	
	String mTime;
	public CallTimeItem(String mNumber, String mName, long mCallTime,
			String mTime) {
		super();
		this.mNumber = mNumber;
		this.mName = mName;
		this.mCallTime = mCallTime;
		this.mTime = mTime;
	}
	public String getmNumber() {
		return mNumber;
	}
	public void setmNumber(String mNumber) {
		this.mNumber = mNumber;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public long getmCallTime() {
		return mCallTime;
	}
	public void setmCallTime(long mCallTime) {
		this.mCallTime = mCallTime;
	}
	public String getmTime() {
		return mTime;
	}
	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	
}
