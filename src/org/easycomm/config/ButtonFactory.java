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
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.GridView;

public class ButtonFactory {

	private Context mContext;
	private VocabDatabase mVocabDB;
	private int mSideSize;
	private String mCurrentFolder;
	private ButtonRenderer mRenderer;
	
	private class ButtonRenderer implements VocabVisitor {

		private DraggableButton mResult;

		public DraggableButton getResult() {
			return mResult;
		}
		
		private void makeButton(Vocab v) {
			mResult = new DraggableButton(mContext);
			mResult.setLayoutParams(new GridView.LayoutParams(mSideSize - 6, mSideSize - 6));
			mResult.setText(v.getData().getDisplayText());
			mResult.setGravity(Gravity.CENTER_HORIZONTAL + Gravity.BOTTOM);
			mResult.setCompoundDrawablesWithIntrinsicBounds(null, v.getData().getImage(), null, null);
			mResult.setTag(v.getID());
		}
		
		@Override
		public void visit(Leaf v) {
			makeButton(v);
			Drawable drawable = mContext.getResources().getDrawable(R.drawable.btn_leaf);
			mResult.setBackground(drawable);
		}

		@Override
		public void visit(Folder v) {
			makeButton(v);
			Drawable drawable = mContext.getResources().getDrawable(R.drawable.btn_folder);
			mResult.setBackground(drawable);
		}

		@Override
		public void visit(Link v) {
			makeButton(v);
			Drawable drawable = mContext.getResources().getDrawable(R.drawable.btn_link);
			mResult.setBackground(drawable);
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
		v.accept(mRenderer);
		DraggableButton button = mRenderer.getResult();
		button.setOnClickListener(onClickListener);
        button.setOnLongClickListener(onLongClickListener);
        button.setOnDragListener(onDragListener);
		return button;
	}

}
