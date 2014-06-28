package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.tree.visitor.NodeVisitor;

public class Folder<T> extends Node<T> {

	protected List<Node<T>> mChildren;

	@Override
	public List<Node<T>> getChildren() {
		return mChildren;
	}

	@Override
	public void traverseFolderWith(NodeVisitor<T> v) {
		accept(v);
		for (Node<T> c : getChildren()) {
			c.traverseFolderWith(v);
		}
	}

	public void add(Node<T> child) {
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
