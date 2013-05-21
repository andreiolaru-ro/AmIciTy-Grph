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
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.ChainedTransformer;
import org.apache.commons.collections15.functors.ConstantTransformer;


import util.graph.Edge;
import util.graph.Node;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.CachingLayout;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author 'Nihal Ablachim'
 * A class which will take a context graph and will display it using JUNG libraries.
 */
public class JungGraphDisplay implements GraphDisplay {
	public util.graph.ContextGraph contextGraph;
	public edu.uci.ics.jung.graph.Graph<Node, Edge> jungGraph;
	private Layout<Node, Edge> layout;
	private VisualizationViewer<Node, Edge> bvs;
	GraphZoomScrollPane panel = null;
	ScalingControl scaler = new CrossoverScalingControl();
	DefaultModalGraphMouse graphMouse = null;
	JComboBox modeBox = null;
	
	public void zoomIn() {
	    setZoom(1);
	}

	public void zoomOut() {
	    setZoom(-1);
	}

	private void setZoom(int amount) {
	    scaler.scale(bvs, amount > 0 ? 1.1f : 1 / 1.1f, bvs.getCenter());
	}
	
	/**
	 * Constructs a new object of type JungGraphDisplay
	 */
	public JungGraphDisplay()
	{
	    //TODO
	}
	@Override
	public void setGraph(util.graph.ContextGraph graph)
	{
		this.contextGraph=graph;

	}
	
	public void createJungGraph()
	{
		jungGraph=new SparseMultigraph<Node,Edge>();
		
		for(Node n : contextGraph.getNodes())
			jungGraph.addVertex(n);

		for(Edge e : contextGraph.getEdges())
			jungGraph.addEdge(e,e.getFrom(),e.getTo(),EdgeType.DIRECTED);
	}
	
	public void setLayout()
	{
		layout = new CircleLayout<Node,Edge>(jungGraph);
		bvs=new VisualizationViewer<Node, Edge>(layout); 
		bvs.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		bvs.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		bvs.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.YELLOW));
		bvs.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		bvs.getRenderContext().setArrowFillPaintTransformer(new ConstantTransformer(Color.LIGHT_GRAY));
		//bvs.getRenderContext().setEdgeFontTransformer(new ConstantTransformer(Color.RED));
		panel = new GraphZoomScrollPane(bvs);
	   //add(panel);

	    graphMouse = new DefaultModalGraphMouse();

	    (bvs).setGraphMouse(graphMouse);

	    modeBox = graphMouse.getModeComboBox();
	    modeBox.addItemListener(graphMouse.getModeListener());
	   // graphMouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
	    
	}
	
	public void setBackgroundColor(Color color)
	{
		bvs.setBackground(color);
	}
	
	public void setLayoutSize(int x,int y)
	{
		Dimension dimension=new Dimension(x,y);
		layout.setSize(dimension);
		bvs.setPreferredSize(dimension);
	}
	public void createFrame(String frameTitle)
	{
		JFrame frame = new JFrame(frameTitle);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(bvs);
		frame.pack();
		frame.setVisible(true);
	}
	 public void addNode(Node node)
	 {
		 contextGraph.addNode(node);
		 jungGraph.addVertex(node);
	 }
	 public void addEdge(Node fromNode,Node toNode,Edge edge)
	 {
		 contextGraph.addEdge(edge);
		 jungGraph.addEdge(edge, fromNode, toNode,EdgeType.DIRECTED);	
	 }
	 @Override
	 public void remove(Node node)
	 {
		contextGraph.removeNode(node);
		jungGraph.removeVertex(node);		
	 }
     @Override
	 public void remove(Edge edge) 
     {
		contextGraph.removeEdge(edge);
		jungGraph.removeEdge(edge);
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
	 public void setNodeLabelPosition(Node n,Position position)
	 {
		 bvs.getRenderer().getVertexLabelRenderer().setPosition(position);
	 }
	 public void setEdgeLabel(Edge edge,String edgeLabel)
	 {
		//TODO 
	 }
	 public void setEdgesColor(final Color color)
	 {
		Transformer<Edge,Paint> edgePaint=new Transformer<Edge,Paint>()
				{
					public Paint transform(Edge e)
					{
						return color;
					}
				};
		bvs.getRenderContext().setEdgeFillPaintTransformer(edgePaint);
	 }
	 public void setNodesColor(final Color color)
	 {
		 Transformer<Node,Paint> vertexPaint = new Transformer<Node,Paint>() 
				 {
					public Paint transform(Node n) {
								return color;
				}
				};
		 bvs.getRenderContext().setVertexFillPaintTransformer(vertexPaint); 
			
	 }
	 public void setNodeShape(Node node,Shape shape)
	 {
		 Transformer<Node,Shape> vertexSize = new Transformer<Node,Shape>(){
	            public Shape transform(Node i){
	                //Ellipse2D circle = new Ellipse2D.Double(-15,-15,40, 20);
	                return new Rectangle(-20,-10,i.getLabel().length()*10,30);
	                //if(i==1) return AffineTransform.getScaleInstance(3, 3).createTransformedShape(circle);
	                //else if(i==2) return circle;
	                //else return new Rectangle(-20, -10, 40, 20);
	            }
	        };
	      bvs.getRenderContext().setVertexShapeTransformer(vertexSize);
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
