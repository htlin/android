package org.easycomm.model.visitor;

import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;

public interface VocabVisitor {

	public void visit(Leaf v);
	public void visit(Folder v);
	public void visit(Link v);
	
}
