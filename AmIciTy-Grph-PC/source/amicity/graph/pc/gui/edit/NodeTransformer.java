package amicity.graph.pc.gui.edit;

import net.xqhs.graphs.graph.Node;

import org.apache.commons.collections15.Transformer;

public class NodeTransformer implements Transformer<Node, String> {

	@Override
	public String transform(Node arg) {
		return arg.getLabel();
	}
}
