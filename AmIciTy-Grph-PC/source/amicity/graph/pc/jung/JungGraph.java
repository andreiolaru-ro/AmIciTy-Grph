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
	public enum LayoutType {
		FRLayout,
		SpringLayout
	}
	
	// FIXME name conflict with net.xqhs.Graph
	private String name;
	private String description;
	protected boolean isPattern;

	protected Graph<Node, Edge> graph;
	protected Layout<Node, Edge> layout;
	
	// TODO: move undo manager to the editor code.
	protected UndoManager undoManager;
	
	protected boolean trackHistory;
	
	public JungGraph(String name, boolean isPattern) {
		this.setName(name);
		this.graph = Graphs.<Node,Edge>synchronizedDirectedGraph(new DirectedSparseMultigraph<Node, Edge>());
		layout = new StaticLayout<Node, Edge>(this);
		this.isPattern = isPattern;
		undoManager = new UndoManager();
		
		trackHistory = true;
	}
	
	public JungGraph(GraphPattern simpleGraph, String name, boolean isPattern) {
		this(name, isPattern);

		// This whole mess is needed to enforce that generic nodes have a unique
		// id. TODO: move this outside the JungGraph type.
		
		Map<Node, Node> transformMap = new HashMap<Node, Node>();
		
		// copy the graph
		for (Node node : simpleGraph.getNodes()) {

			SettableNodeP nodeP = new SettableNodeP(node.getLabel());
			if (node.getLabel().equals("?")) {
				if (node instanceof NodeP) {
					NodeP asNodeP = (NodeP)node;
					nodeP.setLabelIndex(asNodeP.genericIndex());
				} else {
					System.err.println("ERROR: node is not generic.");
				}
			}
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
	
	public JungGraph(SimpleGraph simpleGraph) {
		this("default", false);
		for (Node node : simpleGraph.getNodes()) {
			graph.addVertex(node);
		}
		for (Edge edge : simpleGraph.getEdges()) {
			graph.addEdge(edge, edge.getFrom(), edge.getTo());
		}
	}
	
	// TODO(catalinb): can JungGraph just implement net.xqhs.graphs.graph.Graph?
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
	
	public Layout<Node, Edge> getLayout() {
		return layout;
	}
	
	public void setLayout(Layout<Node, Edge> layout) {
		this.layout = layout;
	}
	
	public void undo() {
		undoManager.Undo();
	}
	
	public void redo() {
		undoManager.Redo();
	}
	
	public boolean addVertexWithHistory(Node node) {		
		boolean result = graph.addVertex(node);
		if (result) {
			Command command = new AddRemoveCommand<Node>(this, node, AddRemoveCommand.Type.Add);
			undoManager.addCommand(command);
		}

		return result;
	}
	
	public void dirty(GraphEvent.Type type) {
		setChanged();
		notifyObservers(new GraphEvent(type, null));
	}
	
	public boolean removeVertexWithHistory(Node node) {
		boolean result = graph.removeVertex(node);
		if (graph.removeVertex(node)) {
			Command command = new AddRemoveCommand<Node>(this, node, AddRemoveCommand.Type.Remove);
			undoManager.addCommand(command);
		}
		
		return result;
	}

	public boolean addEdgeWithHistory(Edge edge) {
		boolean result = graph.addEdge(edge, edge.getFrom(), edge.getTo());
		if (result) {
			Command command = new AddRemoveCommand<Edge>(this, edge, AddRemoveCommand.Type.Add);
			undoManager.addCommand(command);
		}
		
		return result;
	}
	
	public boolean removeEdgeWithHistory(Edge edge) {
		boolean result = graph.removeEdge(edge);
		if (result) {
			Command command = new AddRemoveCommand<Edge>(this, edge, AddRemoveCommand.Type.Remove);
			undoManager.addCommand(command);
		}
		
		return result;
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

			dirty(GraphEvent.Type.GraphStructure);
			return true;
		}
		return false;
	}
	
	public boolean setLabelWithHistory(Edge edge, String label) {
		Command command;
		if (!edge.getLabel().equals(label)) {
			edge.setLabel(label);

			dirty(GraphEvent.Type.GraphStructure);
			return true;
		}
		return false;
	}

	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1) {
		boolean result = graph.addEdge(arg0, arg1);
		
		if (result) {
			dirty(GraphEvent.Type.GraphStructure);
		}
		return result;
	}

	@Override
	public boolean addEdge(Edge arg0, Collection<? extends Node> arg1,
			EdgeType arg2) {
		boolean result = graph.addEdge(arg0, arg1, arg2);
		
		if (result) {
			dirty(GraphEvent.Type.GraphStructure);
		}
		
		return result;
	}

	@Override
	public boolean addVertex(Node node) {		
		boolean result = graph.addVertex(node);
		if (result) {
			dirty(GraphEvent.Type.GraphStructure);
		}
		
		return result;
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
		boolean result = graph.removeEdge(arg0);
		if (result) {
			dirty(GraphEvent.Type.GraphStructure);
		}
		
		return result;
	}

	@Override
	public boolean removeVertex(Node arg0) {
		boolean result = graph.removeVertex(arg0);
		if (result) {
			setChanged();
			notifyObservers(new GraphEvent(GraphEvent.Type.GraphStructure, null));
		}
		return result;
	}

	@Override
	public boolean addEdge(Edge e, Node v1, Node v2) {
		boolean result = graph.addEdge(e, v1, v2);
		if (result) {
			dirty(GraphEvent.Type.GraphStructure);
		}
		return result;
	}

	@Override
	public boolean addEdge(Edge e, Node v1, Node v2, EdgeType edgeType) {
		throw new RuntimeException("Not supported");
		//return graph.addEdge(e, v1, v2, edgeType);
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
	
	@Override
	public String toString() {
		return getName();
	}
	
	public boolean isPattern() {
		return isPattern;
	}
}
