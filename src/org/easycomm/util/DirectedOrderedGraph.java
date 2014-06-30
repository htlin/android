package org.easycomm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectedOrderedGraph<T> {

	private Map<T, ListSet<T>> mGraph;
	
	public DirectedOrderedGraph() {
		mGraph = new HashMap<T, ListSet<T>>();
	}
	
	public List<T> getOutgoingEdgesOf(T vertex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<T> getIncomingEdgesOf(T vertex) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void addVertex(T vertex) {
		// TODO Auto-generated method stub
	}

	public void addEdge(T parent, T child) {
		// TODO Auto-generated method stub
	}
	
	public void removeEdge(T parent, T child) {
		// TODO Auto-generated method stub
	}

	public void removeVertex(T vertex) {
		// TODO Auto-generated method stub
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
	
}
