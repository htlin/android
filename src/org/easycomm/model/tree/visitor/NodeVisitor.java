package org.easycomm.model.tree.visitor;

import org.easycomm.model.tree.Folder;
import org.easycomm.model.tree.Leaf;
import org.easycomm.model.tree.Link;
import org.easycomm.model.tree.Node;

public interface NodeVisitor<T> {

	void visit(Node<T> n);	
	void visit(Leaf<T> n);
	void visit(Folder<T> n);
	void visit(Link<T> n);
	
}
