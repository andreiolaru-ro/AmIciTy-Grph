package amicity.graph.pc;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleNode;
import net.xqhs.graphs.pattern.GraphPattern;
import net.xqhs.graphs.representation.text.TextGraphRepresentation;

public class GraphLibrary implements Serializable {
	public List<String> graphs;
	public List<String> patterns;
	
	public GraphLibrary() {
		graphs = new ArrayList<String>();
		patterns = new ArrayList<String>();
	}
	
	public GraphLibrary(FileInputStream in) throws Exception {
		this();
		GraphPattern p = new GraphPattern();
		TextGraphRepresentation g = new TextGraphRepresentation(p);
		g.readRepresentation(in);
		
		for (Edge edge : p.getEdges()) {
			if (edge.getFrom().getLabel().equals("graphs")) {
				graphs.add(edge.getTo().getLabel());
			} else if (edge.getFrom().getLabel().equals("patterns")) {
				patterns.add(edge.getTo().getLabel());
			} else {
				throw new Exception("Invalid library graph");
			}
		}
	}
	
	public String toString() {
		GraphPattern p = new GraphPattern();
		Node graphsNode = new SimpleNode("graphs");
		Node patternsNode = new SimpleNode("patterns");
		p.addNode(graphsNode);
		p.addNode(patternsNode);
		
		for (String grph : graphs) {
			Node g = new SimpleNode(grph);
			p.addNode(g);
			p.addEdge(new SimpleEdge(graphsNode, g, "-"));
		}
		
		for (String ptrn : patterns) {
			Node g = new SimpleNode(ptrn);
			p.addNode(g);
			p.addEdge(new SimpleEdge(patternsNode, g, "-"));
		}
		
		TextGraphRepresentation repr = new TextGraphRepresentation(p);
		repr.update();
		return repr.displayRepresentation();
	}
}
