package org.easycomm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DirectedOrderedGraph<T> {

	private Map<T, ListSet<T>> mGraph;

	public DirectedOrderedGraph() {
		mGraph = new HashMap<T, ListSet<T>>();
	}

	public int getOrder(){
		return mGraph.size();
	}

	public List<T> getOutgoingEdgesOf(T vertex) {
		return mGraph.get(vertex).getList();
	}

	public Set<T> getIncomingEdgesOf(T vertex) {
		Set<T> parentSet = CUtil.makeSet();
		for (Entry<T, ListSet<T>> entry : mGraph.entrySet()) {
			T key = entry.getKey();
			ListSet<T> list = entry.getValue();
			if (list.contains(vertex)) {
				parentSet.add(key);
			}
		}
		return parentSet;
	}

	public boolean addVertex(T vertex) {
		if (mGraph.containsKey(vertex)) return false;
		else {
			mGraph.put(vertex, new ListSet<T>());
			return true;
		}		
	}

	private void putVertex(T vertex, ListSet<T> list) {
		mGraph.put(vertex, list);
	}

	public boolean addEdge(T parent, T child) throws IllegalArgumentException {
		if (mGraph.containsKey(parent) && mGraph.containsKey(child)) {
			ListSet<T> children = mGraph.get(parent);
			return children.add(child);
		} else {
			// parent or child vertex not exist !!!
			throw new IllegalArgumentException("Source or Destination vertex does not exist.");  
		}		
	}

	public boolean removeVertex(T vertex) {
		if (mGraph.containsKey(vertex)) {
			Set<T> fromVertices = getIncomingEdgesOf(vertex);
			for (T incoming : fromVertices) {
				removeEdge(incoming, vertex);
			}
			mGraph.remove(vertex);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeEdge(T parent, T child) {
		if (mGraph.containsKey(parent) && mGraph.containsKey(child) ) {
			ListSet<T> children = mGraph.get(parent);
			return children.remove(child);
		} else {
			return false;
		}
	}

	public void move(T parent, T source, T target) {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object other) {
		DirectedOrderedGraph<T> o = (DirectedOrderedGraph<T>) other;
		return mGraph.equals(o.mGraph);
	}

	@Override
	public int hashCode() {
		return mGraph.hashCode();
	}

	public static DirectedOrderedGraph<String> readGraph(BufferedReader buffer) throws IOException {
		List<String> lines = CUtil.makeList();
		while (true) {
			String line = buffer.readLine();
			if (line == null) break;
			
			lines.add(line);
		}
		
		String[] lineArray = new String[lines.size()];
		lines.toArray(lineArray);
		return makeGraph(lineArray);
	}
	
	/**
	 *	The context-free grammar of the representation of a DirectedOrderedGraph:
	 *	S :- VP ; S | VP
	 *	VP :- V : OG
	 *	OG :- null | VL
	 *	VL := V , VL | V
	 *	V :- (   T   )
	 *	where	S is a sentence
	 *			VP is vertex phrase
	 *			OG is a outgoing list
	 *			VL is a vertex list
	 *			V is a vertex
	 *			T is the type of a vertex
	 */	
	public static DirectedOrderedGraph<String> makeGraph(String graph) {
		return makeGraph(graph.split(";"));
	}
	
	public static DirectedOrderedGraph<String> makeGraph(String[] vertexPhrase) {
		DirectedOrderedGraph<String> dog = new DirectedOrderedGraph<String>();
		for (String vp : vertexPhrase) {
			// split vertex phrase into vertex and outgoing list
			String[] vList = vp.trim().split(":");
			//debug("vList", vList);

			String nodeFrom = parseType(vList[0]);

			// check whether outgoing list is empty
			if (vList.length > 1) {
				ListSet<String> list = new ListSet<String>();

				// split outgoing list into vertex array
				String[] listNode = vList[1].trim().split(",");
				//debug("listNode", listNode);

				for (String nodeTo : listNode) {
					// extract the information
					// and construct into the required type T
					String node = parseType(nodeTo);
					list.add(node);
				}
				dog.putVertex(nodeFrom, list);
			} else {
				dog.addVertex(nodeFrom);
			}
		}
		return dog;
	}

	/**
	 * parser of the type of the vertex
	 */
	private static String parseType(String node) {
		String str = node.trim();
		int lastIndex = str.length() - 1;
		if (str.charAt(0) == '(' && str.charAt(lastIndex) == ')') {
			str = str.substring(1, lastIndex);
		} else {
			throw new IllegalArgumentException("vertex type parsing error!");
		}
		return str.trim();
	}

	private static void debug(String heading, String[] content) {
		System.out.println(heading + "\t length = " + content.length);
		for(int i = 0; i < content.length; i++){
			System.out.println(content[i]);
		}
	}

}
