package org.easycomm.config;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ButtonAdapter extends BaseAdapter {

	private ButtonFactory mButtonFactory;

	public ButtonAdapter(ButtonFactory buttonFactory) {
		mButtonFactory = buttonFactory;
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
		return mButtonFactory.get(position);
	}

}
