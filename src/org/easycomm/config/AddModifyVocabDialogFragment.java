package org.easycomm.config;

import java.util.List;
import java.util.Map;

import org.easycomm.R;
import org.easycomm.model.Vocab;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.VocabReader;
import org.easycomm.util.CUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class AddModifyVocabDialogFragment extends DialogFragment {

	public interface AddVocabDialogListener {
		public void onAddVocabDialogPositiveClick(AddModifyVocabDialogFragment dialog);
		public void onAddVocabDialogNegativeClick(AddModifyVocabDialogFragment dialog);
	}

	public static final String ARG_VOCAB_ID = "vocabID";
	
	private AddVocabDialogListener mListener;
	private String mSelectedVocabID;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mListener = (AddVocabDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement AddVocabDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mSelectedVocabID = getArguments().getString(ARG_VOCAB_ID);
		
		//Inflate view
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_vocab_add, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mSelectedVocabID == null ? R.string.dialog_vocab_add : R.string.dialog_vocab_modify)
		.setView(dialogView)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onAddVocabDialogPositiveClick(AddModifyVocabDialogFragment.this);
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onAddVocabDialogNegativeClick(AddModifyVocabDialogFragment.this);
			}
		});
		
		//Attach listeners and adapters
		EditText displayText = (EditText) dialogView.findViewById(R.id.display_text);
		EditText speechText = (EditText) dialogView.findViewById(R.id.speech_text);
		ListView listView = (ListView) dialogView.findViewById(R.id.listview);
		
		TextWatcher textWatcher = new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				validate();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {	
			}			
		};
		displayText.addTextChangedListener(textWatcher);
		speechText.addTextChangedListener(textWatcher);
		
		SimpleAdapter adapter = getAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				validate();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		//Populate content for Modify
		AlertDialog dialog = builder.create();
		dialog.show();
		
		if (mSelectedVocabID == null) {
			listView.setSelection(0);
			listView.setItemChecked(0, true);
		} else {
			VocabDatabase vocabDB = VocabDatabase.getInstance(getResources().getAssets());
			Vocab vocab = vocabDB.getTree().getVocab(mSelectedVocabID);
			String text = vocab.getDisplayText();			
			displayText.setText(text);
			speechText.setText(text);
			
			VocabReader vocabReader = VocabReader.getInstance(getResources().getAssets());
			int index = vocabReader.indexOf(mSelectedVocabID);
			if (index < 0) {
				System.err.println("Vocab not found: " + mSelectedVocabID);
			} else {
				listView.setSelection(index);
				listView.setItemChecked(index, true);
			}
		}
		
		validate(dialog);
		
		return dialog;
	}

	private SimpleAdapter getAdapter() {
		VocabReader vocabReader = VocabReader.getInstance(getResources().getAssets());
		List<Map<String, Object>> aList = CUtil.makeList();
		for (Vocab vocab : vocabReader.getAllVocabs()) {
			Map<String, Object> map = CUtil.makeMap();
			map.put("name", vocab.getFilename());
			map.put("image", vocab.getImage());
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

	private void validate() {
		validate((AlertDialog) getDialog());
	}
	
	private void validate(AlertDialog dialog) {
		if (dialog == null) {
			return;
		}
		
		EditText displayText = (EditText) dialog.findViewById(R.id.display_text);
		EditText speechText = (EditText) dialog.findViewById(R.id.speech_text);
		Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		if (displayText.getText().length() == 0
				|| speechText.getText().length() == 0) {
			posButton.setEnabled(false);
		} else {
			posButton.setEnabled(true);
		}
	}

	public String getSelectedVocabID() {
		return mSelectedVocabID;
	}
	
	public String getDisplayText() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSpeechText() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVocabID() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
