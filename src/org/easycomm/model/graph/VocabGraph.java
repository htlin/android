package org.easycomm.model.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.easycomm.util.CUtil;
import org.easycomm.util.DirectedOrderedGraph;

public class VocabGraph {
	
	public static final String ROOT_ID = "root";
	
	private DirectedOrderedGraph<String> mGraph;
	private Map<String, Vocab> mMap;

	public VocabGraph() {
		mGraph = new DirectedOrderedGraph<String>();
		mMap = CUtil.makeMap();
		
		Folder root = new Folder(ROOT_ID, ROOT_ID, null, null, null);
		mGraph.addVertex(ROOT_ID);
		mMap.put(ROOT_ID, root);
	}
	
	public List<Folder> getAllFolders() {
		//TODO
		List<Folder> list = CUtil.makeList();
		getAllFolders(ROOT_ID, list);
		return list;
	}
	
	private void getAllFolders(String parent,  List<Folder> list){
		
		List<String>  children = mGraph.getOutgoingEdgesOf(parent);
		for(String child : children) {
			Vocab v = mMap.get(child);
			if ( v instanceof Folder){
				list.add((Folder)v);
				String folderID = v.getID();
				getAllFolders(folderID, list);
			}
		}
		
		return;
	}

	public List<Link> getSourceLinks(String folderID) {
		//TODO
		Set<String> fromNodes = mGraph.getIncomingEdgesOf(folderID);
		List<Link> result = CUtil.makeList();
		for(String from : fromNodes){
			Vocab v = mMap.get(from);
			if ( v instanceof Link){
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
	
}
