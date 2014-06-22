package org.easycomm.model.tree;

public interface NodeVisitor<T> {

	void visit(Node<T> n);
	
}
