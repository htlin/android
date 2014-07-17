package org.easycomm.config;

import java.util.List;
import java.util.Map;

import org.easycomm.fragment.ImageChooserDialogFragment;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabReader;
import org.easycomm.util.CUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.AdapterView;

public class VocabChooserDialogFragment extends ImageChooserDialogFragment {

	public interface VocabChooserDialogListener {
		public void onVocabChooserDialogPositiveClick(VocabData data);
	}
	
	private VocabReader mVocabReader;
	private VocabChooserDialogListener mListener;
	
	private List<VocabData> cVocabData;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mVocabReader = VocabReader.getInstance(getResources().getAssets());
		cVocabData = mVocabReader.getAllVocabData();
		
		try {			
			mListener = (VocabChooserDialogListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getTargetFragment().toString() + " can not cast to VocabChooserDialogListener");
		}
		
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	protected String getTitle() {
		return "Choose an image from database";
	}

	@Override
	protected void onPositiveClick() {
		int pos = getSelectedItemPosition();
		if (pos != AdapterView.INVALID_POSITION) {
			VocabData data = cVocabData.get(pos);
			mListener.onVocabChooserDialogPositiveClick(data);
		}
	}

	@Override
	protected void onNegativeClick() {
	}

	@Override
	protected List<Map<String, Object>> getListviewData() {
		List<Map<String, Object>> data = CUtil.makeList();
		for (VocabData vocabData : cVocabData) {
			Map<String, Object> map = CUtil.makeMap();
			map.put("name", vocabData.getFilename());
			map.put("image", vocabData.getImage());
			data.add(map);
		}	
		return data;
	}	

}
