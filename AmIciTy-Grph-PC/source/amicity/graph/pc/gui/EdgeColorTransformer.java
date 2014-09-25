package amicity.graph.pc.gui;


import java.awt.Color;
import java.awt.Paint;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;

import org.apache.commons.collections15.Transformer;

import amicity.graph.pc.jung.MatchPair;

public class EdgeColorTransformer implements Transformer<Edge, Paint> {

	private MatchPair pair;

	public EdgeColorTransformer(MatchPair pair) {
		this.pair = pair;
	}
	
	@Override
	public Paint transform(Edge edge) {
		if (pair.match.getSolvedPart().contains(edge)) {
			return Color.BLACK;
		}
		return Color.orange;
	}
}
