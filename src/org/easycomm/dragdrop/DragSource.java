package org.easycomm.dragdrop;

import android.content.ClipData;
import android.view.View;

public interface DragSource {

	boolean isDragAllowed();

	ClipData getClipDataForDragDrop();

	View getDragDropView();

	void onDragStarted();

	void onDropCompleted(DropTarget target, boolean success);

}