package amicity.graph.pc.jung;

import edu.uci.ics.jung.algorithms.layout.Layout;
import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.matcher.Match;

public class MatchPair {
	public JungGraph graph;
	public JungGraph pattern;
	public Layout<Node, Edge> layout;
	public Match match;
	public MatchPair(JungGraph graph, JungGraph pattern, Match match) {
		this.graph = graph;
		this.pattern = pattern;
		this.match = match;
	}
	
	public String toString() {
		return pattern.getName() + ":" + match.getK();
	}
}
