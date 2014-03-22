package org.easycomm.main;

import org.easycomm.main.VocabFragment.VocabActionListener;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ButtonAdapter extends BaseAdapter implements OnClickListener {

	private ButtonFactory mButtonFactory;
	private VocabActionListener mCallback;

	public ButtonAdapter(ButtonFactory buttonFactory, VocabActionListener callback) {
		mButtonFactory = buttonFactory;
		mCallback = callback;
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
		return mButtonFactory.get(position, this);
	}

	@Override
	public void onClick(View v) {
		String id = (String) v.getTag();
		mCallback.onVocabButtonClick(id);
	}

}
