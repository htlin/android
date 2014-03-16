package org.easycomm.config;

import org.easycomm.dragdrop.DragSource;
import org.easycomm.dragdrop.DropTarget;

import android.content.ClipData;
import android.content.Context;
import android.view.View;
import android.widget.Button;

public class DraggableButton extends Button implements DragSource, DropTarget {

	public DraggableButton(Context context) {
		super(context);
	}

	//DragSource methods
	
	@Override
	public boolean isDragAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClipData getClipDataForDragDrop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getDragDropView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onDragStarted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDropCompleted(DropTarget target, boolean success) {
		// TODO Auto-generated method stub
		
	}
	
	
	//DropTarget Methods
	@Override
	public boolean isDropAllowedFrom(DragSource source) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onDrop(DragSource source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragEnter(DragSource source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDragExit(DragSource source) {
		// TODO Auto-generated method stub
		
	}

}
