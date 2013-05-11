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
import java.awt.Shape;

import testing.GraphDisplay;
import util.graph.Edge;
import util.graph.Node;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout3d.SpringLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;

public class JungGraphDisplay implements GraphDisplay {
	private Graph graph;
	private Layout<Node, Edge> springLayout = (Layout<Node, Edge>) new SpringLayout(graph);
	BasicVisualizationServer<Node, Edge> bvs =new BasicVisualizationServer<Node, Edge>(springLayout);
	
	public JungGraphDisplay()
	{
		//TODO
	}
	 public void setGraph(Graph g)
	 {
		 graph=g;
	 }
	 public void addNode(Node n)
	 {
		 //TODO
	 }
	 public void addEdge(Node n1, Node n2, Edge e)
	 {
		 //TODO
	 }
	 public void updateGraph()
	 {
		//TODO
	 }
	 public void focusNode(Node n)
	 {
		//TODO
	 }
	 public void setNodeLabel(Node n,String s)
	 {
		//TODO
	 }
	 public void setEdgeLabel(Edge e,String s)
	 {
		//TODO 
	 }
	 public void setEdgeColor(Edge e,Color c)
	 {
		//TODO
	 }
	 public void setNodeColor(Node n,Color c)
	 {
		//TODO
	 }
	 public void setNodeShape(Node n,Shape s)
	 {
		//TODO
	 }
	 //void setEdgeStroke(Edge e,Stroke s);
	 public String getNodeLabel(Node n)
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
