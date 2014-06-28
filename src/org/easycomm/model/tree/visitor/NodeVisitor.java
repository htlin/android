package org.easycomm.model.tree.visitor;

import org.easycomm.model.tree.Folder;
import org.easycomm.model.tree.Leaf;
import org.easycomm.model.tree.Link;
import org.easycomm.model.tree.Node;

public abstract class NodeVisitor<T> {

	public final void visit(Node<T> n) {}
	
	public abstract void visit(Leaf<T> n);
	public abstract void visit(Folder<T> n);
	public abstract void visit(Link<T> n);
	
}
