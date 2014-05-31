package org.easycomm.config;

import org.easycomm.R;
import org.easycomm.model.Vocab;
import org.easycomm.model.VocabDatabase;

import android.content.Context;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
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
	
	public String getDisplayText(String id) {
		return mVocabDB.getDisplayText(id);
	}
	
	public DraggableButton get(String id, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
		Vocab v = mVocabDB.getVocab(id);
		return get(v, onClickListener, onLongClickListener, onDragListener);
	}

	public DraggableButton get(int pos, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
		Vocab v = mVocabDB.getVocab(pos);
		return get(v, onClickListener, onLongClickListener, onDragListener);
	}
	
	public DraggableButton get(Vocab v, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
		DraggableButton button = new DraggableButton(mContext);
		button.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
    	button.setText(v.getDisplayText());
        button.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
        button.setCompoundDrawablesWithIntrinsicBounds(null, v.getImage(), null, null);
        button.setOnClickListener(onClickListener);
        button.setOnLongClickListener(onLongClickListener);
        button.setOnDragListener(onDragListener);
        button.setTag(v.getID());
		return button;
	}

}
