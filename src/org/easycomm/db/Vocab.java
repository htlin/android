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
