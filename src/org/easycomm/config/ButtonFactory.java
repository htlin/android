package org.easycomm.config;

import org.easycomm.R;
import org.easycomm.db.Vocab;
import org.easycomm.db.VocabDatabase;

import android.content.Context;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ButtonFactory {

	private Context mContext;
	private VocabDatabase mVocabDB;
	private int mSideSize;
	
	public ButtonFactory(Context c, VocabDatabase vocabDB) {
		mContext = c;
		mVocabDB = vocabDB;
		mSideSize = c.getResources().getDimensionPixelSize(R.dimen.button_side);
	}

	public int size() {
		return mVocabDB.size();
	}
	
	public String getText(String id) {
		return mVocabDB.getText(id);
	}
	
	public Button get(String id, OnClickListener onClickListener) {
		Vocab v = mVocabDB.getVocab(id);
		return get(v, onClickListener);
	}

	public Button get(int pos, OnClickListener onClickListener) {
		Vocab v = mVocabDB.getVocab(pos);
		return get(v, onClickListener);
	}
	
	public Button get(Vocab v, OnClickListener onClickListener) {
		Button button = new Button(mContext);
		button.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
    	button.setText(v.getText());
        button.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
        button.setCompoundDrawablesWithIntrinsicBounds(null, v.getImage(), null, null);
        button.setOnClickListener(onClickListener);
        button.setTag(v.getID());
		return button;
	}

}
