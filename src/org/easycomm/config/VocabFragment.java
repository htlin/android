package org.easycomm.config;

import org.easycomm.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class VocabFragment extends Fragment {

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.vocab, container, false);
		
		GridView gv = (GridView) view.findViewById(R.id.vocab_grid);
		
		return view;
	}
	
	
}
