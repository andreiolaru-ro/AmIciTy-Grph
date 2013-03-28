package testing.nihal;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import testing.nihal.util.graph.Edge;
import testing.nihal.util.graph.Node;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class MyGraph {
	
	private Graph<Node,Edge> g=new SparseMultigraph<Node,Edge>();
	private Layout<Node, Edge> layout = new CircleLayout(g);
	public void Initialize()
	{
		Node n1=new Node("Albert");
		Node n2=new Node("Celia");
		Node n3=new Node("User");
		Node n4=new Node("attend conference");
		Node n5=new Node("London");
		Node n6=new Node("LHR");
		Node n7=new Node("flight");
		Node n8=new Node("CDG");
		Node n9=new Node("Paris");
		Node n10=new Node("airport");
		Node n11=new Node("CNAM");
		Node n12=new Node("AI Conference");
		Edge e1=new Edge(n1,n3,"isa");
		Edge e2=new Edge(n2,n3,"isa");
		Edge e3=new Edge(n1,n2,"knows");
		Edge e4=new Edge(n1,n4,"hasactivity");
		Edge e5=new Edge(n1,n5,"in");
		Edge e6=new Edge(n7,n4,"part of");
		Edge e7=new Edge(n7,n6,"from");
		Edge e8=new Edge(n6,n10,"isa");
		Edge e9=new Edge(n8,n10,"isa");
		Edge e10=new Edge(n8,n9,"in");
		Edge e11=new Edge(n11,n9,"in");
		Edge e12=new Edge(n12,n11,"venue");
		Edge e13=new Edge(n7,n8,"to");
		g.addEdge(e1,n1, n3, EdgeType.DIRECTED); // This method
		g.addEdge(e2,n2, n3, EdgeType.DIRECTED);
		g.addEdge(e3, n1, n2, EdgeType.DIRECTED);
		g.addEdge(e4, n1, n4, EdgeType.DIRECTED); // or we can use
		g.addEdge(e5, n1, n5,EdgeType.DIRECTED); // In a directed graph the
		g.addEdge(e6, n7, n4,EdgeType.DIRECTED); // first node is the source
		g.addEdge(e7, n7, n6,EdgeType.DIRECTED);// and the second
		g.addEdge(e8, n6, n10, EdgeType.DIRECTED); 
		g.addEdge(e9, n8, n10,EdgeType.DIRECTED); 
		g.addEdge(e10, n8, n9,EdgeType.DIRECTED);
		g.addEdge(e11, n11, n9,EdgeType.DIRECTED);
		g.addEdge(e12, n12, n11,EdgeType.DIRECTED); 
		g.addEdge(e13, n7, n8,EdgeType.DIRECTED);
		layout.setSize(new Dimension(600,600));
		BasicVisualizationServer<Node,Edge> vv = new BasicVisualizationServer<Node,Edge>(layout);
		
		Transformer<Node,Shape> vertexSize = new Transformer<Node,Shape>(){
            public Shape transform(Node i){
                Ellipse2D circle = new Ellipse2D.Double(-15,-15,40, 20);
                return new Rectangle(-20,-10,i.getLabel().length()*10,30);
                //if(i==1) return AffineTransform.getScaleInstance(3, 3).createTransformedShape(circle);
                //else if(i==2) return circle;
                //else return new Rectangle(-20, -10, 40, 20);
            }
        };
		
		vv.setPreferredSize(new Dimension(800,600));
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setVertexShapeTransformer(vertexSize);
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		JFrame frame = new JFrame("Simple Graph View");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);
		
	}

}
