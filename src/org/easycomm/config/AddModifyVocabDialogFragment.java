package org.easycomm.config;

import org.easycomm.R;
import org.easycomm.config.LinkChooserDialogFragment.LinkChooserDialogListener;
import org.easycomm.config.VocabChooserDialogFragment.VocabChooserDialogListener;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.VocabReader;
import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;
import org.easycomm.model.graph.Vocab;
import org.easycomm.model.visitor.VocabVisitor;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class AddModifyVocabDialogFragment extends DialogFragment implements
		VocabChooserDialogListener,
		LinkChooserDialogListener {

	public interface AddVocabDialogListener {
		public void onAddVocabDialogPositiveClick(String id, VocabData data, String followupFolderID);
	}

	public static final String ARG_VOCAB_ID = "vocabID";
	private String mSelectedVocabID;

	public static final String ARG_FOLDER_ID = "folderID";
	private String mFollowupFolderID;

	private VocabDatabase mVocabDB;
	private VocabReader mVocabReader; 
	private AddVocabDialogListener mListener;
	
	//Instance states
	public static final String DISPLAY_TEXT = "displayText";
	private String mDisplayText;
	
	public static final String SPEECH_CHECKBOX = "speechCheckBox";
	private boolean mSpeakCB;
	
	public static final String SPEECH_TEXT = "speechText";
	private String mSpeechText;
	
	public static final String NEW_IMAGE_VOCAB_DATA = "newImageVocabData";
	private String mNewImageVocabData;
	
	public static final String NEW_FOLLOWUP_FOLDER_ID = "newFollowupFolderID";
	private String mNewFollowupFolderID;
	
	public static final String REQUIRE_FOLLOWUP_FOLDER_ID = "requireFollowupFolderID";
	private boolean mRequireFollowupFolderID;

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
	public void onSaveInstanceState(Bundle outState){
		System.err.println("onSaveInstanceState called");
		outState.putString(DISPLAY_TEXT, mDisplayText);
		outState.putBoolean(SPEECH_CHECKBOX, mSpeakCB);
		outState.putString(SPEECH_TEXT, mSpeechText);
		outState.putString(NEW_IMAGE_VOCAB_DATA, mNewImageVocabData);
		outState.putString(NEW_FOLLOWUP_FOLDER_ID, mNewFollowupFolderID);
		outState.putBoolean(REQUIRE_FOLLOWUP_FOLDER_ID, mRequireFollowupFolderID);
		super.onSaveInstanceState(outState);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		// setup or restore instance variables
		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());
		mVocabReader = VocabReader.getInstance(getResources().getAssets());

		mSelectedVocabID = getArguments().getString(ARG_VOCAB_ID);
		mFollowupFolderID = getArguments().getString(ARG_FOLDER_ID);
			
		if(savedInstanceState != null){
			mDisplayText             = savedInstanceState.getString(DISPLAY_TEXT);
			mSpeakCB	             = savedInstanceState.getBoolean(SPEECH_CHECKBOX);
			mSpeechText              = savedInstanceState.getString(SPEECH_TEXT);
			mNewImageVocabData       = savedInstanceState.getString(NEW_IMAGE_VOCAB_DATA);
			mNewFollowupFolderID     = savedInstanceState.getString(NEW_FOLLOWUP_FOLDER_ID);
			mRequireFollowupFolderID = savedInstanceState.getBoolean(REQUIRE_FOLLOWUP_FOLDER_ID);
		}
		else {
			
			Vocab selectedVocab = mVocabDB.getVocab(mSelectedVocabID);
			VocabData selectedVocabData = selectedVocab.getData();
			
			if (selectedVocabData != null) {
				mDisplayText = selectedVocabData.getDisplayText();
				mSpeechText = selectedVocabData.getSpeechText();
				if(mSpeechText == null ){
					mSpeakCB = false;
				}
				else {
					mSpeakCB = true;
				}
				mNewImageVocabData = selectedVocabData.getDisplayText();
			}
			if(mFollowupFolderID != null){
				VocabData linkFolderData = mVocabDB.getVocab(mFollowupFolderID).getData();
				mNewImageVocabData = linkFolderData.getDisplayText();
				mNewFollowupFolderID = mFollowupFolderID;
				mRequireFollowupFolderID = true;
			}
			
		}
		
		

		//Inflate view
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.dialog_vocab_add, null);
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				sendbackVocabData();
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {				
			}
		});

		//Attach listeners and adapters
		EditText displayText = (EditText) view.findViewById(R.id.display_text);
		CheckBox speakCB = (CheckBox) view.findViewById(R.id.speak_checkbox);
		final EditText speechText = (EditText) view.findViewById(R.id.speech_text);
		final Button imageChooseButton = (Button) view.findViewById(R.id.image_choose_button);
		ImageView image = (ImageView) view.findViewById(R.id.vocab_image_chosen);
		final Button linkChooseButton = (Button) view.findViewById(R.id.link_choose_button);
		final ImageView imageLink = (ImageView) view.findViewById(R.id.folder_image_chosen);

		
		displayText.setText(mDisplayText);
		speakCB.setChecked(mSpeakCB);
		if(mSpeakCB) speechText.setText(mSpeechText);
		image.setImageDrawable(mVocabReader.getVocabData(mNewImageVocabData).getImage());
		
		 
		mVocabDB.getVocab(mSelectedVocabID).accept(new VocabVisitor() {
			@Override
			public void visit(Leaf v) {
				builder.setTitle("Add/Modify a Vocab");
				linkChooseButton.setVisibility(View.GONE);
				imageLink.setVisibility(View.GONE);
			}

			@Override
			public void visit(Folder v) {
				builder.setTitle("Add/Modify a Folder");
				linkChooseButton.setVisibility(View.GONE);
				imageLink.setVisibility(View.GONE);
			}

			@Override
			public void visit(Link v) {
				builder.setTitle("Add/Modify a Link");
				mRequireFollowupFolderID = true;
			}			
		});
		
		if( mRequireFollowupFolderID ){
			imageLink.setImageDrawable(mVocabDB.getVocabData(mFollowupFolderID).getImage());
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

		speakCB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean checked = ((CheckBox) v).isChecked();
				speechText.setEnabled(checked);
				validate();
			}			
		});

		imageChooseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				VocabChooserDialogFragment frag = new VocabChooserDialogFragment();
				frag.setTargetFragment(AddModifyVocabDialogFragment.this, 0);
				frag.show(getFragmentManager(), "VocabChooser");		
			}
		});

		linkChooseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LinkChooserDialogFragment frag = new LinkChooserDialogFragment();
				frag.setTargetFragment(AddModifyVocabDialogFragment.this, 0);
				frag.show(getFragmentManager(), "LinkChooser");		
			}
		});

		//Populate content for Modify
		AlertDialog dialog = builder.create();
		dialog.show();
		validate(dialog);		
		return dialog;
	}

	@Override
	public void onLinkChooserDialogPositiveClick(Folder folder) {
		Drawable image = folder.getData().getImage();
		ImageView iv = (ImageView) getDialog().findViewById(R.id.folder_image_chosen);
		iv.setImageDrawable(image);
		mNewFollowupFolderID = folder.getID();
		validate();
	}

	@Override
	public void onVocabChooserDialogPositiveClick(VocabData data) {
		mNewImageVocabData = data.getDisplayText();
		Drawable image = data.getImage();
		ImageView iv = (ImageView) getDialog().findViewById(R.id.vocab_image_chosen);
		iv.setImageDrawable(image);
		validate();
	}	

	private void validate() {
		validate((AlertDialog) getDialog());
	}

	private void validate(AlertDialog dialog) {
		if (dialog == null) {
			return;
		}

		boolean enable = true;

		EditText displayText = (EditText) dialog.findViewById(R.id.display_text);
		EditText speechText = (EditText) dialog.findViewById(R.id.speech_text);
		CheckBox speakCB = (CheckBox) dialog.findViewById(R.id.speak_checkbox);
		Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		if (displayText.getText().length() ==  0) {
			enable = false;
		}
		if (speakCB.isChecked() && speechText.getText().length() == 0) {
			enable = false;
		}
		if (mNewImageVocabData == null) {
			enable = false;
		}
		
		if (mRequireFollowupFolderID && mNewFollowupFolderID == null) {
			enable = false;
		}

		posButton.setEnabled(enable);
	}


	private void sendbackVocabData() {
		VocabData data = setupVocabData();		
		mListener.onAddVocabDialogPositiveClick(mSelectedVocabID, data, mNewFollowupFolderID);
	}

	private VocabData setupVocabData() {
		EditText displayTextView = (EditText) getDialog().findViewById(R.id.display_text);
		String displayText = displayTextView.getText().toString();
		String speechText = null;
		CheckBox speakCB = (CheckBox) getDialog().findViewById(R.id.speak_checkbox);
		if (speakCB.isChecked()) {
			EditText speechTextView = (EditText) getDialog().findViewById(R.id.speech_text);
			speechText = speechTextView.getText().toString();
		}

		ImageView vocabImageView = (ImageView) getDialog().findViewById(R.id.vocab_image_chosen);
		Drawable vocabImage = vocabImageView.getDrawable();
		String mImageFilename =  mVocabReader.getVocabData(mNewImageVocabData).getFilename();

		return new VocabData(displayText, speechText, mImageFilename, vocabImage);
	}


	public String getSelectedVocabID() {
		return mSelectedVocabID;
	}

}