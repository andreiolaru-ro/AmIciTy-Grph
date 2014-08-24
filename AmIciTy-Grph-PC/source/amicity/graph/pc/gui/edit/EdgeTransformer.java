package amicity.graph.pc.gui.edit;

import net.xqhs.graphs.graph.Edge;

import org.apache.commons.collections15.Transformer;

public class EdgeTransformer implements Transformer<Edge, String> {

	@Override
	public String transform(Edge arg) {
		return arg.getLabel();
	}

}
