package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.util.CUtil;

public class FolderCollector<T> implements NodeVisitor<T> {

	private List<Node<T>> mResult;

	public FolderCollector() {
		mResult = CUtil.makeList();
	}
	
	@Override
	public void visit(Node<T> n) {
		if (n instanceof Folder) {
			mResult.add(n);
		}
	}

	public List<Node<T>> getResult() {
		return mResult;
	}

}
