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

public class RequestNewTextDialogFragment extends DialogFragment {

	
	private AddModifyVocabDialogFragment mListener;
	private EditText displayText;


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//
		try {
			
			mListener = (AddModifyVocabDialogFragment) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getTargetFragment().toString() + " can not cast to AddModifyVocabDialogFragment");
		}
		
		//
		
		//Inflate view
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_vocab_add_2, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.testing_ask )
		.setView(dialogView)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendBackData();
				
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				
			}
		});
		
		//Attach listeners and adapters
		displayText = (EditText) dialogView.findViewById(R.id.testing_result);
		
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
		
		//Populate content for Modify
		AlertDialog dialog = builder.create();
		dialog.show();
		
		validate(dialog);
		
		return dialog;
	}
	
	private void sendBackData(){
		mListener.receiveData( displayText.getText().toString());
		getDialog().dismiss();
	}


	private void validate() {
		validate((AlertDialog) getDialog());
	}
	
	private void validate(AlertDialog dialog) {
		if (dialog == null) {
			return;
		}
		
		EditText displayText = (EditText) dialog.findViewById(R.id.testing_result);
		Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		if (displayText.getText().length() == 0) {
			posButton.setEnabled(false);
		} else {
			posButton.setEnabled(true);
		}
	}

	
}
