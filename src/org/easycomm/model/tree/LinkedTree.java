package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.tree.visitor.FolderCollector;

public class LinkedTree<T> {

	private Node<T> mRoot;
	
	public List<Node<T>> getAllFolders() {
		FolderCollector<T> v = new FolderCollector<T>();
		mRoot.traverseFolderWith(v);
		return v.getResult();
	}

	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Node<T> getNode(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeNode(String id) {
		// TODO Auto-generated method stub
		
	}
	
}
