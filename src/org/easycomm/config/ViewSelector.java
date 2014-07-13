package org.easycomm.config;

import android.view.View;

public class ViewSelector {

	private String mSelectedID;
	private String mFollowupFolderID;
	private View mSelectedView;
	
	public boolean isSelected() {
		return mSelectedID != null;
	}

	public String getSelectedID() {
		return mSelectedID;
	}
	
	public boolean hasFollowupFolder() {
		return mFollowupFolderID != null;
	}
	
	public void setFollowupFolderID(String id) {
		mFollowupFolderID = id;
	}
	
	public String getFollowupFolderID() {
		return mFollowupFolderID;
	}

	public boolean select(String id, View v) {
		if (id.equals(mSelectedID)) return false;
		
		if (mSelectedID != null) {
			updateView(mSelectedView, false);
		}
		
		mSelectedID = id;
		mSelectedView = v;
		updateView(mSelectedView, true);		
		return true;
	}

	public void deselect() {
		mSelectedID = null;
		mSelectedView = null;
		mFollowupFolderID = null;
	}
	
	private void updateView(View v, boolean isSelected) {
		v.setSelected(isSelected);
	}

}
