package org.easycomm.model.graph;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.easycomm.model.VocabData;
import org.easycomm.model.visitor.VocabSorter;
import org.easycomm.model.visitor.VocabVisitor;
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
		VocabSorter visit = new VocabSorter();
		for (Entry<String, Vocab> entry : mMap.entrySet()) {
			String id = entry.getKey();
			if (ROOT_ID.equals(id)) continue;
			
			entry.getValue().accept(visit);
		}
		return visit.getFolders();
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
	
	public void traverseDescendants(String id, VocabVisitor visit) {
		Vocab v = getVocab(id);
		v.accept(visit);
		for (String child : mGraph.getOutgoingEdgesOf(id)) {
			traverseDescendants(child, visit);
		}
	}
	
	public void traverseParents(String id, VocabVisitor visit) {
		for (String parent : mGraph.getIncomingEdgesOf(id)) {
			Vocab v = getVocab(parent);
			v.accept(visit);
		}
	}

	public void removeChild(Vocab folder, Vocab child) {
		removeChild(folder.getID(), child.getID());
	}
	
	public void removeChild(String folderID, String childID) {
		mGraph.removeEdge(folderID, childID);
	}

	public void addChild(Vocab folder, Vocab child) {
		addChild(folder.getID(), child.getID());
	}

	public void addChild(String folderID, String childID) {
		mGraph.addEdge(folderID, childID);
	}
	
	
	
	public void remove(String id) {
		List<Vocab> allVocabs = CUtil.makeList();
		
		VocabSorter visit = new VocabSorter();
		traverseDescendants(id, visit);
		List<Folder> folders = visit.getFolders();
		allVocabs.addAll(folders);
		allVocabs.addAll(visit.getLeaves());
		allVocabs.addAll(visit.getLinks());
		
		visit = new VocabSorter();
		for (Folder f : folders) {
			traverseParents(f.getID(), visit);
		}
		allVocabs.addAll(visit.getLinks());
		
		for (Vocab v : allVocabs) {
			removeVertex(v.getID());
		}
	}
	
	private void removeVertex(String id) {
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
