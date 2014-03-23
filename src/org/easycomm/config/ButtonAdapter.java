package org.easycomm.config;

import org.easycomm.config.VocabFragment.VocabActionListener;
import org.easycomm.dragdrop.DragController;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ButtonAdapter extends BaseAdapter implements OnClickListener, OnLongClickListener {

	private ButtonFactory mButtonFactory;
	private VocabActionListener mCallback;
	private DragController mDragController;

	public ButtonAdapter(ButtonFactory buttonFactory, VocabActionListener callback, DragController dragController) {
		mButtonFactory = buttonFactory;
		mCallback = callback;
		mDragController = dragController;
	}

	@Override
	public int getCount() {
		return mButtonFactory.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return mButtonFactory.get(position, this, this, mDragController);
	}

	@Override
	public void onClick(View v) {
		String id = (String) v.getTag();
		mCallback.onVocabButtonClick(id, v);
	}

	@Override
	public boolean onLongClick(View v) {
		mDragController.startDrag(v);
		return true;
	}

}
