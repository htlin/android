package org.easycomm.config;

import java.util.List;
import java.util.Map;

import org.easycomm.R;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.VocabReader;
import org.easycomm.model.graph.Vocab;
import org.easycomm.util.CUtil;

import android.annotation.SuppressLint;
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
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class AddModifyVocabDialogFragment extends DialogFragment 
//implements RequestDialogListener 
{

	public interface AddVocabDialogListener {
		public void onAddVocabDialogPositiveClick(AddModifyVocabDialogFragment dialog);
		public void onAddVocabDialogNegativeClick(AddModifyVocabDialogFragment dialog);
	}

	public static final String ARG_VOCAB_ID = "vocabID";
	
	private View dialogView;
	
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
		dialogView = inflater.inflate(R.layout.dialog_vocab_add, null);
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
		CheckBox speakCB = (CheckBox) dialogView.findViewById(R.id.speak_checkbox);
		EditText speechText = (EditText) dialogView.findViewById(R.id.speech_text);
		Button imageChooseButton = (Button) dialogView.findViewById(R.id.image_choose_button);
		ImageView image = (ImageView) dialogView.findViewById(R.id.vocab_image_chosen);
		
		if( mSelectedVocabID != null ){
			VocabDatabase vocabDB = VocabDatabase.getInstance(getResources().getAssets());
			Vocab vocab = vocabDB.getVocab(mSelectedVocabID);
			String text = vocab.getData().getDisplayText();
			displayText.setText(text);
			speechText.setText(text);
			image.setImageDrawable(vocab.getData().getImage());
		}
		
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
		
		imageChooseButton.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				chooseImage();
			}
		});
		
		//Populate content for Modify
		AlertDialog dialog = builder.create();
		dialog.show();
		
		
		
		validate(dialog);
		
		return dialog;
	}
	
	private void chooseImage() {
		DialogFragment chooseImageFragment = new ImageChooserDialogFragment();
		chooseImageFragment.setTargetFragment(this, 0);
		
		Bundle args = new Bundle();
		args.putString(ImageChooserDialogFragment.ARG_VOCAB_ID, mSelectedVocabID);
		chooseImageFragment.setArguments(args);
		
		chooseImageFragment.show(getFragmentManager(), "ChooseImage");		
	}
	
	public void receiveData(Drawable image){
		// not finish yet *****************************
		if(image != null) {
			ImageView iv = (ImageView) dialogView.findViewById(R.id.vocab_image_chosen);
			iv.setImageDrawable(image);
		}
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
