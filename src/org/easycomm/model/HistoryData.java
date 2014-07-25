package org.easycomm.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryData {
	
	private Date mDate;
	private String mDisplayText;
	
	public HistoryData(Date date, String displayText) {
		mDate = date;
		mDisplayText = displayText;
	}
	
	public String getDateString() {
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		return sdf_date.format(mDate);
	}
	
	public String getTimeString() {
		SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm", Locale.US);		
		return sdf_time.format(mDate);
	}
	
	public String getDisplayText() {
		return mDisplayText;
	}
	
	public void setDate(Date date) {
		mDate = date;
	}
	
	public void setDisplayText(String displayText) {
		mDisplayText = displayText;
	}

}
