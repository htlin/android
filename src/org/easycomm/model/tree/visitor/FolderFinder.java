package org.easycomm.model.tree.visitor;

import org.easycomm.model.tree.Folder;

public class FolderFinder<T> extends NodeVisitorAdapter<T> {

	private T mTarget;
	private Folder<T> mResult;
	
	public FolderFinder(T target) {
		mTarget = target;
	}
	
	public Folder<T> getResult() {
		return mResult;
	}

	@Override
	public void visit(Folder<T> n) {
		if (mTarget.equals(n.getObject())) {
			mResult = n;
		}
	}

}
