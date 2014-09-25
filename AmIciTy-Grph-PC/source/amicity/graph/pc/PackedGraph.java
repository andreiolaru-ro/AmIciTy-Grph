package amicity.graph.pc;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import amicity.graph.pc.jung.JungGraph;

public class PackedGraph implements Serializable {
	private static final long serialVersionUID = -1472357984289994836L;
	public List<Node> nodes;
	public List<Edge> edges;
	public List<Point2D> points;
	public String name;
	public String description;
	public boolean isPattern = false;
	
	public PackedGraph(JungGraph graph) {
		nodes = new ArrayList<Node>(graph.getVertices());
		edges = new ArrayList<Edge>(graph.getEdges());
		points = new ArrayList<Point2D>();
		for (Node node : nodes) {
			points.add(graph.getLayout().transform(node));
		}
		name = graph.getName();
		description = graph.getDescription();
		isPattern = graph.isPattern();
	}
	
}