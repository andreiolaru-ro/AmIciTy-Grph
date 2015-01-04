package amicity.graph.pc.gui.util;

public class GraphUIEvent
{

	public enum Type {
		SelectedGraphChanged,
		None
	}
	
	// FIXME(catalinb): maybe use WeakRefs
	protected Object source;
	protected Type type;
	protected Object data;
	
	public GraphUIEvent(Object source) {
		this.source = source;
	}
	
	public GraphUIEvent(Object source, Type type, Object data) {
		this.source = source;
		this.type = type;
		this.data = data;
	}
	
	public Object getSource() {
		return source;
	}
	
	public Type getType() {
		return type;
	}
	
	public Object getData() {
		return data;
	}
}
