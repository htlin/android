package org.easycomm.util;

import java.util.HashMap;
import java.util.Map;

public class DirectedOrderedGraph<T> {

	private Map<T, ListSet<T>> mGraph;
	
	public DirectedOrderedGraph() {
		mGraph = new HashMap<T, ListSet<T>>();
	}
	
	public void addVertex(T vertex) {
		// TODO Auto-generated method stub
		
	}

	public void addEdge(T parent, T child) {
		// TODO Auto-generated method stub
		
	}

	public void removeVertex(T vertex) {
		// TODO Auto-generated method stub
		
	}

	public void move(T parent, T source, T target) {
		// TODO Auto-generated method stub
		
	}

}
