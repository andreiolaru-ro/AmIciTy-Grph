/*******************************************************************************
 * Copyright (C)  Andrei Olaru and contributors (see AUTHORS).
 * 
 * This file is part of AmIciTy-Grph.
 * 
 * AmIciTy-Grph is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * AmIciTy-Grph is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with AmIciTy-Grph.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package testing.nihal;

import java.awt.Color;

import util.graph.Edge;
import util.graph.Node;
import edu.uci.ics.jung.graph.Graph;

/**
 * @author 'Nihal Ablachim'
 * An interface consisting of a set of methods which will help to display a context graph regardless of the 
     software library used for visualization of graphs.
 */
public interface GraphDisplay {
	 
	/**
	 * Sets the reference to the context graph given as parameter.
	 * @param graph : the context graph to set for display
	 */
	void setGraph(Graph graph); 
	
	 /**
	 * Adds dynamically a node to the context graph and displays the node.
	 * @param node : the node to add to the context graph
	 * 
	 */
	void addNode(Node node);
	
	 /**
	 * Adds dynamically the specified edge between specified nodes to the context graph and displays the edge. 
	 * @param fromNode : the from node
	 * @param toNode : the to node
	 * @param edge : the edge to add to the context graph
	 */
	void addEdge(Node fromNode,Node toNode,Edge edge);
	
	 /**
	 * Updates the graph after changes are occurred in the graph
	 */
	void updateGraph();
	
	 /**
	 * Sets the display view to the neighborhood of the specified node. 
	 * @param node : the node, the neighborhood of which will be displayed
	 */
	void focusNode(Node node);
	
	 /**
	 * Gets the label of the node given as parameter.
	 * @param node : the node for which the label is wanted
	 * @return the label of the node given as parameter
	 */
	String getNodeLabel(Node node);
	
	 /**
	 * @param edge : the edge for which the label is wanted
	 * @return    the label of the edge given as parameter
	 */
	String getEdgeLabel(Edge edge);
	
	 /**
	 * Gets the label of the edge given as parameter.
	 * @param edge : the edge for which the color is wanted
	 * @return the color of the edge given as parameter
	 */
	Color getEdgeColor(Edge edge);
	
	 /**
	 * Gets the color of the node given as parameter.
	 * @param node :  the node for which the color is wanted
	 * @return the color of the node given as parameter
	 */
	Color getNodeColor(Node node);
	 // sets the specified label to the specified node
	 //void setNodeLabel(Node node,String nodeLabel);
	// sets the specified label to the specified edge
	 //void setEdgeLabel(Edge edge,String edgeLabel);
	// sets the specified color to the specified edge
	 //void setEdgeColor(Edge edge,Color color);
	// sets the specified color to the specified node
	 //void setNodeColor(Node node,Color color);
	// sets the specified shape to the specified node
	 //void setNodeShape(Node node,Shape shape);
	// sets the specified shape to the specified edge
	 //void setEdgeStroke(Edge e,Stroke s);

}
