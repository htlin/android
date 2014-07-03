package org.easycomm.util;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

}