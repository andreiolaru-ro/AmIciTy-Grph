/*******************************************************************************
\ * Copyright (C)  Nihal Ablachim and contributors (see AUTHORS).
 * 
 * This file is part of Visualization of Context Graphs.
 * 
 * Visualization of Context Graphs is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * Visualization of Context Graphs is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Visualization of Context Graphs.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package testing.nihal;

import java.awt.Dimension;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;


/**
 * @author 'Nihal Ablachim'
 * This class was meant to display a graph using different types of layouts provided by JUNG.
 * Attention: Tried with a forest layout, didn't work, give up!
 */
public class Layouts {
	private Forest<Integer, String> g = new DelegateForest<Integer, String>();
	//private Layout<Integer, String> layout = new CircleLayout(g);
	//private Layout<Integer, String> layout = new SpringLayout(g);
	private TreeLayout<Integer, String> layout = new TreeLayout<Integer,String>(g);
	public void Initialize()
	{
		for(int i=0;i<=15;i++)
			g.addVertex((Integer) i);
		
		for(int i=1;i<=30;i++)
			g.addEdge(((Integer)i).toString(), (i-1), i);
			
		g.addEdge("89", 15, 7);
		g.addEdge("100", 3, 10);
		
		//layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Integer,String> vv =
		new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		//JFrame frame = new JFrame("Circle Layout");
		/*JFrame frame = new JFrame("Spring Layout");
		//JFrame frame = new JFrame("FRLayout");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);*/

	}
	
}
