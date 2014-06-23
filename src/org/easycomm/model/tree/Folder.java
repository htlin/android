package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.tree.visitor.NodeVisitor;

public class Folder<T> extends Node<T> {

	protected List<Node<T>> mChildren;

	@Override
	public List<Node<T>> getChildren() {
		return mChildren;
	}

	@Override
	public void traverseFolderWith(NodeVisitor<T> v) {
		accept(v);
		for (Node<T> c : getChildren()) {
			c.traverseFolderWith(v);
		}
	}

}
