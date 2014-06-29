package org.easycomm.main;

import org.easycomm.R;
import org.easycomm.model.Vocab;
import org.easycomm.model.VocabDatabase;

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
		return mVocabDB.getTree().size();
	}
	
	public String getDisplayText(String id) {
		return mVocabDB.getTree().getVocab(id).getDisplayText();
	}
	
	public Button get(String id, OnClickListener onClickListener) {
		Vocab v = mVocabDB.getTree().getVocab(id);
		return get(v, onClickListener);
	}

	public Button get(int pos, OnClickListener onClickListener) {
		Vocab v = mVocabDB.getTree().getVocab(pos);
		return get(v, onClickListener);
	}
	
	public Button get(Vocab v, OnClickListener onClickListener) {
		Button button = new Button(mContext);
		button.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
    	button.setText(v.getDisplayText());
        button.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
        button.setCompoundDrawablesWithIntrinsicBounds(null, v.getImage(), null, null);
        button.setOnClickListener(onClickListener);
        button.setTag(v.getID());
		return button;
	}

}
