package org.easycomm.model.tree;

import java.util.List;

public class LinkedTree<T> {

	private Node<T> mRoot;
	
	public List<Node<T>> getAllFolders() {
		FolderCollector<T> v = new FolderCollector<T>();
		mRoot.accept(v);
		return v.getResult();
	}
	
}
