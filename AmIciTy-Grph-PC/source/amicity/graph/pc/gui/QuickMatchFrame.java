package amicity.graph.pc.gui;

import java.util.List;

import javax.swing.JFrame;

import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.matcher.Match;
import net.xqhs.graphs.pattern.GraphPattern;
import amicity.graph.pc.QuickMatchWorker;
import amicity.graph.pc.common.MatchListener;
import amicity.graph.pc.jung.JungGraph;

public class QuickMatchFrame extends JFrame implements MatchListener {
	MatchViewer view;
	QuickMatchWorker worker;
	public QuickMatchFrame(Graph graph, GraphPattern pattern) {
		view = new MatchViewer(new JungGraph("", false));
		worker = new QuickMatchWorker(this, graph, pattern);
		worker.execute();
		this.add(view);
		this.setSize(800, 800);
	}
	@Override
	public void publishMatch(List<Match> match) {
		Match last = match.get(match.size() - 1);
		view.setMatch(last);
	}
}
