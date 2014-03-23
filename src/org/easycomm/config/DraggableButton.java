package org.easycomm.config;

import org.easycomm.R;
import org.easycomm.dragdrop.DragSource;
import org.easycomm.dragdrop.DropTarget;

import android.content.ClipData;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.Button;

public class DraggableButton extends Button implements DragSource, DropTarget {

	public DraggableButton(Context context) {
		super(context);
	}

	private void setFilter() {
		getBackground().setColorFilter(R.color.buttonNearlyEmpty, PorterDuff.Mode.DARKEN);
	}

	private void clearFilter() {
		getBackground().clearColorFilter();
	}
	
	//DragSource methods
	
	@Override
	public boolean isDragAllowed() {
		return true;
	}

	@Override
	public ClipData getClipDataForDragDrop() {
		return null;
	}

	@Override
	public View getDragDropView() {
		return this;
	}

	@Override
	public void onDragStarted() {
		setFilter();
	}
	
	@Override
	public void onDropCompleted(DropTarget target, boolean success) {
		clearFilter();
	}
	
	

	//DropTarget Methods
	@Override
	public boolean isDropAllowedFrom(DragSource source) {
		if (source == this) return false;
		
		return true;
	}

	@Override
	public void onDrop(DragSource source) {
		if (source == this) return;
		
		clearFilter();
	}

	@Override
	public void onDragEnter(DragSource source) {
		if (source == this) return;
		
		setFilter();
	}

	@Override
	public void onDragExit(DragSource source) {
		if (source == this) return;
		
		clearFilter();
	}

	@Override
	public String getID() {
		return (String) getTag();
	}

}
