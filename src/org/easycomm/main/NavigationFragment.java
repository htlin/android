package org.easycomm.main;

import org.easycomm.R;
import org.easycomm.util.Constant;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class NavigationFragment extends Fragment {

	public interface NavigationListener {
		void onHomeButtonClick();
		void onBackButtonClick();
	}
	
	private NavigationListener mCallback;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
            mCallback = (NavigationListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement NavigationListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.navigation, container, false);

		Button home = (Button) view.findViewById(R.id.home);
		home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCallback.onHomeButtonClick();
			}
		});
		
		Button back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCallback.onBackButtonClick();
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (savedInstanceState != null) {
			CharSequence  currentFolder = savedInstanceState.getCharSequence(Constant.CURRENT_FOLDER);
			if (currentFolder != null) {
				TextView textview = (TextView) getView().findViewById(R.id.current_folder);
				textview.setText(currentFolder);
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		TextView textview = (TextView) getView().findViewById(R.id.current_folder);
		CharSequence  currentFolder = textview.getText();
		outState.putCharSequence(Constant.CURRENT_FOLDER, currentFolder);

		super.onSaveInstanceState(outState);
	}
	
	
	public void setCurrentFolder(String s) {
		TextView textview = (TextView) getView().findViewById(R.id.current_folder);
		textview.setText(s);
	}

}
