/* ===========================================================
 * GTNA : Graph-Theoretic Network Analyzer
 * ===========================================================
 *
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors
 *
 * Project Info:  http://www.p2p.tu-darmstadt.de/research/gtna/
 *
 * GTNA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GTNA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * ---------------------------------------
 * BFS.java
 * ---------------------------------------
 * (C) Copyright 2009-2011, by Benjamin Schiller (P2P, TU Darmstadt)
 * and Contributors 
 *
 * Original Author: Nico;
 * Contributors:    -;
 *
 * Changes since 2011-05-17
 * ---------------------------------------
 *
 */
package gtna.transformation.spanningtree;

import java.util.ArrayList;
import java.util.LinkedList;

import gtna.graph.Graph;
import gtna.graph.Node;
import gtna.graph.spanningTree.ParentChild;
import gtna.graph.spanningTree.SpanningTree;
import gtna.transformation.Transformation;
import gtna.transformation.TransformationImpl;

/**
 * @author Nico
 * 
 */
public class BFS extends TransformationImpl implements Transformation {
	public BFS() {
		super("SPANNINGTREE_BFS", new String[] {}, new String[] {});
	}
	
	@Override
	public Graph transform(Graph graph) {
		Node root = graph.getNode(0);
		
		Node tempNodeFromList;
		int[] edges;
		Node[] nodes = graph.getNodes();
		
    	LinkedList<Node> todoList = new LinkedList<Node>();
    	LinkedList<Integer> handledNodes = new LinkedList<Integer>();
    	LinkedList<Integer> linkedNodes = new LinkedList<Integer>();
    	
    	ArrayList<ParentChild> parentChildList = new ArrayList<ParentChild>();
    	
    	todoList.add(root);
    	linkedNodes.add(root.getIndex());
    	while ( !todoList.isEmpty() ) {
    		tempNodeFromList = todoList.pop();
    		if ( handledNodes.contains(tempNodeFromList.getIndex()) ) {
    				/*
    				 * Although the current node was fetched from the 
    				 * todoList, we will continue: this node was already
    				 * processed
    				 */
    			continue;
    		}
    		
    		edges = tempNodeFromList.getOutgoingEdges();
    		for ( int e: edges ) {
    			if (  !linkedNodes.contains(e) ) {
    					/*
    					 * Node e has not been linked yet, so
    					 * add it to the todoList (to handle it soon)
    					 * and add the edge from the current node to e
    					 * to the list of edges 
    					 */
    				todoList.add( nodes[e] );
    				linkedNodes.add(e);
    				
    				parentChildList.add( new ParentChild(tempNodeFromList.getIndex(), e));
    			}
    		}
    		
    		handledNodes.add(tempNodeFromList.getIndex());
    	}
		
    	SpanningTree result = new SpanningTree(graph, parentChildList);
    	
		graph.addProperty("SPANNINGTREE", result);
		return graph;
	}
	
	@Override
	public boolean applicable(Graph g) {
		return true;
	}	
}
