package org.easycomm.config;

import org.easycomm.R;
import org.easycomm.config.LinkChooserDialogFragment.LinkChooserDialogListener;
import org.easycomm.config.VocabChooserDialogFragment.VocabChooserDialogListener;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Folder;
import org.easycomm.util.Constant;

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
		public void onAddVocabDialogPositiveClick(VocabData data, String followupFolderID);
	}

	public static final String ARG_VOCAB_ID = "vocabID";
	private String mSelectedVocabID;

	public static final String ARG_VOCAB_TYPE = "vocabType";
	private int mVocabType;

	public static final String ARG_FOLDER_ID = "folderID";
	private String mFollowupFolderID;

	public static final String ARG_TITLE = "title";

	private VocabDatabase mVocabDB;

	private View mDialogView;

	private AddVocabDialogListener mListener;
	private String mNewFollowupFolderID;
	private String mImageFilename;


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
		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());

		mSelectedVocabID = getArguments().getString(ARG_VOCAB_ID);
		mVocabType = getArguments().getInt(ARG_VOCAB_TYPE);
		mFollowupFolderID = getArguments().getString(ARG_FOLDER_ID);
		String title = getArguments().getString(ARG_TITLE);

		//Inflate view
		LayoutInflater inflater = getActivity().getLayoutInflater();
		mDialogView = inflater.inflate(R.layout.dialog_vocab_add, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(title)
		.setView(mDialogView)
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
		EditText displayText = (EditText) mDialogView.findViewById(R.id.display_text);
		CheckBox speakCB = (CheckBox) mDialogView.findViewById(R.id.speak_checkbox);
		final EditText speechText = (EditText) mDialogView.findViewById(R.id.speech_text);
		Button imageChooseButton = (Button) mDialogView.findViewById(R.id.image_choose_button);
		ImageView image = (ImageView) mDialogView.findViewById(R.id.vocab_image_chosen);
		Button linkChooseButton = (Button) mDialogView.findViewById(R.id.link_choose_button);
		ImageView imageLink = (ImageView) mDialogView.findViewById(R.id.folder_image_chosen);

		if (mSelectedVocabID != null) {
			// Modify ...
			VocabData vocabData = mVocabDB.getVocab(mSelectedVocabID).getData();
			displayText.setText(vocabData.getDisplayText());
			speakCB.setChecked(true);
			speechText.setText(vocabData.getSpeechText());
			image.setImageDrawable(vocabData.getImage());
			if (mVocabType == Constant.LINK_TYPE) {
				VocabData linkFolderData = mVocabDB.getVocab(mFollowupFolderID).getData();
				imageLink.setImageDrawable(linkFolderData.getImage());
				mNewFollowupFolderID = mFollowupFolderID;
			} else {
				linkChooseButton.setVisibility(View.GONE);
				imageLink.setVisibility(View.GONE);
			}
		} else {
			// Add ...
			if (mVocabType == Constant.LINK_TYPE) {

			} else {
				linkChooseButton.setVisibility(View.GONE);
				imageLink.setVisibility(View.GONE);
			}
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
		ImageView iv = (ImageView) mDialogView.findViewById(R.id.folder_image_chosen);
		iv.setImageDrawable(image);
		mNewFollowupFolderID = folder.getID();
		validate();
	}

	@Override
	public void onVocabChooserDialogPositiveClick(VocabData data) {
		mImageFilename = data.getFilename();
		Drawable image = data.getImage();
		ImageView iv = (ImageView) mDialogView.findViewById(R.id.vocab_image_chosen);
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
		CheckBox speakCB = (CheckBox) mDialogView.findViewById(R.id.speak_checkbox);
		Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		if (displayText.getText().length() ==  0) {
			enable = enable && false;
		}
		if( speakCB.isChecked() && speechText.getText().length() == 0  ){
			enable = enable && false;
		}
		if( mImageFilename == null) enable = enable && false;
		if( mVocabType == Constant.LINK_TYPE && mNewFollowupFolderID == null) {
			enable = enable && false;
		}

		posButton.setEnabled(enable);
	}


	private void sendbackVocabData(){
		VocabData data = setupVocabData();		
		mListener.onAddVocabDialogPositiveClick(data, mNewFollowupFolderID);
	}

	private VocabData setupVocabData(){
		EditText displayTextView = (EditText) mDialogView.findViewById(R.id.display_text);
		String displayText = displayTextView.getText().toString();
		String speechText = null;
		CheckBox speakCB = (CheckBox) mDialogView.findViewById(R.id.speak_checkbox);
		if( speakCB.isChecked() ) {
			EditText speechTextView = (EditText) mDialogView.findViewById(R.id.speech_text);
			speechText = speechTextView.getText().toString();
		}

		ImageView voacbImageView = (ImageView) mDialogView.findViewById(R.id.vocab_image_chosen);
		Drawable vocabImage = voacbImageView.getDrawable();

		return new VocabData(displayText, speechText, mImageFilename,  vocabImage);
	}


	public String getSelectedVocabID() {
		return mSelectedVocabID;
	}

}
