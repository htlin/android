package org.easycomm.model.tree.visitor;

import java.util.List;

import org.easycomm.model.tree.Folder;
import org.easycomm.model.tree.Node;
import org.easycomm.util.CUtil;

public class FolderCollector<T> extends NodeVisitorAdapter<T> {

	private List<Node<T>> mResult;

	public FolderCollector() {
		mResult = CUtil.makeList();
	}
	
	public List<Node<T>> getResult() {
		return mResult;
	}

	@Override
	public void visit(Folder<T> n) {
		mResult.add(n);
	}

}
