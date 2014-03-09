package org.easycomm.main;

import org.easycomm.main.VocabFragment.VocabActionListener;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ButtonAdapter extends BaseAdapter {

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
		OnClickListener onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				onVocabClick(v);
			}
		};
		
		return mButtonFactory.get(position, onClickListener);
	}

	private void onVocabClick(View view) {
		String key = (String) view.getTag();
		mCallback.onVocabButtonClick(key);
	}

}
