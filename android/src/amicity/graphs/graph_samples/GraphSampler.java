package amicity.graphs.graph_samples;

import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.graph.SimpleNode;
import amicity.graphs.android.GraphView;
import amicity.graphs.android.common.Dimension;

public class GraphSampler {

	public static GraphView sample1(Dimension size) {
		Graph graph = new SimpleGraph();
		Node v1 = new SimpleNode("v1");
		Node v2 = new SimpleNode("v2");
		graph.addNode(v1); graph.addNode(v2);
		graph.addEdge(new SimpleEdge(v1, v2, "12"));
		Node v3 = new SimpleNode("v3");
		graph.addNode(v3);
		graph.addEdge(new SimpleEdge(v2, v3, "23"));
		v1 = new SimpleNode("v4");
		graph.addNode(v1);
		graph.addEdge(new SimpleEdge(v3, v1, "34"));
		v1 = new SimpleNode("v5");
		graph.addNode(v1);
		graph.addEdge(new SimpleEdge(v3, v1, "35"));
		v1 = new SimpleNode("v6");
		graph.addNode(v1);
		graph.addEdge(new SimpleEdge(v3, v1, "36"));
	
		return new GraphView(graph, size);
	}

}
