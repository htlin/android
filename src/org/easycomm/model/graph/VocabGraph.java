package org.easycomm.model.graph;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easycomm.model.VocabData;
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
		Folder root = makeFolder(rootData);
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
		List<Folder> list = CUtil.makeList();
		Collection<Vocab> allVocab = mMap.values();
		for (Vocab v : allVocab) {
			if (v instanceof Folder) {
				list.add((Folder)v);
			}
		}
		return list;
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
		Set<String> fromNodes = mGraph.getIncomingEdgesOf(folderID);
		List<Link> result = CUtil.makeList();
		for (String from : fromNodes) {
			Vocab v = mMap.get(from);
			if (v instanceof Link) {
				result.add((Link)v);
			}
		}	
		return result;
	}
	
	public void add(String folderID, String  childID) {
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
		mGraph.addVertex(folder.getID());
		mMap.put(folder.getID(), folder);
		return folder;
	}
	
	public Leaf makeLeaf(VocabData data) {
		Leaf leaf = new Leaf(getNewID(), data);
		mGraph.addVertex(leaf.getID());
		mMap.put(leaf.getID(), leaf);
		return leaf;
	}
	
	public Link makeLink(VocabData data) {
		Link link = new Link(getNewID(), data);
		mGraph.addVertex(link.getID());
		mMap.put(link.getID(), link);
		return link;
	}
	
	private String getNewID() {
		mlastID++;
		return Integer.toString(mlastID);
	}

}
