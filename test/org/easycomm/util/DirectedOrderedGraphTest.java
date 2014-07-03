package org.easycomm.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class DirectedOrderedGraphTest {

	private DirectedOrderedGraph<String> mGraph;
	private DirectedOrderedGraph<String> mOther;

	@Before
	public void setUp() {
		String g = "(root): (fa), (vb); (fa): (vc), (vd); (vb): ; (vc): ; (vd): ";
		mGraph = DirectedOrderedGraph.makeGraph(g);
		mOther = null;
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
		
		assertEquals(CUtil.makeList(), mGraph.getIncomingEdgesOf("root"));
		assertEquals(Arrays.asList("root"), mGraph.getIncomingEdgesOf("fa"));
		assertEquals(Arrays.asList("root"), mGraph.getIncomingEdgesOf("vb"));
		assertEquals(Arrays.asList("fa"), mGraph.getIncomingEdgesOf("vc"));
		assertEquals(Arrays.asList("fa"), mGraph.getIncomingEdgesOf("vd"));
	}
	
	@Test
	public void testAdd() {
		mOther = new DirectedOrderedGraph<String>();
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

}