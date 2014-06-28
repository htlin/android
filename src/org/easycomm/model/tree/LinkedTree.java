package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.tree.visitor.FolderCollector;
import org.easycomm.model.tree.visitor.FolderFinder;

public class LinkedTree<T> {

	private Folder<T> mRoot;
	
	public List<Folder<T>> getAllFolders() {
		FolderCollector<T> v = new FolderCollector<T>();
		mRoot.traverseFolderWith(v);
		return v.getResult();
	}
		
	public Folder<T> getFolder(T folderID) {
		FolderFinder<T> v = new FolderFinder<T>(folderID);
		mRoot.traverseFolderWith(v);
		return v.getResult();
	}

}
