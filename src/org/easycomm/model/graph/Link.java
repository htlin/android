package org.easycomm.model.graph;

import org.easycomm.model.VocabData;
import org.easycomm.model.visitor.VocabVisitor;


public class Link extends Vocab {

	public Link(String id, VocabData data) {
		super(id, data);
	}

	@Override
	public void accept(VocabVisitor v) {
		v.visit(this);
	}

}
