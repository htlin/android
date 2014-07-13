package org.easycomm.main;

import java.util.ArrayList;
import java.util.List;

import org.easycomm.MainActivity;
import org.easycomm.R;
import org.easycomm.util.CUtil;
import org.easycomm.util.Constant;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class SentenceFragment extends Fragment {

	public interface SentenceActionListener {
		void onSentenceButtonClick(String id);
		void onSentenceBarClick(List<String> ids);
	}

	private ButtonFactory mButtonFactory;
	private SentenceActionListener mCallback;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
            mCallback = (SentenceActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement SentenseActionListener");
        }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sentence, container, false);

		MainActivity activity = (MainActivity) getActivity();
		mButtonFactory = activity.getButtonFactory();
		
		Button delete = (Button) view.findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDeleteClick(v);
			}
		});
		
		Button deleteAll = (Button) view.findViewById(R.id.delete_all);
		deleteAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDeleteAllClick(v);
			}
		});
		
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.sentence);
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
			ArrayList<String> buttonIDs = savedInstanceState.getStringArrayList(Constant.BUTTON_IDS);
			if (buttonIDs != null) {
				for (String id : buttonIDs) {
					addButton(id);
				}
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentence);
		
		ArrayList<String> buttonIDs = (ArrayList<String>) getButtonIDs(ll);
		outState.putStringArrayList(Constant.BUTTON_IDS, buttonIDs);
		
		super.onSaveInstanceState(outState);
	}
	
	public void addButton(String key) {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentence);
		Button button = mButtonFactory.get(key, new OnClickListener() {
			@Override
			public void onClick(View v) {
				onVocabClick(v);
			}
		});
		
		ll.addView(button);
	}
	
	private void onDeleteClick(View v) {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentence);
		int size = ll.getChildCount();
		if (size > 0) {
			ll.removeViewAt(size - 1);
		}
	}
	
	protected void onDeleteAllClick(View v) {
		deleteAll();
	}

	private void onVocabClick(View v) {
		Button button = (Button) v;
		String id = (String) button.getTag();
		mCallback.onSentenceButtonClick(id);
	}
	
	private void onBackgroundClick(View v) {
		LinearLayout ll = (LinearLayout) v;
		List<String> ids = getButtonIDs(ll);
		mCallback.onSentenceBarClick(ids);
	}
	
	private List<String> getButtonIDs(LinearLayout ll) {
		List<String> ids = CUtil.makeList();
		for (int i = 0; i < ll.getChildCount(); i++) {
			Button b = (Button) ll.getChildAt(i);
			String id = (String) b.getTag();
			ids.add(id);
		}
		return ids;
	}

	private void deleteAll() {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentence);
		ll.removeAllViews();
	}

	public void invalidate() {
		deleteAll();
	}

}
