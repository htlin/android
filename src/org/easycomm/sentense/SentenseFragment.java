package org.easycomm.sentense;

import java.util.ArrayList;
import java.util.List;

import org.easycomm.MainActivity;
import org.easycomm.R;
import org.easycomm.util.CUtil;
import org.easycomm.vocab.ButtonFactory;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SentenseFragment extends Fragment {

	public interface SentenseActionListener {
		void onSentenseButtonClick(String key);
		void onSentenseBarClick(List<String> keys);
	}

	private static final String BUTTON_KEYS = "button_keys";

	private ButtonFactory mButtonFactory;
	private SentenseActionListener mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
            mCallback = (SentenseActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement SentenseActionListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sentense, container, false);

		MainActivity activity = (MainActivity) getActivity();
		mButtonFactory = activity.getButtonFactory();
		
		Button delete = (Button) view.findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDeleteClick(v);
			}
		});
		
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.sentense);
		ll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackgroundClick(v);
			}
		});
				
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if (savedInstanceState != null) {
			ArrayList<String> buttonKeys = savedInstanceState.getStringArrayList(BUTTON_KEYS);
			if (buttonKeys != null) {
				for (String key : buttonKeys) {
					addButton(key);
				}
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentense);
		
		ArrayList<String> buttonKeys = (ArrayList<String>) getButtonKeys(ll);
		outState.putStringArrayList(BUTTON_KEYS, buttonKeys);
		
		super.onSaveInstanceState(outState);
	}
	
	public void addButton(String key) {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentense);
		Button button = mButtonFactory.get(key, new OnClickListener() {
			@Override
			public void onClick(View v) {
				onVocabClick(v);
			}
		});
		
		ll.addView(button);
	}
	
	private void onDeleteClick(View v) {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentense);
		int size = ll.getChildCount();
		if (size > 0) {
			ll.removeViewAt(size - 1);
		}
	}

	private void onVocabClick(View v) {
		Button button = (Button) v;
		String key = (String) button.getTag();
		mCallback.onSentenseButtonClick(key);
	}
	
	private void onBackgroundClick(View v) {
		LinearLayout ll = (LinearLayout) v;
		List<String> keys = getButtonKeys(ll);
		mCallback.onSentenseBarClick(keys);
	}
	
	private List<String> getButtonKeys(LinearLayout ll) {
		List<String> keys = CUtil.makeList();
		for (int i = 0; i < ll.getChildCount(); i++) {
			Button b = (Button) ll.getChildAt(i);
			String key = (String) b.getTag();
			keys.add(key);
		}
		return keys;
	}

	

}
