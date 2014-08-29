package amicity.graph.pc.common;

public class GraphUpdateEvent {
	public static enum Type {
		GraphStructure,
		Name,
		Description
	};
	
	public Type type;
	public Object data;
	
	public GraphUpdateEvent(Type type, Object data) {
		this.type = type;
		this.data = data;
	}
}
