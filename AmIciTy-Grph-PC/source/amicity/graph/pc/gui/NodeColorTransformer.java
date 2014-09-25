package amicity.graph.pc.gui;

import java.awt.Color;
import java.awt.Paint;

import net.xqhs.graphs.graph.Node;

import org.apache.commons.collections15.Transformer;

import amicity.graph.pc.jung.MatchPair;

public class NodeColorTransformer implements Transformer<Node, Paint> {

	private MatchPair pair;

	public NodeColorTransformer(MatchPair pair) {
		this.pair = pair;
	}
	
	@Override
	public Paint transform(Node node) {
		if (pair.match.getSolvedPart().contains(node)) {
			return Color.RED;
		}
		return Color.orange;
	}
}
