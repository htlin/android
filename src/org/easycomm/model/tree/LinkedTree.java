package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.Vocab;
import org.easycomm.model.tree.visitor.FolderCollector;

public class LinkedTree<T> {

	private Node<T> mRoot;
	
	public List<Folder<T>> getAllFolders() {
		FolderCollector<T> v = new FolderCollector<T>();
		mRoot.traverseFolderWith(v);
		return v.getResult();
	}

	
	
	public int size() {
		return mVocabTree.size();
	}
	
	public T getVocab(String id) {
		return getNode(id).getObject();
	}
	
	public T getVocab(int index) {
		return mVocabs.get(index);
	}

	public void add(Vocab v) {
		mVocabs.add(v);
		mVocabMap.put(v.getID(), v);
	}
	
	public void remove(String id) {
		mVocabTree.removeNode(id);
	}

	public void move(String sourceID, String targetID) {
		if (sourceID.equals(targetID)) return;
		
		Vocab sourceVocab = mVocabMap.get(sourceID);
		Vocab targetVocab = mVocabMap.get(targetID);
		
		int targetIndex = mVocabs.indexOf(targetVocab);
		mVocabs.remove(sourceVocab);
		mVocabs.add(targetIndex, sourceVocab);
	}

	
	public Node<T> getNode(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeNode(String id) {
		// TODO Auto-generated method stub
		
	}
	
}
