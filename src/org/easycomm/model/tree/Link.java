package org.easycomm.model.tree;

import java.util.List;

public class Link<T> extends Node<T> {

	protected Node<T> mLink;

	public Link(Node<T> link) {
		mLink = link;		
	}

	@Override
	public List<Node<T>> getChildren() {
		return mLink.getChildren();
	}

	public void accept(NodeVisitor<T> v) {
		v.visit(this);
	}
	
}
