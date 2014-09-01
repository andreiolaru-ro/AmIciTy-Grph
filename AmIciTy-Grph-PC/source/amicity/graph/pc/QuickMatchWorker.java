package amicity.graph.pc;

import java.util.List;

import javax.swing.SwingWorker;

import amicity.graph.pc.common.MatchListener;
import testing.GraphMatcherTest;
import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.matcher.GraphMatcherQuick;
import net.xqhs.graphs.matcher.GraphMatchingProcess;
import net.xqhs.graphs.matcher.Match;
import net.xqhs.graphs.matcher.MonitorPack;
import net.xqhs.graphs.pattern.GraphPattern;
import net.xqhs.util.logging.LoggerSimple;
import net.xqhs.util.logging.LoggerSimple.Level;
import net.xqhs.util.logging.UnitComponent;

public class QuickMatchWorker extends SwingWorker<Match, Match> {

	Graph graph;
	GraphPattern pattern;
	MonitorPack monitor;
	GraphMatchingProcess process;
	MatchListener matchListener;
	
	public QuickMatchWorker(MatchListener listener, Graph graph, GraphPattern pattern) {
		this.matchListener = listener;
		this.graph = graph;
		this.pattern = pattern;
		monitor = new MonitorPack()
		.setLog((LoggerSimple) new UnitComponent().setUnitName(
				"matcher").setLogLevel(Level.INFO));

		System.out.println("Graph has: " + graph.getEdges());
		System.out.println("Pattern has: " + pattern.getEdges());
		process = GraphMatcherQuick.getMatcher(graph, pattern, monitor);
	}
	
	public void runWithThreshold(int k) {
		System.out.println("RunningONWORKER");
		process.resetIterator(k);
		while (true) {
			Match m = process.getNextMatch();
			if (m == null)
				break;
			publish(m);
		}
	}
	
	@Override
	protected Match doInBackground() throws Exception {
		runWithThreshold(3);
		runWithThreshold(4);
		runWithThreshold(2);




		System.out.println("Best matches count: " + process.getBestMatches().size());
		
		return null;
	}
	
	@Override
	protected void process(List<Match> chunks) {
		System.out.println("Got match!");
		matchListener.publishMatch(chunks);
	}

}
