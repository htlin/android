package org.easycomm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.IOException;


public class DirectedOrderedGraph<T> {

	private Map<T, ListSet<T>> mGraph;

	public DirectedOrderedGraph() {
		mGraph = new HashMap<T, ListSet<T>>();
	}

	public ListSet<T> getOutgoingEdgesOf(T vertex) {

		return mGraph.get(vertex);
	}

	public List<T> getIncomingEdgesOf(T vertex) {
		// TODO Auto-generated method stub
		List<T> parentList = new ArrayList<T>();
		Set<T> keys = mGraph.keySet();
		Iterator<T> iter = keys.iterator();
		while(iter.hasNext()) {
			T v = iter.next();
			ListSet<T> list = getOutgoingEdgesOf(v);
			if(list.contains(vertex)){
				parentList.add(v);
			}
		}

		return parentList;
	}

	public void addVertex(T vertex) {

		mGraph.put(vertex, new ListSet<T>());
	}

	public void addVertex(T vertex, ListSet<T> list) {

		mGraph.put(vertex, list);
	}

	public void addEdge(T parent, T child) {

		if(mGraph.containsKey(parent)){
			ListSet<T> children = getOutgoingEdgesOf(parent);
			children.add(child);
		}
		else {
			// parent not exist !!!
			// to be clarified
		}

	}

	public void removeEdge(T parent, T child) {

		if(mGraph.containsKey(parent)){
			ListSet<T> children = getOutgoingEdgesOf(parent);
			children.remove(child);
		}
		else {
			// parent not exist !!!
			// to be clarified
		}
	}

	public void removeVertex(T vertex) {

		mGraph.remove(vertex);
	}

	public void move(T parent, T source, T target) {
		// TODO Auto-generated method stub
	}

	public int order(){
		return mGraph.size();
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

/*
 *	The context-free grammar of the representation of a DirectedOrderedGraph:
 *
 *	S :- VP ; S | VP
 *	VP :- V : OG
 *	OG :- null | VL
 *	VL := V , VL | V
 *	V :- (   T   )
 *
 *	where	S is a sentence
 *			VP is vertex phrase
 *			OG is a outgoing list
 *			VL is a vertex list
 *			V is a vertex
 *			T is the type of a vertex
 *
 */

	public static DirectedOrderedGraph<String> makeGraph(String graph) {

		DirectedOrderedGraph<String> dog = new DirectedOrderedGraph<String>();
		// split sentence into array of vertex phrase
		String[] vertex_phrase = graph.split(";");

//debug("vertex_phrase", vertex_phrase);

		for(String vp : vertex_phrase){
			// split vertex phrase into vertex and outgoing list
			String[] v_list = vp.trim().split(":");
//debug("v_list", v_list);

			String nodeFrom = parseType(v_list[0]);

			// check whether outgoing list is empty
			if(v_list.length > 1 ) {
				ListSet<String> list = new ListSet<String>();

				// split outgoing list into vertex array
				String[] listNode = v_list[1].trim().split(",");
//debug("listNode", listNode);

				for(String nodeTo : listNode){
					// extract the information
					// and construct into the required type T
					String node = parseType(nodeTo);
					list.add(node);
				}
				dog.addVertex(nodeFrom, list);
			}
			else {
				dog.addVertex(nodeFrom);
			}
		}
		return dog;
	}

	public static DirectedOrderedGraph<String> readGraph(BufferedReader buffer) {

		DirectedOrderedGraph<String> dog = new DirectedOrderedGraph<String>();

		try
        {
        	while(true){
        		String line = buffer.readLine();
        		if(line == null) break;

	        	// split each line into vertex and outgoing list
				String[] v_list = line.trim().split(":");
debug("v_list", v_list);

				String nodeFrom = parseType(v_list[0]);

				// check whether outgoing list is empty
				if(v_list.length > 1 ) {
					ListSet<String> list = new ListSet<String>();

					// split outgoing list into vertex array
					String[] listNode = v_list[1].trim().split(",");
debug("listNode", listNode);

					for(String nodeTo : listNode){
						// extract the information
						// and construct into the required type T
						String node = parseType(nodeTo);
						list.add(node);
					}
					dog.addVertex(nodeFrom, list);
				}
				else {
					dog.addVertex(nodeFrom);
				}
        	}
        	return dog;

        }
        catch (IOException x)
        {
            throw new Error("bad input stream");
        }

	}

	/*
	 *		parser of the type of the vertex
	 *
	 */
	private static String parseType(String node){
		String str = node.trim();
		int lastIndex = str.length()-1;
		if(str.charAt(0) == '(' && str.charAt(lastIndex) == ')'){
			str = str.substring(1, lastIndex);
		}
		else {
			System.out.println("vertex type parsing error!");
		}
		return str.trim();
	}

	private static void debug(String heading, String[] content){
		System.out.println(heading + "\t length = "+content.length);
		for(int i=0; i<content.length; i++){
			System.out.println(content[i]);
		}
	}

}
