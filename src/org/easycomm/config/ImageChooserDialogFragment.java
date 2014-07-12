package org.easycomm.config;

import java.util.List;
import java.util.Map;

import org.easycomm.R;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.VocabReader;
import org.easycomm.model.graph.Vocab;
import org.easycomm.util.CUtil;

import android.app.Activity;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class ImageChooserDialogFragment extends DialogFragment {

	public static final String ARG_VOCAB_ID = "vocabID";
	
	private AddModifyVocabDialogFragment mListener;
	private int mSelectedVocabImageIndex;
	private String mSelectedVocabImageName;
	private String mSelectedVocabID;
	private List<Map<String, Object>> aList;
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//
		try {
			
			mListener = (AddModifyVocabDialogFragment) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getTargetFragment().toString() + " can not cast to AddModifyVocabDialogFragment");
		}
		
		//
		
		mSelectedVocabID = getArguments().getString(ARG_VOCAB_ID);
		mSelectedVocabImageName = null;
		
		//Inflate view
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_image_choose, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.image_choose )
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
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				TextView imageName = (TextView) view.findViewById(R.id.vocab_name);
//				System.err.println("image name =  " + imageName.getText());
				mSelectedVocabImageName = imageName.getText().toString();
			}
			
		});
			
		
		//Populate content for Modify
		AlertDialog dialog = builder.create();
		dialog.show();
		
		validate(dialog);
		
		return dialog;
	}
	
	
	private SimpleAdapter getAdapter() {
		VocabReader vocabReader = VocabReader.getInstance(getResources().getAssets());
		aList = CUtil.makeList();
		for (VocabData vocabData : vocabReader.getAllVocabData()) {
			Map<String, Object> map = CUtil.makeMap();
			map.put("name", vocabData.getDisplayText());
			map.put("image", vocabData.getImage());
			aList.add(map);
		}

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
		System.err.println("image select index =  " + mSelectedVocabImageIndex);
		mListener.receiveData(mSelectedVocabImageName);
		
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
