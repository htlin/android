package org.easycomm.main;

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
			mResult.setLayoutParams(new GridView.LayoutParams(mSideSize - 8, mSideSize - 8));
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
		mSideSize = mContext.getResources().getDimensionPixelSize(R.dimen.button_side);
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
	
	public Button get(String id, OnClickListener onClickListener) {
		Vocab v = mVocabDB.getVocab(id);
		return get(v, onClickListener);
	}

	public Button get(int pos, OnClickListener onClickListener) {
		List<Vocab> currentFolder = mVocabDB.getGraph().getChildren(mCurrentFolder);
		Vocab v = currentFolder.get(pos);
		return get(v, onClickListener);
	}
	
	private Button get(Vocab v, OnClickListener onClickListener) {
		v.accept(mRenderer);
		Button button = mRenderer.getResult();
		button.setOnClickListener(onClickListener);
		return button;
	}

}
