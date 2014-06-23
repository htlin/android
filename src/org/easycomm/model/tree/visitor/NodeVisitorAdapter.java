package org.easycomm.model.tree.visitor;

import org.easycomm.model.tree.Folder;
import org.easycomm.model.tree.Leaf;
import org.easycomm.model.tree.Link;
import org.easycomm.model.tree.Node;

public class NodeVisitorAdapter<T> implements NodeVisitor<T> {

	@Override
	public void visit(Node<T> n) {
	}

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
