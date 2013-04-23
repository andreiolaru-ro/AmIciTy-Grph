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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class JungforDoc {

	private Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
	//private Layout<Integer, String> layout = new CircleLayout(g);
	private Layout<Integer, String> layout = new CircleLayout(g);
	public void initialize()
	{
		g.addVertex((Integer) 1);
		g.addVertex((Integer) 2);
		g.addVertex((Integer) 3);
		g.addVertex((Integer) 4);
		g.addEdge("Edge-A", 1, 2,EdgeType.DIRECTED); 
		g.addEdge("Edge-B", 2, 3);
		g.addEdge("Edge-C", 2, 3,EdgeType.DIRECTED);
		//DijkstraShortestPath<Integer,String> alg = new DijkstraShortestPath(g);
		
		//System.out.println("The shortest unweighted path from" + 1 +
		//" to " + 3 + " is:");
		//System.out.println((alg.getPath(1, 3)).toString());
		
		layout.setSize(new Dimension(600,500)); 

		VisualizationViewer<Integer,String> vv =
		new VisualizationViewer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(600,500)); 
		vv.setBackground(Color.white);
		
		Transformer<Integer,Paint> vertexPaint = new Transformer<Integer,Paint>() {
			public Paint transform(Integer i) {
			if(i%2==0)return Color.CYAN;
			else return Color.RED;
			}
			};
			
			Transformer<String,Paint> edgePaint = new Transformer<String,Paint>() {
				public Paint transform(String i) {
				return Color.MAGENTA;
				}
				};
		
			float dash[] = {10.0f};
			final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
			Transformer<String, Stroke> edgeStrokeTransformer =
			new Transformer<String, Stroke>() {
			public Stroke transform(String s) {
			return edgeStroke;
			}
			};
		
			Transformer<Integer,Shape> vertexSize = new Transformer<Integer,Shape>(){
	            public Shape transform(Integer i){
	                Ellipse2D circle = new Ellipse2D.Double(-15,-15,40, 20);
	                // in this case, the vertex is twice as large
	                if(i==1) return AffineTransform.getScaleInstance(3, 3).createTransformedShape(circle);
	                else if(i==2) return circle;
	                else return new Rectangle(-20, -10, 40, 20);
	            }
	        };
			vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
			vv.getRenderContext().setVertexShapeTransformer(vertexSize);
			vv.getRenderContext().setEdgeFillPaintTransformer(edgePaint);
			vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
			vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
			vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
			vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
			
			PluggableGraphMouse gm = new PluggableGraphMouse();
			gm.add(new TranslatingGraphMousePlugin(MouseEvent.BUTTON1_MASK));
			CrossoverScalingControl cs=new CrossoverScalingControl();
			gm.add(new ScalingGraphMousePlugin(cs, 0, 1.1f, 0.9f));
			
			//DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
			//gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
			//vv.addKeyListener(gm.getModeKeyListener());
			
			//EditingModalGraphMouse gm =
			//		new EditingModalGraphMouse(vv.getRenderContext(),     
				//	g.,g.getEdges().);
			vv.setGraphMouse(gm);
			
			/*
			JFrame frame = new JFrame("Interactive Graph View 1");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(vv);
			frame.pack();
			frame.setVisible(true);*/
	}	
}
