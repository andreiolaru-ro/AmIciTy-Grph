package amicity.graph.pc.jung;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import amicity.graph.pc.common.Command;

public class AddRemoveCommand<T> implements Command {
	protected JungGraph graph;
	protected T element;
	
	static enum Type {
		Add,
		Remove
	};
	
	protected Type type;
	
	public AddRemoveCommand(JungGraph graph, T element, Type type) {
		this.graph = graph;
		this.element = element;
		this.type = type;
	}
	
	private void doUndo(T element) {
		if (type == Type.Add) {
			remove(element);
		} else {
			add(element);
		}
	}
	
	private void doRedo(T element) {
		if (type == Type.Add) {
			add(element);
		} else {
			remove(element);
		}
	}
	
	private void add(Node node) {
		graph.addVertex(node);
	}
	
	private void add(Edge edge) {
		graph.addEdge(edge, edge.getFrom(), edge.getTo());
	}
	
	private void remove(Node node) {
		graph.removeVertex(node);
	}
	
	private void remove(Edge edge) {
		graph.removeEdge(edge);
	}
	
	public void add(Object obj) {
		// Java generics are so bad.
		System.out.println("UndoManager::add");
		if (obj instanceof Node) {
			add((Node) obj);
		}
		
		if (obj instanceof Edge) {
			add((Edge) obj);
		}
	}
	
	public void remove(Object obj) {
		System.out.println("UndoManager::remove");
		if (obj instanceof Node) {
			remove((Node) obj);
		}
		
		if (obj instanceof Edge) {
			remove((Edge) obj);
		}
	}

	@Override
	public void Undo() {
		doUndo(element);
	}

	@Override
	public void Redo() {
		doRedo(element);
	}
}
