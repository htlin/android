package org.easycomm.model.tree.visitor;

import org.easycomm.model.tree.Folder;
import org.easycomm.model.tree.Leaf;
import org.easycomm.model.tree.Link;

public class NodeVisitorAdapter<T> extends NodeVisitor<T> {

	@Override
	public void visit(Leaf<T> n) {
	}

	@Override
	public void visit(Folder<T> n) {
	}

	@Override
	public void visit(Link<T> n) {
	}

}
