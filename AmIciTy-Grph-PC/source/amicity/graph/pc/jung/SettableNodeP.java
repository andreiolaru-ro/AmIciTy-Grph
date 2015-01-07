package amicity.graph.pc.jung;

import net.xqhs.graphs.pattern.NodeP;

public class SettableNodeP extends NodeP {
	public SettableNodeP(String string) {
		if (string.equals("?")) {
			this.generic = true;
		} else {
			this.generic = false;
		}
		this.label = string;
	}

	public void setGeneric(boolean flag) {
		this.generic = flag;
	}
	
	public void setLabelIndex(int index) {
		labelIndex = index;
	}

}
