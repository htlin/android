package org.easycomm.main;

import org.easycomm.MainActivity;
import org.easycomm.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class VocabFragment extends Fragment {

	public interface VocabActionListener {
		void onVocabButtonClick(String id);
	}

	private VocabActionListener mCallback;
	private ButtonAdapter mButtonAdapter;
	
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
		
		GridView gv = (GridView) view.findViewById(R.id.vocab_grid);
		MainActivity activity = (MainActivity) getActivity();
		mButtonAdapter = new ButtonAdapter(activity.getButtonFactory(), mCallback);
		gv.setAdapter(mButtonAdapter);
		
		return view;
	}

	public void invalidate() {
		mButtonAdapter.notifyDataSetChanged();
	}
		
}