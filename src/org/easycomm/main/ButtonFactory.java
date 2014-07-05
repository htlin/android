package org.easycomm.main;

import java.util.List;

import org.easycomm.R;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Vocab;
import org.easycomm.model.graph.VocabGraph;

import android.content.Context;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ButtonFactory {

	private Context mContext;
	private VocabDatabase mVocabDB;
	private int mSideSize;
	private String mCurrentFolder;
	
	public ButtonFactory(Context c, VocabDatabase vocabDB) {
		mContext = c;
		mVocabDB = vocabDB;
		mSideSize = mContext.getResources().getDimensionPixelSize(R.dimen.button_side);
		mCurrentFolder = VocabGraph.ROOT_ID;
	}

	public void setCurrentFolder(String folderID) {
		mCurrentFolder = folderID;
	}

	public int size() {
		List<Vocab> currentFolder = mVocabDB.getGraph().getChildren(mCurrentFolder);
		return currentFolder.size();
	}

	public String getDisplayText(String id) {
		return mVocabDB.getVocabData(id).getDisplayText();
	}
	
	public Button get(String id, OnClickListener onClickListener) {
		Vocab v = mVocabDB.getVocab(id);
		return get(v, onClickListener);
	}

	public Button get(int pos, OnClickListener onClickListener) {
		List<Vocab> currentFolder = mVocabDB.getGraph().getChildren(mCurrentFolder);
		Vocab v = currentFolder.get(pos);
		return get(v, onClickListener);
	}
	
	public Button get(Vocab v, OnClickListener onClickListener) {
		Button button = new Button(mContext);
		button.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
    	button.setText(v.getData().getDisplayText());
        button.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
        button.setCompoundDrawablesWithIntrinsicBounds(null, v.getData().getImage(), null, null);
        button.setOnClickListener(onClickListener);
        button.setTag(v.getID());
		return button;
	}

}
