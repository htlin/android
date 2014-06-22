package org.easycomm.model.tree;

import java.util.Collections;
import java.util.List;

public class Leaf<T> extends Node<T> {

	@Override
	public List<Node<T>> getChildren() {
		return Collections.emptyList();
	}

	public void accept(NodeVisitor<T> v) {
		v.visit(this);
	}
	
}
