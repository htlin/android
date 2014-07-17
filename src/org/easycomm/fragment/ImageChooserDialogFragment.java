package org.easycomm.fragment;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public abstract class ImageChooserDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		//Inflate view
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_image_choose, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(getTitle()) 
		.setView(dialogView)
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				onPositiveClick();
			}
		})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				onNegativeClick();
			}
		});


		//Attach listeners and adapters
		ListView listView = (ListView) dialogView.findViewById(R.id.listview);

		SimpleAdapter adapter = getAdapter();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				validate();
			}
		});

		//Populate content for Modify
		AlertDialog dialog = builder.create();
		dialog.show();

		validate(dialog);		
		return dialog;
	}

	private SimpleAdapter getAdapter() {
		List<Map<String, Object>> data = getListviewData();

		String[] from = { "image", "name" };
		int[] to = { R.id.vocab_image, R.id.vocab_name};

		SimpleAdapter adapter = new SimpleAdapter(getActivity(), data, R.layout.listview_layout, from, to);
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
		if (dialog == null) return;

		Button posButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
		if (getSelectedItemPosition(dialog) == AdapterView.INVALID_POSITION) {
			posButton.setEnabled(false);
		} else {
			posButton.setEnabled(true);
		}
	}

	protected int getSelectedItemPosition() {
		return getSelectedItemPosition(getDialog());
	}
	
	protected int getSelectedItemPosition(Dialog dialog) {
		ListView listView = (ListView) dialog.findViewById(R.id.listview);
		return listView.getCheckedItemPosition();
	}
	
	
	protected abstract String getTitle();
	protected abstract void onPositiveClick();
	protected abstract void onNegativeClick();
	protected abstract List<Map<String, Object>> getListviewData();

}
