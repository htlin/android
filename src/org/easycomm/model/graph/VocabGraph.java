package org.easycomm.model.graph;

import java.util.List;
import java.util.Map;

import org.easycomm.model.Vocab;
import org.easycomm.util.CUtil;
import org.easycomm.util.DirectedOrderedGraph;

public class VocabGraph {
	
	private DirectedOrderedGraph<String> mGraph;
	private Map<String, Vocab> mMap;

	public VocabGraph() {
		mGraph = new DirectedOrderedGraph<String>();
		mMap = CUtil.makeMap();
	}
	
	public List<Folder> getAllFolders() {
		//TODO
		return null;
	}

	public List<Link> getSourceLinks(String folderID) {
		//TODO
		return null;
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
	
}
