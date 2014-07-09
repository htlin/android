package org.easycomm.config;

import java.util.List;

import org.easycomm.R;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;
import org.easycomm.model.graph.Vocab;
import org.easycomm.model.graph.VocabGraph;
import org.easycomm.model.visitor.VocabVisitor;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.GridView;

public class ButtonFactory {

	private Context mContext;
	private VocabDatabase mVocabDB;
	private int mSideSize;
	private String mCurrentFolder;
	private ButtonRenderer mRenderer;
	
	private class ButtonRenderer implements VocabVisitor {

		private Button mResult;

		public Button getResult() {
			return mResult;
		}
		
		private void makeButton(Vocab v) {
			mResult = new Button(mContext);
			mResult.setLayoutParams(new GridView.LayoutParams(mSideSize, mSideSize));
			mResult.setText(v.getData().getDisplayText());
			mResult.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
			mResult.setCompoundDrawablesWithIntrinsicBounds(null, v.getData().getImage(), null, null);
			mResult.setTag(v.getID());
		}
		
		@Override
		public void visit(Leaf v) {
			makeButton(v);
		}

		@Override
		public void visit(Folder v) {
			makeButton(v);
	        int folderColor = mContext.getResources().getColor(R.color.buttonFolderBackground);
	        mResult.getBackground().setColorFilter(folderColor, PorterDuff.Mode.MULTIPLY);
		}

		@Override
		public void visit(Link v) {
			makeButton(v);
	        int folderColor = mContext.getResources().getColor(R.color.buttonFolderBackground);
	        mResult.getBackground().setColorFilter(folderColor, PorterDuff.Mode.MULTIPLY);
		}

	}
	
	public ButtonFactory(Context c, VocabDatabase vocabDB) {
		mContext = c;
		mVocabDB = vocabDB;
		mSideSize = c.getResources().getDimensionPixelSize(R.dimen.button_side);
		mCurrentFolder = VocabGraph.ROOT_ID;
		mRenderer = new ButtonRenderer();
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
	
	public DraggableButton get(String id, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
		Vocab v = mVocabDB.getVocab(id);
		return get(v, onClickListener, onLongClickListener, onDragListener);
	}

	public DraggableButton get(int pos, OnClickListener onClickListener, OnLongClickListener onLongClickListener, OnDragListener onDragListener) {
		List<Vocab> currentFolder = mVocabDB.getGraph().getChildren(mCurrentFolder);
		Vocab v = currentFolder.get(pos);
		return get(v, onClickListener, onLongClickListener, onDragListener);
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
