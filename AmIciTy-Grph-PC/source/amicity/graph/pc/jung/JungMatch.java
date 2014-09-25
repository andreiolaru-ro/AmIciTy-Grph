package amicity.graph.pc.jung;

import java.util.ArrayList;
import java.util.List;

import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.matcher.Match;
import net.xqhs.graphs.pattern.GraphPattern;

public class JungMatch {

	private JungGraph graph;
	private JungGraph pattern;
	private List<Match> matches;

	public JungMatch(JungGraph graph, JungGraph pattern) {
		this.graph = graph;
		this.pattern = pattern;
		matches = new ArrayList<Match>();
	}
	
	public void addMatch(Match match) {
		matches.add(match);
	}
	
	public JungGraph getGraph() {
		return graph;
	}
	
	public JungGraph getPattern() {
		return pattern;
	}
	public List<Match> getMatches() {
		return matches;
	}
}
