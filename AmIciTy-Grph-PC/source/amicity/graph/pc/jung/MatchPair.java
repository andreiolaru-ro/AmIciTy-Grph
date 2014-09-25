package amicity.graph.pc.jung;

import net.xqhs.graphs.matcher.Match;

public class MatchPair {
	public JungGraph graph;
	public JungGraph pattern;
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
