package org.easycomm.util;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.*;

public class DirectedOrderedGraphTest {

	DirectedOrderedGraph<String> myGraph, other;

	public void setUp(){
		String g1 = "(root): (fa), (vb); (fa): (vc), (vd); (vb): ; (vc): ; (vd): ";
		myGraph = DirectedOrderedGraph.makeGraph(g1);
		System.out.println("myGraph order = " +myGraph.order());
	}

	public void test1(){
		String g2 = "(root): (fa), (vb); (fa): (vc), (vd); (vb):; (vc):; (vd): ";
		other = DirectedOrderedGraph.makeGraph(g2);
		System.out.println("compare with g2 = "+myGraph.equals(other));

		String g3 = "(fa): (vc), (vd); (root): (fa), (vb); (vb):; (vd):; (vc): ";
		other = DirectedOrderedGraph.makeGraph(g3);
		System.out.println("compare with g3 = "+myGraph.equals(other));
		try{
			System.out.println("reading graph from file ...");
			BufferedReader buffer = new BufferedReader(new FileReader("graph3.txt"));

			other = DirectedOrderedGraph.readGraph(buffer);
			System.out.println("compare with graph3.txt = "+myGraph.equals(other));
		}
		catch(Exception e){
			System.out.println("Error... "+e.getMessage());
		}

		String g4 = "(root): (vb), (fa); (fa): (vc), (vd); (vb):; (vc):; (vd): ";
		other = DirectedOrderedGraph.makeGraph(g4);
		System.out.println("compare with g4 = "+myGraph.equals(other));
	}


	public void start(){
		setUp();
		test1();
	}



	public static void main(String[] args) {
      DirectedOrderedGraphTest prog = new DirectedOrderedGraphTest();

      prog.start();
   }

}