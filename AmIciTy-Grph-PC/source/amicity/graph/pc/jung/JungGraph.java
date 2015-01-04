package amicity.graph.pc.jung;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.pattern.GraphPattern;
import net.xqhs.graphs.pattern.NodeP;
import amicity.graph.pc.common.Command;
import amicity.graph.pc.common.GraphEvent;
import amicity.graph.pc.common.UndoManager;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Graphs;
import edu.uci.ics.jung.graph.util.Pair;

public class JungGraph extends Observable implements Graph<Node, Edge> {
	// FIXME name conflict with net.xqhs.Graph
	private String name;
	private String description;
	protected boolean isPattern;
	private boolean needsLayout;

	protected Graph<Node, Edge> graph;
	protected Layout<Node, Edge> layout;
	
	protected UndoManager undoManager;
	
	public JungGraph(String name, boolean isPattern) {
		this.setName(name);
		this.graph = Graphs.<Node,Edge>synchronizedDirectedGraph(new DirectedSparseMultigraph<Node, Edge>());
		layout = new StaticLayout<Node, Edge>(this);
		this.isPattern = isPattern;
		undoManager = new UndoManager();
	}
	
	public JungGraph(SimpleGraph simpleGraph, String name, boolean isPattern) {
		this(name, isPattern);

		Map<Node, Node> transformMap = new HashMap<Node, Node>();
		
		// copy the graph
		for (Node node : simpleGraph.getNodes()) {
			SettableNodeP nodeP = new SettableNodeP(node.getLabel());
			transformMap.put(node, nodeP);
			graph.addVertex(nodeP);
		}
		
		for (Edge edge : simpleGraph.getEdges()) {
			Edge updatedEdge = new SimpleEdge(transformMap.get(edge.getFrom()),
											  transformMap.get(edge.getTo()),
											  edge.getLabel());
			graph.addEdge(updatedEdge, updatedEdge.getFrom(), updatedEdge.getTo());
		}
	}

	public net.xqhs.graphs.graph.Graph asSimpleGraph() {
		SimpleGraph simpleGraph = (SimpleGraph) new SimpleGraph().setUnitName(getName());
		for (Node node : graph.getVertices()) {
			simpleGraph.addNode(node);
		}
		for (Edge edge : graph.getEdges()) {
			try {
				simpleGraph.addEdge(edge);
			} catch (Exception e) {
				System.out.println(edge.getFrom());
				System.out.println(edge.getLabel());
				System.out.println(edge.getTo());
				System.out.println(edge.getClass());
				e.printStackTrace();
			}
		}
		return simpleGraph;
	}
	
	public GraphPattern asGraphPattern() {
		GraphPattern graphPattern = (GraphPattern) new GraphPattern().setUnitName(getName());
		for (Node node : graph.getVertices()) {
			graphPattern.addNode(node);
		}
		for (Edge edge : graph.getEdges()) {
			try {
				graphPattern.addEdge(edge);
			} catch (Exception e) {
				System.out.println(edge.getFrom());
				System.out.println(edge.getLabel());
				System.out.println(edge.getTo());
				System.out.println(edge.getClass());
				e.printStackTrace();
			}
		}
		return graphPattern;
	}
	
	public GraphPattern asGraphPattern3() {
		GraphPattern graphPattern = (GraphPattern) new GraphPattern().setUnitName(getName());
		int count = 1;
		Map<Node, Node> nodeMap = new HashMap<Node, Node>();
		
		for (Node node : graph.getVertices()) {
			NodeP nodeP;
			if (node.getLabel().equals("?")) {
				nodeP = new NodeP(count++); 
			} else {
				nodeP = new NodeP(node.getLabel());
			}
			nodeMap.put(node, nodeP);
			graphPattern.addNode(nodeP);
		}
		
		for (Edge edge : graph.getEdges()) {
			Node fromNode = nodeMap.get(edge.getFrom());
			Node toNode = nodeMap.get(edge.getTo());
			graphPattern.addEdge(new SimpleEdge(fromNode, toNode, edge.getLabel()));
			//graphPattern.addEdge(edge);
		}

		return graphPattern;
	}
	
	public Layout<Node, Edge> getLayout() {
		return layout;
	}

	public void setLayout(Layout<Node, Edge> layout) {
		this.layout = layout;
	}
	
	public void undo() {
		System.out.println("undo graph");
		if (undoManager.Undo()) {
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
		}
	}
	
	public void redo() {
		if (undoManager.Redo()) {
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
		}
	}
	
	public boolean addVertexWithHistory(Node node) {
		Command command = new AddRemoveCommand<Node>(this, node, AddRemoveCommand.Type.Add);
		
		if (graph.addVertex(node)) {
			undoManager.addCommand(command);
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
			return true;
		}

		return false;
	}
	
	public void dirty() {
		setChanged();
		notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
	}
	
	public boolean removeVertexWithHistory(Node node) {
		Command command = new AddRemoveCommand<Node>(this, node, AddRemoveCommand.Type.Remove);
		
		if (graph.removeVertex(node)) {
			undoManager.addCommand(command);
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
			return true;
		}
		
		return false;
	}

	public boolean addEdgeWithHistory(Edge edge) {
		Command command = new AddRemoveCommand<Edge>(this, edge, AddRemoveCommand.Type.Add);
		
		if (graph.addEdge(edge, edge.getFrom(), edge.getTo())) {
			undoManager.addCommand(command);
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
			return true;
		}
		
		return false;
	}
	
	public boolean removeEdgeWithHistory(Edge edge) {
		Command command = new AddRemoveCommand<Edge>(this, edge, AddRemoveCommand.Type.Remove);
		
		if (graph.removeEdge(edge)) {
			undoManager.addCommand(command);
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
			return true;
		}
		
		return false;
	}
		
	public boolean setLabelWithHistory(Node node, String label) {
		SettableNodeP nodeP = (SettableNodeP) node;
		Command command;
		if (!node.getLabel().equals(label)) {
			if (isPattern) {
				if (label.equals("?")) {
					nodeP.setGeneric(true);
				} else if (node.getLabel().equals("?")) {
					nodeP.setGeneric(false);
				}
			}
			node.setLabel(label);

			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
			return true;
		}
		return false;
	}
	
	public boolean setLabelWithHistory(Edge edge, String label) {
		Command command;
		if (!edge.getLabel().equals(label)) {
			edge.setLabel(label);

			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
			return true;
		}
		return false;
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
	public boolean addVertex(Node node) {
		return graph.addVertex(node);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (!name.equals(this.name)) {
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.Name, name));
		}
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString() {
		return getName();
	}
	
	public boolean isPattern() {
		return isPattern;
	}

	public boolean needsLayout() {
		return needsLayout;
	}

	public void setNeedsLayout(boolean needsLayout) {
		this.needsLayout = needsLayout;
	}
}
