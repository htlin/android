package org.easycomm.db;

import android.graphics.drawable.Drawable;

public class Vocab {

	private String mID;
	private String mText;
	private Drawable mImage;
	
	public Vocab(String id, String text, Drawable image) {
		mID = id;
		mText = text;
		mImage = image;
	}

	public String getID() {
		return mID;
	}
	
	public String getText() {
		return mText;
	}
	
	public Drawable getImage() {
		return mImage;
	}
	
}
