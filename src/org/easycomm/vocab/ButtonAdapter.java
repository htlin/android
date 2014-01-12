package org.easycomm.vocab;

import org.easycomm.R;
import org.easycomm.vocab.VocabFragment.VocabActionListener;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class ButtonAdapter extends BaseAdapter implements OnClickListener {

	private final String[] mButtonTexts = new String[] {
			"I",
			"You",
			"Me",
			"He",
			"She",
			"Her",
			"His",
			"Love",
			"Android",
			"To",
			"Get",
	};
	
	private Context mContext;
	private VocabActionListener mCallback;

	public ButtonAdapter(Context c, VocabActionListener callback) {
		mContext = c;
		mCallback = callback;
	}

	@Override
	public int getCount() {
		return mButtonTexts.length;
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
		Button button;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
        	button = new Button(mContext);
        	int side = mContext.getResources().getDimensionPixelSize(R.dimen.button_side);
        	button.setLayoutParams(new GridView.LayoutParams(side, side));
        	button.setPadding(0, 0, 0, 0);
        } else {
        	button = (Button) convertView;
        }

        button.setText(mButtonTexts[position]);
        button.setOnClickListener(this);
        return button;
	}

	@Override
	public void onClick(View view) {
		Button button = (Button) view;
		String text = button.getText().toString();
		mCallback.onVocabButtonClick(text);
	}

}
