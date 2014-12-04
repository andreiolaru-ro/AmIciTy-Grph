package amicity.graph.pc.jung;

import net.xqhs.graphs.pattern.NodeP;

public class SettableNodeP extends NodeP {
	public SettableNodeP(String string) {
		this.generic = false;
		this.label = string;
	}

	public void setGeneric(boolean flag) {
		this.generic = flag;
	}

}
