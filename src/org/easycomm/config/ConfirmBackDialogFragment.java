package org.easycomm.config;

import org.easycomm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConfirmBackDialogFragment extends DialogFragment {

	public interface ConfirmBackDialogListener {
		public void onConfirmBackDialogPositiveClick(DialogFragment dialog);
		public void onConfirmBackDialogNegativeClick(DialogFragment dialog);
	}
	
	ConfirmBackDialogListener mListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mListener = (ConfirmBackDialogListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement ConfirmBackDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_confirm_message)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onConfirmBackDialogPositiveClick(ConfirmBackDialogFragment.this);
			}
		})
		.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				mListener.onConfirmBackDialogNegativeClick(ConfirmBackDialogFragment.this);
			}
		});		
		return builder.create();
	}
	
}
