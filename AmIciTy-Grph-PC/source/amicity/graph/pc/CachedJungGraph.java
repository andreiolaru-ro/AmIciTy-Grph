package amicity.graph.pc;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.Serializable;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleGraph;
import amicity.graph.pc.jung.JungGraph;

import java.util.*;

public class CachedJungGraph extends JungGraph {
	File filepath;
	boolean dirty;

	public CachedJungGraph(SimpleGraph simpleGraph, File path, String name,
			boolean isPattern) {
		super(simpleGraph, name, isPattern);
		dirty = true;
	}
	
	public CachedJungGraph(String name, boolean isPattern) {
		super(name, isPattern);
		dirty = true;
	}
	
	public CachedJungGraph(PackedGraph packedGraph) {
		super(packedGraph.name, packedGraph.isPattern);
		for (int i = 0; i < packedGraph.nodes.size(); i++) {
			Node node = packedGraph.nodes.get(i);
			Point2D pos = packedGraph.points.get(i);
			addVertex(node);
			layout.setLocation(node, pos);
		}

		for (Edge edge : packedGraph.edges) {
			addEdge(edge, edge.getFrom(), edge.getTo());
		}
	}

	public void setFilepath(File filepath) {
		this.filepath = filepath;
	}

	public File getFilepath() {
		return filepath;
	}
	public String toString() {
		return "<html><b>" + super.toString() + "</b></html>";
	}
	
	public PackedGraph pack() {
		return new PackedGraph(this);
	}
	

}
