package org.easycomm.dragdrop;

public interface DragDropPresenter {

	boolean isDragDropEnabled();

	void onDragStarted(DragSource source);

	void onDropCompleted(DropTarget target, boolean success);

}