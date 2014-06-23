package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.tree.visitor.NodeVisitor;

public class Link<T> extends Node<T> {

	protected Node<T> mLink;

	public Link(Node<T> link) {
		mLink = link;		
	}

	@Override
	public List<Node<T>> getChildren() {
		return mLink.getChildren();
	}

	@Override
	public void traverseFolderWith(NodeVisitor<T> v) {
		accept(v);
	}
	
}