package org.easycomm.model;

import android.graphics.drawable.Drawable;

public class Vocab {

	private String mID;
	private String mDisplayText;
	private String mSpeechText;
	private String mFilename;
	private Drawable mImage;
	
	public Vocab(String id, String displayText, String speechText, String filename, Drawable image) {
		mID = id;
		mDisplayText = displayText;
		mSpeechText = speechText;
		mFilename = filename;
		mImage = image;
	}

	public String getID() {
		return mID;
	}
	
	public String getDisplayText() {
		return mDisplayText;
	}
	
	public String getSpeechText() {
		return mSpeechText;
	}
	
	public String getFilename() {
		return mFilename;
	}
	
	public Drawable getImage() {
		return mImage;
	}

	public void setDisplayText(String v) {
		mDisplayText = v;
	}

	public void setSpeechText(String v) {
		mSpeechText = v;
	}

	public Vocab copy() {
		return new Vocab(mID, mDisplayText, mSpeechText, mFilename, mImage);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vocab)) return false;
		
		Vocab o = (Vocab) obj;
		return mID.equals(o.mID);
	}

	@Override
	public int hashCode() {
		return mID.hashCode();
	}

}
