package org.easycomm.sentense;

import java.util.List;

import org.easycomm.R;
import org.easycomm.util.CUtil;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

public class SentenseFragment extends Fragment {

	public interface SentenseActionListener {
		void onSentenseButtonClick(String text);
		void onSentenseBarClick(String text);
	}

	private SentenseActionListener mCallback;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sentense, container, false);
		
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
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
            mCallback = (SentenseActionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity + " must implement SentenseActionListener");
        }
	}

	public void addButton(String text) {
		LinearLayout ll = (LinearLayout) getView().findViewById(R.id.sentense);
		Button button = new Button(getActivity());
		int side = getResources().getDimensionPixelSize(R.dimen.button_side);
		button.setLayoutParams(new GridView.LayoutParams(side, side));
		button.setText(text);
		button.setOnClickListener(new OnClickListener() {
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
		String text = button.getText().toString();
		mCallback.onSentenseButtonClick(text);
	}
	
	private void onBackgroundClick(View v) {
		LinearLayout ll = (LinearLayout) v;
		List<Button> buttons = getButtons(ll);
		String text = getSentense(buttons);
		mCallback.onSentenseButtonClick(text);
	}
	
	private List<Button> getButtons(LinearLayout ll) {
		List<Button> buttons = CUtil.makeList();
		for (int i = 0; i < ll.getChildCount(); i++) {
			Button b = (Button) ll.getChildAt(i);
			buttons.add(b);
		}
		return buttons;
	}

	private String getSentense(List<Button> buttons) {
		StringBuffer buf = new StringBuffer();
		for (Button b : buttons) {
			buf.append(b.getText());
			buf.append(" ");
		}
		return buf.toString();
	}

}
