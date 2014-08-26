package amicity.graph.pc;

import java.util.List;

import javax.swing.SwingWorker;

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
	
	public QuickMatchWorker(Graph graph, GraphPattern pattern) {
		this.graph = graph;
		this.pattern = pattern;
		monitor = new MonitorPack()
		.setLog((LoggerSimple) new UnitComponent().setUnitName(
				"matcher").setLogLevel(Level.INFO));

		System.out.println("Graph nodes: " + graph.getNodes());
		System.out.println("Graph has: " + graph.getEdges());
		for (Edge edge : graph.getEdges()) {
			System.out.println(edge.getFrom());
		}
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
		testMatchingProcess(graph, pattern, monitor);
		runWithThreshold(4);
		runWithThreshold(3);
		runWithThreshold(2);
		System.out.println("Best matches count: " + process.getBestMatches().size());
		
		GraphMatcherTest tester = new GraphMatcherTest();
		
		SimpleGraph grph = tester.mGraph;
		GraphPattern ptrn = tester.mPattern;
		return null;
	}
	
	protected void testMatchingProcess(Graph G, GraphPattern GP,
			MonitorPack monitoring) {
		GraphMatchingProcess GMQ = GraphMatcherQuick.getMatcher(G, GP,
				monitoring);
		//printSeparator(0, "individual matches [4]");
		GMQ.resetIterator(4);
		while (true) {
			Match m = GMQ.getNextMatch();
			if (m == null)
				break;
			//log.li("============== new match\n[]", m);
		}
		monitoring.printStats();

		//printSeparator(0, "individual matches [3]"); // =================================
		GMQ.resetIterator(3);
		while (true) {
			Match m = GMQ.getNextMatch();
			if (m == null)
				break;
			System.out.println("NEW MATCH!");
			//log.li("============== new match\n[]", m);
		}
		monitoring.printStats();

		//printSeparator(0, "all matches [3]"); // ============================
		// GMQ.clearData();
		//log.li(GMQ.getAllMatches(3).toString()); // is a long line
		monitoring.printStats();

		//printSeparator(0, "best matches"); // ============================
		//log.li(GMQ.getBestMatches().toString()); // is a long line
		monitoring.printStats();
	}
	
	@Override
	protected void process(List<Match> chunks) {
		System.out.println("Got match!");
	}

}
