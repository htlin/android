package org.easycomm.model.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easycomm.model.VocabData;
import org.easycomm.model.visitor.VocabSorter;
import org.easycomm.util.CUtil;
import org.easycomm.util.DirectedOrderedGraph;

public class VocabGraph {
	
	public static final String ROOT_ID = "root";
	
	// Currently the largest vertex ID in this graph
	private int mlastID;
	
	private DirectedOrderedGraph<String> mGraph;
	private Map<String, Vocab> mMap;

	public VocabGraph() {
		mGraph = new DirectedOrderedGraph<String>();
		mMap = CUtil.makeMap();
		mlastID = 0;
		
		VocabData rootData = new VocabData(ROOT_ID, ROOT_ID, null, null);
		Folder root = new Folder(ROOT_ID, rootData);
		mGraph.addVertex(ROOT_ID);
		mMap.put(ROOT_ID, root);	
	}

	public Folder getRoot() {
		return (Folder) getVocab(ROOT_ID);
	}

	public Vocab getVocab(String id) {
		return mMap.get(id);
	}
	
	public List<Folder> getAllFolders() {
		VocabSorter.INSTANCE.clear();
		for (Vocab v : mMap.values()) {
			v.accept(VocabSorter.INSTANCE);
		}
		return VocabSorter.INSTANCE.getFolders();
	}

	public List<Vocab> getChildren(String folderID) {
		List<String> toNodes = mGraph.getOutgoingEdgesOf(folderID);
		List<Vocab> result = CUtil.makeList();
		for (String to : toNodes) {
			Vocab v = mMap.get(to);
			result.add(v);
		}	
		return result;
	}
	
	public List<Link> getSourceLinks(String folderID) {
		VocabSorter.INSTANCE.clear();
		Set<String> fromNodes = mGraph.getIncomingEdgesOf(folderID);
		for (String from : fromNodes) {
			Vocab v = mMap.get(from);
			v.accept(VocabSorter.INSTANCE);
		}	
		return VocabSorter.INSTANCE.getLinks();
	}

	public void addChild(Vocab folder, Vocab child) {
		addChild(folder.getID(), child.getID());
	}

	public void addChild(String folderID, String childID) {
		mGraph.addEdge(folderID, childID);
	}
	
	public void remove(String id) {
		mGraph.removeVertex(id);
		mMap.remove(id);
	}

	public void move(String folderID, String sourceID, String targetID) {
		mGraph.move(folderID, sourceID, targetID);
	}
	
	
	public Folder makeFolder(VocabData data) {
		Folder folder = new Folder(getNewID(), data);
		addVocab(folder);
		return folder;
	}
	
	public Leaf makeLeaf(VocabData data) {
		Leaf leaf = new Leaf(getNewID(), data);
		addVocab(leaf);
		return leaf;
	}
	
	public Link makeLink(VocabData data) {
		Link link = new Link(getNewID(), data);
		addVocab(link);
		return link;
	}
	
	private void addVocab(Vocab v) {
		mGraph.addVertex(v.getID());
		mMap.put(v.getID(), v);
	}
	
	private String getNewID() {
		mlastID++;
		return Integer.toString(mlastID);
	}

}
