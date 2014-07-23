package org.easycomm.model;

import android.graphics.drawable.Drawable;

public class HistoryData {
	
	private String mDate;
	private String mDisplayText;
	
	public HistoryData(String date, String displayText) {
		mDate = date;
		mDisplayText = displayText;
	}
	
	public String getDate() {
		return mDate;
	}
	
	public String getDisplayText() {
		return mDisplayText;
	}
	
	public void setDate(String date) {
		mDate = date;
	}
	
	public void setDisplayText(String displayText) {
		mDisplayText = displayText;
	}

}
