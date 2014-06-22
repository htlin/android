package org.easycomm.model;

import android.graphics.drawable.Drawable;

public class Vocab {

	protected String mID;
	protected String mDisplayText;
	protected String mSpeechText;
	protected String mFilename;
	protected Drawable mImage;
	
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
	
	public boolean hasSpeechText() {
		return mSpeechText == null || mSpeechText.isEmpty();
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
