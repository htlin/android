package org.easycomm.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class DirectedOrderedGraphTest {

	private DirectedOrderedGraph<String> mGraph;
	private DirectedOrderedGraph<String> mOther;

	@Before
	public void setUp() {
		String g = "(root): (fa), (vb); (fa): (vc), (vd); (vb): ; (vc): ; (vd): ";
		mGraph = DirectedOrderedGraph.makeGraph(g);
		mOther = new DirectedOrderedGraph<String>();
	}

	@Test
	public void testOrder() {
		assertEquals(5, mGraph.getOrder());
	}

	@Test
	public void testEquals() {
		String g = "(root): (fa), (vb); (fa): (vc), (vd); (vb):; (vc):; (vd): ";
		mOther = DirectedOrderedGraph.makeGraph(g);
		assertEquals(mGraph, mOther);

		g = "(fa): (vc), (vd); (root): (fa), (vb); (vb):; (vd):; (vc): ";
		mOther = DirectedOrderedGraph.makeGraph(g);
		assertEquals(mGraph, mOther);

		g = "(root): (vb), (fa); (fa): (vc), (vd); (vb):; (vc):; (vd): ";
		mOther = DirectedOrderedGraph.makeGraph(g);
		assertNotEquals(mGraph, mOther);
	}

	@Test
	public void testReadGraph() throws IOException {
		BufferedReader buffer = new BufferedReader(new FileReader("test_data\\graph3.txt"));
		mOther = DirectedOrderedGraph.readGraph(buffer);
		assertEquals(mGraph, mOther);
		buffer.close();
	}
	
	@Test
	public void testGet() {
		assertEquals(Arrays.asList("fa", "vb"), mGraph.getOutgoingEdgesOf("root"));
		assertEquals(Arrays.asList("vc", "vd"), mGraph.getOutgoingEdgesOf("fa"));
		assertEquals(CUtil.makeList(), mGraph.getOutgoingEdgesOf("vb"));
		assertEquals(CUtil.makeList(), mGraph.getOutgoingEdgesOf("vc"));
		assertEquals(CUtil.makeList(), mGraph.getOutgoingEdgesOf("vd"));
		
		assertEquals(CUtil.makeSet(), mGraph.getIncomingEdgesOf("root"));
		assertEquals(CUtil.asSet("root"), mGraph.getIncomingEdgesOf("fa"));
		assertEquals(CUtil.asSet("root"), mGraph.getIncomingEdgesOf("vb"));
		assertEquals(CUtil.asSet("fa"), mGraph.getIncomingEdgesOf("vc"));
		assertEquals(CUtil.asSet("fa"), mGraph.getIncomingEdgesOf("vd"));
	}
	
	@Test
	public void testAdd() {
		mOther.addVertex("root");
		mOther.addVertex("fa");
		mOther.addVertex("vb");
		mOther.addVertex("vc");
		mOther.addVertex("vd");
		mOther.addEdge("root", "fa");
		mOther.addEdge("root", "vb");
		mOther.addEdge("fa", "vc");
		mOther.addEdge("fa", "vd");
		assertEquals(mGraph, mOther);
	}

	@Test
	public void testAddVertexExists() {
		assertTrue(mOther.addVertex("root"));
		assertFalse(mOther.addVertex("root"));
	}
	
	@Test
	public void testAddEdgeExists() {
		assertTrue(mOther.addVertex("va"));
		assertTrue(mOther.addVertex("vb"));
		assertTrue(mOther.addEdge("va", "vb"));
		assertFalse(mOther.addEdge("va", "vb"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddEdgeNotExists() {
		mOther.addEdge("va", "vb");
	}
	
	@Test
	public void testRemoveEdge() {
		assertTrue(mGraph.removeEdge("root", "fa"));
		assertFalse(mGraph.removeEdge("root", "fa"));
		assertFalse(mGraph.removeEdge("ghost", "vb"));
		assertFalse(mGraph.removeEdge("vb", "ghost"));
		String g = "(root): (vb); (fa): (vc), (vd); (vb): ; (vc): ; (vd): ";
		mOther = DirectedOrderedGraph.makeGraph(g);
		assertEquals(mOther, mGraph);
	}
	
	@Test
	public void testRemoveVertex() {
		assertFalse(mGraph.removeVertex("ghost"));
		assertTrue(mGraph.removeVertex("vd"));
		assertTrue(mGraph.removeVertex("vb"));
		String g = "(root): (fa); (fa): (vc); (vc): ";
		mOther = DirectedOrderedGraph.makeGraph(g);
		assertEquals(mOther, mGraph);
	}
	
}