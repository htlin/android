package org.easycomm.config;

import org.easycomm.R;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Vocab;

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
//		return mVocabDB.getTree().size();
		return -1;
	}
	
	public String getDisplayText(String id) {
//		return mVocabDB.getTree().getVocab(id).getDisplayText();
		return null;
	}
	
	public DraggableButton get(String id, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
//		Vocab v = mVocabDB.getTree().getVocab(id);
//		return get(v, onClickListener, onLongClickListener, onDragListener);
		return null;
	}

	public DraggableButton get(int pos, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
//		Vocab v = mVocabDB.getTree().getVocab(pos);
//		return get(v, onClickListener, onLongClickListener, onDragListener);
		return null;
	}
	
	public DraggableButton get(Vocab v, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
		DraggableButton button = new DraggableButton(mContext);
		button.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
    	button.setText(v.getData().getDisplayText());
        button.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
        button.setCompoundDrawablesWithIntrinsicBounds(null, v.getData().getImage(), null, null);
        button.setOnClickListener(onClickListener);
        button.setOnLongClickListener(onLongClickListener);
        button.setOnDragListener(onDragListener);
        button.setTag(v.getID());
		return button;
	}

}
