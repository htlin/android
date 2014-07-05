package org.easycomm.main;

import java.util.List;

import org.easycomm.MainActivity;
import org.easycomm.R;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Vocab;

import android.content.Context;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ButtonFactory {

	//private Context mContext;
	private MainActivity mActivity;
	private VocabDatabase mVocabDB;
	private int mSideSize;
	private List<Vocab> currentVocabList;
	
	//public ButtonFactory(Context c, VocabDatabase vocabDB) {
	public ButtonFactory(MainActivity act, VocabDatabase vocabDB) {
		//mContext = c;
		mActivity = act;
		mVocabDB = vocabDB;
		mSideSize = mActivity.getResources().getDimensionPixelSize(R.dimen.button_side);
	}

		

	public int size() {
		String currentFolderString = mActivity.getCurrentFolder();
		String currentFolderID; 
		if( currentFolderString == null) {
			Vocab currentFolder = mVocabDB.getGraph().getRoot();
			currentFolderID = currentFolder.getID();
		}
		else {
			currentFolderID = currentFolderString;
		}
		currentVocabList = mVocabDB.getGraph().getChildren(currentFolderID);
		return currentVocabList.size();
	}

	
	
	
	public String getDisplayText(String id) {
		return mVocabDB.getVocabData(id).getDisplayText();
	}
	
	public Button get(String id, OnClickListener onClickListener) {
		Vocab v = mVocabDB.getGraph().getVocab(id);
		return get(v, onClickListener);
	}

		

	public Button get(int pos, OnClickListener onClickListener) {
		Vocab v = currentVocabList.get(pos);
		return get(v, onClickListener);
	}

	
	public Button get(Vocab v, OnClickListener onClickListener) {
		Button button = new Button(mActivity);
		button.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
    	button.setText(v.getData().getDisplayText());
        button.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
        button.setCompoundDrawablesWithIntrinsicBounds(null, v.getData().getImage(), null, null);
        button.setOnClickListener(onClickListener);
        button.setTag(v.getID());
		return button;
	}

}
