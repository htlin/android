package org.easycomm.model.tree;

import java.util.Collections;
import java.util.List;

import org.easycomm.model.tree.visitor.NodeVisitor;

public class Leaf<T> extends Node<T> {

	@Override
	public List<Node<T>> getChildren() {
		return Collections.emptyList();
	}

	@Override
	public void traverseFolderWith(NodeVisitor<T> v) {
		accept(v);
	}
	
}
