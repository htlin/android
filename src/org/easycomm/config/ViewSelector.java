package org.easycomm.config;

import org.easycomm.R;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.View;

public class ViewSelector {

	private Resources mResources;
	
	private String mSelectedID;
	private String folderID;
	private View mSelectedView;
	
	public ViewSelector(Resources resources) {
		mResources = resources;
	}

	public boolean isSelected() {
		return mSelectedID != null;
	}

	public String getSelectedID() {
		return mSelectedID;
	}
	
	public boolean isFolderSelected(){
		return folderID != null;
	}
	
	public void setFolderID(String id){
		folderID = id;
	}
	
	public String getFolderID(){
		return folderID;
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
		folderID = null;
	}
	
	private void updateView(View v, boolean isSelected) {
		int color = mResources.getColor(R.color.buttonSelected);
		
		if (isSelected) {
			v.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
		} else {
			v.getBackground().clearColorFilter();
		}
	}

}
