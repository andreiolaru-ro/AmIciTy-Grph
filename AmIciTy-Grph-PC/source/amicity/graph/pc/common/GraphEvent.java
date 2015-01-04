package amicity.graph.pc.common;

public class GraphEvent {
	public static enum Type {
		GraphStructure,
		Name,
		Description
	}
	
	public Type type;
	public Object data;
	
	public GraphEvent(Type type, Object data) {
		this.type = type;
		this.data = data;
	}
}
