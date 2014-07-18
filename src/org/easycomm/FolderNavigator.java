package org.easycomm;

import java.util.ArrayList;
import java.util.List;

import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.VocabGraph;
import org.easycomm.util.CUtil;
import org.easycomm.util.Constant;

import android.content.Intent;
import android.os.Bundle;

public class FolderNavigator {

	private ArrayList<String> mPathIDs;

	public FolderNavigator() {
		this(new ArrayList<String>());
	}
	
	public FolderNavigator(ArrayList<String> pathIDs) {
		mPathIDs = pathIDs;
		
		if (mPathIDs.isEmpty()) {
			mPathIDs.add(VocabGraph.ROOT_ID);
		}
	}

	public void load(Intent intent) {
		if (intent != null) {
			mPathIDs = intent.getStringArrayListExtra(Constant.FOLDER_PATH);
		}
	}

	public void load(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mPathIDs = savedInstanceState.getStringArrayList(Constant.FOLDER_PATH);
		}
	}
	
	public void save(Intent intent) {
		if (intent != null) {
			intent.putStringArrayListExtra(Constant.FOLDER_PATH, mPathIDs);
		}
	}

	public void save(Bundle outState) {
		if (outState != null) {
			outState.putStringArrayList(Constant.FOLDER_PATH, mPathIDs);
		}
	}

	public ArrayList<String> getList() {
		return mPathIDs;
	}

	public void setList(ArrayList<String> pathIDs) {
		mPathIDs = pathIDs;
	}

	public String getCurrentFolder() {
		int count = mPathIDs.size();
		if (count == 0) return null;
		else return mPathIDs.get(count - 1);		
	}

	public void moveDown(String id) {
		mPathIDs.add(id);
	}

	public boolean moveUp() {		
		if (mPathIDs.size() > 1) {
			mPathIDs.remove(mPathIDs.size() - 1);
			return true;
		} else {
			return false;
		}
	}

	public boolean reset() {
		if (mPathIDs.size() > 1) {
			mPathIDs.clear();
			mPathIDs.add(VocabGraph.ROOT_ID);
			return true;
		} else {
			return false;
		}
	}

	public List<String> getTexts(VocabDatabase vocabDB) {
		List<String> folderTexts = CUtil.makeList();
		for (String id : mPathIDs) {
			String text = vocabDB.getVocabData(id).getDisplayText();
			folderTexts.add(text);
		}
		return folderTexts;
	}

}
