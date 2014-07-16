package org.easycomm.config;

import java.util.List;
import java.util.Map;

import org.easycomm.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class ImageChooserDialogFragment extends DialogFragment {

	public static final String ARG_TITLE = "title";
	private String title;	
	private AddModifyVocabDialogFragment mListener;
	private int mSelectedIndex;	
	private List<Map<String, Object>> aList;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		try {			
			mListener = (AddModifyVocabDialogFragment) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getTargetFragment().toString() + " can not cast to AddModifyVocabDialogFragment");
		}

		title = getArguments().getString(ARG_TITLE);

		//Inflate view
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_image_choose, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title) 
		.setView(dialogView)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendVocabImageIndex();
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {				
			}
		});
		
		
		//Attach listeners and adapters
		ListView listView = (ListView) dialogView.findViewById(R.id.listview);
		
		SimpleAdapter adapter = getAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mSelectedIndex = position;
			}
		});

		//Populate content for Modify
		AlertDialog dialog = builder.create();
		dialog.show();
		
		validate(dialog);		
		return dialog;
	}
	
	
	private SimpleAdapter getAdapter() {
		aList = mListener.getListviewData();
		
		String[] from = { "image", "name" };
		int[] to = { R.id.vocab_image, R.id.vocab_name};

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), aList, R.layout.listview_layout, from, to);
		adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data, String textRepresentation) {
				if(view instanceof ImageView && data instanceof Drawable) {
					ImageView iv = (ImageView) view;
					iv.setImageDrawable((Drawable) data);
					return true;
				} else {
					return false;
				}
			}
		});

		return adapter;
	}
	
	private void sendVocabImageIndex(){
		mListener.setSelecetedIndex(mSelectedIndex);		
	}

	private void validate() {
		validate((AlertDialog) getDialog());
	}
	
	private void validate(AlertDialog dialog) {
		if (dialog == null) {
			return;
		}
	}

}
