package org.easycomm.model;

public class HistoryData {
	
	private String mDate;
	private String mTime;
	private String mDisplayText;
	
	public HistoryData(String date, String time, String displayText) {
		mDate = date;
		mTime = time;
		mDisplayText = displayText;
	}
	
	public String getDate() {
		return mDate;
	}
	
	public String getTime() {
		return mTime;
	}
	
	public String getDisplayText() {
		return mDisplayText;
	}
	
	public void setDate(String date) {
		mDate = date;
	}
	
	public void setTime(String time) {
		mTime = time;
	}
	
	public void setDisplayText(String displayText) {
		mDisplayText = displayText;
	}

}
