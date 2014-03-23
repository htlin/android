package org.easycomm.config;

import org.easycomm.ConfigActivity;
import org.easycomm.R;
import org.easycomm.dragdrop.DragController;
import org.easycomm.dragdrop.DragDropPresenter;
import org.easycomm.dragdrop.DragSource;
import org.easycomm.dragdrop.DropTarget;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class VocabFragment extends Fragment implements DragDropPresenter {

	public interface VocabActionListener {
		void onVocabButtonClick(String id, View v);
		void onVocabDragDrop(String sourceID, String targetID);
	}

	private VocabActionListener mCallback;
	private ButtonAdapter mButtonAdapter;
	private DragController mDragController;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
            mCallback = (VocabActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement VocabActionListener");
        }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.vocab, container, false);
		
		mDragController = new DragController(this);
		
		GridView gv = (GridView) view.findViewById(R.id.vocab_grid);
		ConfigActivity activity = (ConfigActivity) getActivity();
		mButtonAdapter = new ButtonAdapter(activity.getButtonFactory(), mCallback, mDragController);
		gv.setAdapter(mButtonAdapter);
		
		return view;
	}

	public void invalidate() {
		mButtonAdapter.notifyDataSetChanged();
	}
	
	@Override
	public boolean isDragDropEnabled() {
		return true;
	}

	@Override
	public void onDragStarted(DragSource source) {
	}

	@Override
	public void onDropCompleted(DragSource source, DropTarget target, boolean success) {
		if (success) {
			mCallback.onVocabDragDrop(source.getID(), target.getID());
		}
	}
	
}