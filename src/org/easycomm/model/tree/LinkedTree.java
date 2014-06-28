package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.Vocab;
import org.easycomm.model.tree.visitor.FolderCollector;

public class LinkedTree<T> {

	private Folder<T> mRoot;
	
	public List<Folder<T>> getAllFolders() {
		FolderCollector<T> v = new FolderCollector<T>();
		mRoot.traverseFolderWith(v);
		return v.getResult();
	}
	
	
	public Folder<T> getFolder() {
		return null;
	}

	public void add(String folderID, Vocab v) {
	}
	
	public void remove(String id) {
	}

	public void move(String sourceID, String targetID) {
//		if (sourceID.equals(targetID)) return;
//		
//		Vocab sourceVocab = mVocabMap.get(sourceID);
//		Vocab targetVocab = mVocabMap.get(targetID);
//		
//		int targetIndex = mVocabs.indexOf(targetVocab);
//		mVocabs.remove(sourceVocab);
//		mVocabs.add(targetIndex, sourceVocab);
	}

}
