package org.easycomm.model.tree;

import java.util.List;

public class Folder<T> extends Node<T> {

	protected List<Node<T>> mChildren;

	@Override
	public List<Node<T>> getChildren() {
		return mChildren;
	}

	public void accept(NodeVisitor<T> v) {
		v.visit(this);
		for (Node<T> c : getChildren()) {
			c.accept(v);
		}
	}
	
}
