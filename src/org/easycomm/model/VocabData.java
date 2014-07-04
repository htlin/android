package org.easycomm.model;

import android.graphics.drawable.Drawable;


public class VocabData {

	private String mDisplayText;
	private String mSpeechText;
	private String mFilename;
	private Drawable mImage;
	
	public VocabData(String displayText, String speechText, String filename, Drawable image) {
		mDisplayText = displayText;
		mSpeechText = speechText;
		mFilename = filename;
		mImage = image;
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

}
