package org.easycomm.dragdrop;

/*
 * Copyright (C) 2013 Wglxy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * (Note to other developers: The above note says you are free to do what you want with this code.
 *  Any problems are yours to fix. Wglxy.com is simply helping you get started. )
 */

import android.content.ClipData;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

public class DragController implements View.OnDragListener {

	private DragDropPresenter mPresenter;

	private boolean mDragging;            // indicates that drag-drop is in progress
	private boolean mDropSuccess;         // indicates that the drop was successful

	private DragSource mDragSource;       // where the drag originated
	private DropTarget mDropTarget;       // where the object was dropped

	public DragController(DragDropPresenter p) {
		mPresenter = p;
	}

	@Override
	public boolean onDrag(View v, DragEvent event) {
		// Check to see if the presenter object has drag-drop enabled.
		if (mPresenter != null) {
			if (!mPresenter.isDragDropEnabled ()) return false;
		}

		// Determine if the view is a DragSource, DropTarget, or both.
		// That information is used below when the event is handled.
		boolean isDragSource = false;
		boolean isDropTarget = false;
		DragSource source = null;
		DropTarget target = null;
		if (v instanceof DragSource) {
			isDragSource = true;
			source = (DragSource) v;
		}
		if (v instanceof DropTarget) {
			isDropTarget = true;
			target = (DropTarget) v;
		}
		
		boolean eventResult = false;
		final int action = event.getAction();

		// Handles each of the expected events
		switch(action) {

		case DragEvent.ACTION_DRAG_STARTED:
			// We want a call to mPresenter.onDragStarted once. So check to see if we are already dragging.
			if (!mDragging) {
				mDragging = true;
				mDropSuccess = false;
				if (mPresenter != null) mPresenter.onDragStarted (mDragSource);
				Log.d(DragController.class.getName(), "Drag started.");
			}

			// At the start of a drag, all drop targets must say they are interested in the rest
			// of the drag events of this drag-drop operation.
			// Allow for the case where a view is both a source and a target.
			if (isDragSource) {
				// The view continues to see drag events if it is the source of the current drag
				// or if it is a target itself.
				if (source == mDragSource) {
					if (source.isDragAllowed ()) {
						eventResult = true;
						source.onDragStarted ();
					}
				} else {
					eventResult = isDropTarget && target.isDropAllowedFrom (mDragSource);
				}
			} else if (isDropTarget) {
				eventResult = target.isDropAllowedFrom (mDragSource);
			} else eventResult =  false;
			break;

		case DragEvent.ACTION_DRAG_ENTERED:
			Log.d(DragController.class.getName(), "DragController.onDrag - entered view");
			if (isDropTarget) {
				target.onDragEnter (mDragSource);
				mDropTarget = target;
				eventResult = true;
			} else eventResult = false;
			break;

		case DragEvent.ACTION_DRAG_EXITED: 
			Log.d(DragController.class.getName(), "DragController.onDrag - exited view");
			if (isDropTarget) {
				mDropTarget = null;
				target.onDragExit (mDragSource);
				eventResult = true;
			} else eventResult = false;
			break;

		case DragEvent.ACTION_DROP: 
			Log.d(DragController.class.getName(), "DragController.onDrag - dropped");
			if (isDropTarget) {
				if (target.isDropAllowedFrom (mDragSource)) {
					target.onDrop (mDragSource);
					mDropTarget = target;
					mDropSuccess = true;
				}
				eventResult = true;
			} else eventResult = false;
			break;

		case DragEvent.ACTION_DRAG_ENDED:
			Log.d(DragController.class.getName(), "DragController.onDrag - ended");
			if (mDragging) {
				// At the end of the drag, do two things.
				// (1) Inform the drag source that the drag is over; (2) Inform the presenter.
				Log.d(DragController.class.getName(), "DragController.onDrag DragSource: " + mDragSource);
				if (mDragSource != null) mDragSource.onDropCompleted(mDropTarget, mDropSuccess);
				if (mPresenter != null) mPresenter.onDropCompleted(mDragSource, mDropTarget, mDropSuccess);
				eventResult =  true;
			}
			mDragging = false;
			mDragSource = null;
			mDropTarget = null;
			break;
		}
		return eventResult;

	}

	public boolean startDrag(View v) {
		if (!(v instanceof DragSource)) return false;
		
		DragSource ds = (DragSource) v;
		if (!ds.isDragAllowed ()) return false;

		mDragging = false;
		mDropSuccess = false;
		mDragSource = ds;
		mDropTarget = null;

		ClipData dragData = ds.getClipDataForDragDrop ();
		View.DragShadowBuilder shadowView = new View.DragShadowBuilder (v);
		v.startDrag (dragData, shadowView, null, 0);
		return true;
	}

}