/*******************************************************************************
 * Copyright (C)  Nihal Ablachim and contributors (see AUTHORS).
 * 
 * This file is part of Visualization of Context Graphs.
 * 
 * Visualization of Context Graphs is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * Visualization of Context Graphs is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Visualization of Context Graphs.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package testing;

import java.awt.Color;
import java.awt.Shape;

import testing.nihal.util.graph.Edge;
import testing.nihal.util.graph.Node;
import edu.uci.ics.jung.graph.Graph;

public interface GraphDisplay {
	 void setGraph(Graph g);
	 void addNode(Node n);
	 void addEdge(Edge e);
	 void updateGraph();
	 void focusNode(Node n);
	 void setNodeLabel(Node n,String s);
	 void setEdgeLabel(Edge e,String s);
	 void setEdgeColor(Edge e,Color c);
	 void setNodeColor(Node n,Color c);
	 void setNodeShape(Node n,Shape s);
	 //void setEdgeStroke(Edge e,Stroke s);
	 String getNodeLabel(Node n);
	 String getEdgeLabel(Edge e);
	 Color getEdgeColor(Edge e);
	 Color getNodeColor(Node n);
	 //void expandNode();

}
