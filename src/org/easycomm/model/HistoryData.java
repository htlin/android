package org.easycomm.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HistoryData {
	
	private Calendar mDate;
	private String mDisplayText;
	
	public HistoryData(Calendar date, String displayText) {
		mDate = date;
		mDisplayText = displayText;
	}
	
	public String getDateString() {
		SimpleDateFormat sdf_date = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		return sdf_date.format(mDate.getTime());
	}
	
	public String getTimeString() {
		SimpleDateFormat sdf_time = new SimpleDateFormat("hh:mm", Locale.US);		
		return sdf_time.format(mDate.getTime());
	}
	
	public String getDateTimeString() {
		SimpleDateFormat sdf_time = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);		
		return sdf_time.format(mDate.getTime());
	}
	
	public String getDisplayText() {
		return mDisplayText;
	}
	
	public boolean isSameDay(int year, int month, int day){
		if( day != mDate.get(Calendar.DAY_OF_MONTH) ) return false;
		if( month != mDate.get(Calendar.MONTH) ) return false;
		if( year != mDate.get(Calendar.YEAR) ) return false;
		return true;
	}
	
	public void setDate(Calendar date) {
		mDate = date;
	}
	
	public void setDisplayText(String displayText) {
		mDisplayText = displayText;
	}

}
