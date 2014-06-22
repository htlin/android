package org.easycomm.model.tree;

import java.util.List;

public abstract class Node<T> {

	protected String mID;
	protected T mObject;

	public String getID() {
		return mID;
	}
	
	public T getObject() {
		return mObject;
	}
	
	public abstract List<Node<T>> getChildren();
	public abstract void accept(NodeVisitor<T> v);
	
}
