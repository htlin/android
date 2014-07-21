package org.easycomm.model.visitor;

import java.util.List;

import org.easycomm.model.graph.Folder;
import org.easycomm.model.graph.Leaf;
import org.easycomm.model.graph.Link;
import org.easycomm.util.CUtil;

public class VocabSorter implements VocabVisitor {

	private List<Leaf> mLeaves;
	private List<Folder> mFolders;
	private List<Link> mLinks;
	
	public VocabSorter() {
		mLeaves = CUtil.makeList();
		mFolders = CUtil.makeList();
		mLinks = CUtil.makeList();
	}
	
	public List<Leaf> getLeaves() {
		return mLeaves;
	}
	
	public List<Folder> getFolders() {
		return mFolders;
	}
	
	public List<Link> getLinks() {
		return mLinks;
	}
	
	@Override
	public void visit(Leaf v) {
		mLeaves.add(v);
	}

	@Override
	public void visit(Folder v) {
		mFolders.add(v);
	}

	@Override
	public void visit(Link v) {
		mLinks.add(v);
	}

}
