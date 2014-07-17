package org.easycomm.config;

import java.util.List;
import java.util.Map;

import org.easycomm.fragment.ImageChooserDialogFragment;
import org.easycomm.model.VocabData;
import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Folder;
import org.easycomm.util.CUtil;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.AdapterView;

public class LinkChooserDialogFragment extends ImageChooserDialogFragment {

	public interface LinkChooserDialogListener {
		public void onLinkChooserDialogPositiveClick(Folder folder);
	}
	
	private VocabDatabase mVocabDB;
	private LinkChooserDialogListener mListener;
	
	private List<Folder> cFolders;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		mVocabDB = VocabDatabase.getInstance(getResources().getAssets());
		cFolders = mVocabDB.getGraph().getAllFolders();
		
		try {			
			mListener = (LinkChooserDialogListener) getTargetFragment();
		} catch (ClassCastException e) {
			throw new ClassCastException(getTargetFragment().toString() + " can not cast to LinkChooserDialogListener");
		}
		
		return super.onCreateDialog(savedInstanceState);
	}

	@Override
	protected String getTitle() {
		return "Choose a link to an existing folder";
	}

	@Override
	protected void onPositiveClick() {
		int pos = getSelectedItemPosition();
		if (pos != AdapterView.INVALID_POSITION) {
			Folder folder = cFolders.get(pos);
			mListener.onLinkChooserDialogPositiveClick(folder);
		}
	}

	@Override
	protected void onNegativeClick() {
	}

	@Override
	protected List<Map<String, Object>> getListviewData() {
		List<Map<String, Object>> data = CUtil.makeList();		
		for (Folder folder : cFolders) {
			VocabData vocabData = folder.getData();
			Map<String, Object> map = CUtil.makeMap();
			map.put("name", vocabData.getDisplayText());
			map.put("image", vocabData.getImage());
			data.add(map);
		}	
		return data;
	}	

}
