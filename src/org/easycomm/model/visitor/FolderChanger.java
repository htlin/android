package org.easycomm.model.visitor;

import java.util.List;

import org.easycomm.model.VocabDatabase;
import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;
import org.easycomm.model.graph.Vocab;

public class FolderChanger implements VocabVisitor {

	public static final FolderChanger INSTANCE = new FolderChanger();
	
	private VocabDatabase mVocabDB;
	private String mResult;

	public FolderChanger init(VocabDatabase vocabDB) {
		mVocabDB = vocabDB;
		return this;
	}

	public String getResult() {
		return mResult;
	}

	@Override
	public void visit(Leaf v) {
		mResult = null;
	}

	@Override
	public void visit(Folder v) {
		mResult = v.getID();
	}

	@Override
	public void visit(Link v) {
		List<Vocab> children = mVocabDB.getGraph().getChildren(v.getID());
		mResult = children.get(0).getID();
	}

}
