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
	
	private DirectedOrderedGraph<String> mGraph;
	private Map<String, Vocab> mMap;

	public VocabGraph() {
		mGraph = new DirectedOrderedGraph<String>();
		mMap = CUtil.makeMap();
		
		VocabData rootData = new VocabData(ROOT_ID, ROOT_ID, null, null);
		Folder root = makeFolder(rootData);
		mGraph.addVertex(ROOT_ID);
		mMap.put(ROOT_ID, root);
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
	
	public void add(String folderID, Vocab child) {
		mGraph.addVertex(child.getID());
		mGraph.addEdge(folderID, child.getID());
		mMap.put(child.getID(), child);
	}
	
	public void remove(String id) {
		mGraph.removeVertex(id);
		mMap.remove(id);
	}

	public void move(String folderID, String sourceID, String targetID) {
		mGraph.move(folderID, sourceID, targetID);
	}
	
	
	public Folder makeFolder(VocabData data) {
		return null;
	}
	
	public Leaf makeLeaf(VocabData data) {
		return null;
	}
	
	public Link makeLink(VocabData data) {
		return null;
	}
	
}
