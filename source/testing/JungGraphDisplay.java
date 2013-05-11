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

import util.graph.Edge;
import util.graph.Node;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout3d.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

/**
 * @author 'Nihal Ablachim'
 * A class which will take a context graph and will display it using JUNG libraries.
 */
public class JungGraphDisplay implements GraphDisplay {
	private Graph graph;
	//private Layout<Node, Edge> springLayout = (Layout<Node, Edge>) new SpringLayout(graph);
	//BasicVisualizationServer<Node, Edge> bvs =new BasicVisualizationServer<Node, Edge>(springLayout);
	
	/**
	 * Constructs a new object of type JungGraphDisplay
	 */
	public JungGraphDisplay()
	{
		//TODO
	}

	public void setGraph(Graph g)
	 {
		 graph=g;
	 }
	 public void addNode(Node node)
	 {
		 //TODO
	 }
	 public void addEdge(Node fromNode,Node toNode,Edge edge)
	 {
		 //TODO
	 }
	 public void updateGraph()
	 {
		//TODO
	 }
	 public void focusNode(Node node)
	 {
		//TODO
	 }
	 public void setNodeLabel(Node node,String nodeLabel)
	 {
		//TODO
	 }
	 public void setEdgeLabel(Edge edge,String edgeLabel)
	 {
		//TODO 
	 }
	 public void setEdgeColor(Edge edge,Color color)
	 {
		//TODO
	 }
	 public void setNodeColor(Node node,Color color)
	 {
		//TODO
	 }
	 public void setNodeShape(Node node,Shape shape)
	 {
		//TODO
	 }
	 //void setEdgeStroke(Edge edge,Stroke s);
	 public String getNodeLabel(Node node)
	 {
		//TODO
		 return "";
	 }
	 public String getEdgeLabel(Edge e)
	 {
		//TODO
		 return "";
	 }
	 public Color getEdgeColor(Edge e)
	 {
		//TODO
		 Color c=new Color(0);
		 return c;
	 }
	 public Color getNodeColor(Node n)
	 {
		//TODO
		 Color c=new Color(0);
		 return c;
	 }

}
