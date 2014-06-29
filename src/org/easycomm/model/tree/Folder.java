package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.tree.visitor.NodeVisitor;
import org.easycomm.util.CUtil;

public class Folder<T> extends Node<T> {

	protected List<Node<T>> mChildren;
	
	public Folder() {
		mChildren = CUtil.makeList();
	}

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
	
	private int indexOf(String id) {
		for (int i = 0; i < mChildren.size(); i++) {
			if (mChildren.get(i).getObject().equals(id)) {
				return i;
			}
		}		
		return -1;
	}

	public void add(Node<T> child) {
		mChildren.add(child);
	}
	
	public Node<T> remove(String id) {
		int i = indexOf(id);
		if (i >= 0) {
			return mChildren.remove(i);
		} else {
			return null;
		}
	}

	public void move(String sourceID, String targetID) {
		if (sourceID.equals(targetID)) return;
		
		int sourceIndex = indexOf(sourceID);
		int targetIndex = indexOf(targetID);
		Node<T> sourceNode = mChildren.remove(sourceIndex);
		mChildren.add(targetIndex, sourceNode);
	}

}
