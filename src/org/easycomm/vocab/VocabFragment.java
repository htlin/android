package org.easycomm.vocab;

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
		void onVocabButtonClick(String key);
	}

	private VocabActionListener mCallback;
	
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
		gv.setAdapter(new ButtonAdapter(activity.getButtonFactory(), mCallback));
		
		return view;
	}
	
	
}
