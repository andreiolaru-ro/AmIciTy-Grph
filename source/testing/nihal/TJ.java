package testing.nihal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.SparseGraph;
//import edu.uci.ics.jung.graph.Vertex;
//import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class TJ {

	/*public static void main(String[] args) {
		// Graph<V, E> where V is the type of the vertices
		// and E is the type of the edges
		JungforDoc f=new JungforDoc();
		f.initialize();
		Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
		//SimpleGraph<Integer,String>g1=new SparseMultigraph<Integer, String>();
		// Add some vertices. From above we defined these to be type Integer.
		//DirectedGraph<Integer, String> f1 = new DirectedSparseGraph<Integer, String>();
		//Vertex v1 = f1.addVertex(new DirectedSparseVertex());
		//Vertex v1 = g.addVertex(new SparseMultigraph<Integer, String>());
		g.addVertex((Integer) 1);
		g.addVertex((Integer) 2);
		//g.addVertex((Integer) 3);
		//g.removeVertex(1);
		// Add some edges. From above we defined these to be of type String
		// Note that the default is for undirected edges.
		g.addEdge("Edge-A", 1, 2); // Note that Java 1.5 auto-boxes primitives
		//g.addEdge("Edge-B", 2, 3);
		//g.addEdge("Edge-C", 2, 3);
		
		// Let's see what we have. Note the nice output from the
		// SparseMultigraph<V,E> toString() method
		System.out.println("The graph g = " + g.toString());
		// Note that we can use the same nodes and edges in two different
		// graphs.
		/*Graph<String, String> g2 = new SparseMultigraph<String, String>();
		g2.addVertex((String) "attend conference");
		g2.addVertex((String) "User");
		g2.addVertex((String) "Celia");
		g2.addVertex((String)"AI Conference");
		g2.addVertex((String)"CNAM");
		g2.addVertex((String)"taxi ride");
		g2.addVertex((String)"CDG");
		g2.addEdge("isa","Celia", "User",EdgeType.DIRECTED);
		g2.addEdge("has activity","Celia", "attend conference", EdgeType.DIRECTED);
		//g2.addEdge("attend conference", "AI Conference", EdgeType.DIRECTED);
		g2.addEdge("venue", "AI Conference", "CNAM", EdgeType.DIRECTED);
		g2.addEdge("to", "taxi ride", "CNAM", EdgeType.DIRECTED);
		//g2.addEdge("1", "taxi ride", "CDG", EdgeType.DIRECTED);
		g2.addEdge("part of", "taxi ride", "attend conference", EdgeType.DIRECTED);
		//g2.addEdge("Edge-P", 2, 3); // A parallel edge
		System.out.println("The graph g2 = " + g2.toString());

		// The Layout<V, E> is parameterized by the vertex and edge types
		Layout<String, String> layout = new CircleLayout<>(g);
		layout.setSize(new Dimension(600, 600)); // sets the initial size of the
													// space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<String, String>(
				layout);
		vv.setPreferredSize(new Dimension(600, 600)); // Sets the viewing area
														// size
		
		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			public Paint transform(String i) {
			if(i.compareTo("CDG")==0) return Color.GREEN;
			else return Color.RED;
			}
			};
			
			
			
			// Set up a new stroke Transformer for the edges
			float dash[] = {10.0f};
			final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
			BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
			Transformer<String, Stroke> edgeStrokeTransformer =new Transformer<String, Stroke>() {
			public Stroke transform(String s) {
			return edgeStroke;
			}
			};
			
			
			vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
			vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
			
			vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
			vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
			
			//vv.getRenderer().getEdgeLabelRenderer().labelEdge(vv.getRenderContext(), layout ,"isa", "to");
			vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

           // GraphViewerForm = new edu.uci.ics.jung.visualization.GraphZoomScrollPane((VisualizationViewer) vv);
			JFrame frame = new JFrame("Simple Graph View 2");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(vv);
			frame.pack();
			frame.setVisible(true);*/
}
