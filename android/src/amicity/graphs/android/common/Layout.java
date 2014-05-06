package amicity.graphs.android.common;

/*
* Copyright (c) 2003, the JUNG Project and the Regents of the University 
* of California
* All rights reserved.
*
* This software is open-source under the BSD license; see either
* "license.txt" or
* http://jung.sourceforge.net/license.txt for a description.
*/


import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.Node;

import org.apache.commons.collections15.Transformer;

public interface Layout extends Transformer<Node, Point2D> {
	void initialize();
	
	/**
	 * provides initial locations for all vertices.
	 * @param initializer
	 */
	void setInitializer(Transformer<Node, Point2D> initializer);
    
	/**
	 * setter for graph
	 * @param graph
	 */
    void setGraph(Graph graph);

	/**
	 * Returns the full graph (the one that was passed in at 
	 * construction time) that this Layout refers to.
	 * 
	 */
	Graph getGraph();
	
	/**
	 * 
	 *
	 */
	void reset();
	
	/**
	 * @param d
	 */
	void setSize(Dimension d);
	
	/**
	 * Returns the current size of the visualization's space.
	 */
	Dimension getSize();


	/**
	 * Sets a flag which fixes this vertex in place.
     * 
	 * @param v	vertex
	 */
	void lock(Node v, boolean state);

    /**
     * Returns <code>true</code> if the position of vertex <code>v</code>
     * is locked.
     */
    boolean isLocked(Node v);

    /**
     * set the location of a vertex
     * @param v
     * @param location
     */
	void setLocation(Node v, Point2D location);
}
