package amicity.graph.pc.jung;

import java.util.Collection;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleGraph;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Graphs;
import edu.uci.ics.jung.graph.util.Pair;

public class JungGraph implements Graph<Node, Edge> {
	// fix name conflict with net.xqhs.Graph
	Graph<Node, Edge> graph;
	Layout<Node, Edge> layout;

	public static JungGraph createJungGraph() {
		return new JungGraph(Graphs.<Node,Edge>synchronizedDirectedGraph(new DirectedSparseMultigraph<Node, Edge>()));
	}
	
	public JungGraph(Graph<Node, Edge> graph) {
		this.graph = graph;
		layout = new StaticLayout<Node, Edge>(graph);
	}
	
	public net.xqhs.graphs.graph.Graph asSimpleGraph() {
		SimpleGraph simpleGraph = new SimpleGraph();
		for (Node node : graph.getVertices()) {
			simpleGraph.addNode(node);
		}
		for (Edge edge : graph.getEdges()) {
			simpleGraph.addEdge(edge);
		}
		return simpleGraph;
	}
	
	public Layout<Node, Edge> getLayout() {
		return layout;
	}
	public void setLayout(Layout<Node, Edge> layout) {
		this.layout = layout;
	}

	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1) {
		return graph.addEdge(arg0, arg1);
	}

	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1,
			EdgeType arg2) {
		return graph.addEdge(arg0, arg1, arg2);
	}

	@Override
	public boolean addVertex(Node arg0) {
		return graph.addVertex(arg0);
	}

	@Override
	public boolean containsEdge(Edge arg0) {
		return graph.containsEdge(arg0);
	}

	@Override
	public boolean containsVertex(Node arg0) {
		return graph.containsVertex(arg0);
	}

	@Override
	public int degree(Node arg0) {
		return graph.degree(arg0);
	}

	@Override
	public Edge findEdge(Node arg0, Node arg1) {
		return graph.findEdge(arg0, arg1);
	}

	@Override
	public Collection<Edge> findEdgeSet(Node arg0, Node arg1) {
		return graph.findEdgeSet(arg0, arg1);
	}

	@Override
	public EdgeType getDefaultEdgeType() {
		return graph.getDefaultEdgeType();
	}

	@Override
	public int getEdgeCount() {
		return graph.getEdgeCount();
	}

	@Override
	public int getEdgeCount(EdgeType arg0) {
		return graph.getEdgeCount(arg0);
	}

	@Override
	public EdgeType getEdgeType(Edge arg0) {
		return graph.getEdgeType(arg0);
	}

	@Override
	public Collection<Edge> getEdges() {
		return graph.getEdges();
	}

	@Override
	public Collection<Edge> getEdges(EdgeType arg0) {
		return graph.getEdges(arg0);
	}

	@Override
	public int getIncidentCount(Edge arg0) {
		return graph.getIncidentCount(arg0);
	}

	@Override
	public Collection<Edge> getIncidentEdges(Node arg0) {
		return graph.getIncidentEdges(arg0);
	}

	@Override
	public Collection<Node> getIncidentVertices(Edge arg0) {
		return graph.getIncidentVertices(arg0);
	}

	@Override
	public int getNeighborCount(Node arg0) {
		return graph.getNeighborCount(arg0);
	}

	@Override
	public Collection<Node> getNeighbors(Node arg0) {
		return graph.getNeighbors(arg0);
	}

	@Override
	public int getVertexCount() {
		return graph.getVertexCount();
	}

	@Override
	public Collection<Node> getVertices() {
		return graph.getVertices();
	}

	@Override
	public boolean isIncident(Node arg0, Edge arg1) {
		return graph.isIncident(arg0, arg1);
	}

	@Override
	public boolean isNeighbor(Node arg0, Node arg1) {
		return graph.isNeighbor(arg0, arg1);
	}

	@Override
	public boolean removeEdge(Edge arg0) {
		return graph.removeEdge(arg0);
	}

	@Override
	public boolean removeVertex(Node arg0) {
		return graph.removeVertex(arg0);
	}

	@Override
	public boolean addEdge(Edge e, Node v1, Node v2) {
		return graph.addEdge(e, v1, v2);
	}

	@Override
	public boolean addEdge(Edge e, Node v1, Node v2, EdgeType edgeType) {
		return graph.addEdge(e, v1, v2, edgeType);
	}

	@Override
	public Node getDest(Edge directed_edge) {
		return graph.getDest(directed_edge);
	}

	@Override
	public Pair<Node> getEndpoints(Edge edge) {
		return graph.getEndpoints(edge);
	}

	@Override
	public Collection<Edge> getInEdges(Node vertex) {
		return graph.getInEdges(vertex);
	}

	@Override
	public Node getOpposite(Node vertex, Edge edge) {
		return graph.getOpposite(vertex, edge);
	}

	@Override
	public Collection<Edge> getOutEdges(Node vertex) {
		return graph.getOutEdges(vertex);
	}

	@Override
	public int getPredecessorCount(Node vertex) {
		return graph.getPredecessorCount(vertex);
	}

	@Override
	public Collection<Node> getPredecessors(Node vertex) {
		return graph.getPredecessors(vertex);
	}

	@Override
	public Node getSource(Edge directed_edge) {
		return graph.getSource(directed_edge);
	}

	@Override
	public int getSuccessorCount(Node vertex) {
		return graph.getSuccessorCount(vertex);
	}

	@Override
	public Collection<Node> getSuccessors(Node vertex) {
		return graph.getSuccessors(vertex);
	}

	@Override
	public int inDegree(Node vertex) {
		return graph.inDegree(vertex);
	}

	@Override
	public boolean isDest(Node vertex, Edge edge) {
		return graph.isDest(vertex, edge);
	}

	@Override
	public boolean isPredecessor(Node v1, Node v2) {
		return graph.isPredecessor(v1, v2);
	}

	@Override
	public boolean isSource(Node vertex, Edge edge) {
		return graph.isSource(vertex, edge);
	}

	@Override
	public boolean isSuccessor(Node v1, Node v2) {
		return graph.isSuccessor(v1, v2);
	}

	@Override
	public int outDegree(Node vertex) {
		return graph.outDegree(vertex);
	}
}
