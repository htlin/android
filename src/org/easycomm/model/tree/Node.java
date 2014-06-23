package org.easycomm.model.tree;

import java.util.List;

import org.easycomm.model.tree.visitor.NodeVisitor;

public abstract class Node<T> {

	protected String mID;
	protected T mObject;

	public String getID() {
		return mID;
	}
	
	public T getObject() {
		return mObject;
	}

	public void accept(NodeVisitor<T> v) {
		v.visit(this);
	}
	
	public abstract List<Node<T>> getChildren();
	public abstract void traverseFolderWith(NodeVisitor<T> v);
	
}
