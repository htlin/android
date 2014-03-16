package org.easycomm.dragdrop;

import android.view.View;

public interface DropTarget {

	boolean isDropAllowedFrom(DragSource source);

	View getDragDropView();

	void onDrop(DragSource source);

	void onDragEnter(DragSource source);

	void onDragExit(DragSource source);

}
