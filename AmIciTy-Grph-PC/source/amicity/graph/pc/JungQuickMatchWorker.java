package amicity.graph.pc;

import java.util.List;

import javax.swing.SwingWorker;

import net.xqhs.graphs.matcher.GraphMatcherQuick;
import net.xqhs.graphs.matcher.Match;
import net.xqhs.graphs.matcher.MonitorPack;
import net.xqhs.util.logging.LoggerSimple;
import net.xqhs.util.logging.UnitComponent;
import net.xqhs.util.logging.LoggerSimple.Level;
import amicity.graph.pc.common.JungMatchListener;
import amicity.graph.pc.jung.JungMatch;

public class JungQuickMatchWorker extends SwingWorker<JungMatch, JungMatch> {

	List<JungMatch> matches;
	private MonitorPack monitor;
	private JungMatchListener listener;
	public JungQuickMatchWorker(JungMatchListener listener, List<JungMatch> matches) {
		this.matches = matches;
		this.listener = listener;
		
		monitor = new MonitorPack()
		.setLog((LoggerSimple) new UnitComponent().setUnitName(
				"matcher").setLogLevel(Level.INFO));
	}

	
	@Override
	protected JungMatch doInBackground() throws Exception {
		for (JungMatch match : matches) {
			GraphMatcherQuick process = GraphMatcherQuick.getMatcher(match.getGraph().asSimpleGraph(), match.getPattern().asGraphPattern(), monitor);
			process.resetIterator(3);
			while (true) {
				Match m = process.getNextMatch();
				if (m == null)
					break;
			}

			for (Match m : process.getAllMatches(3)) {
				match.addMatch(m);
			}
			publish(match);
		}
		return null;
	}

	@Override
	protected void process(List<JungMatch> chunks) {
		System.out.println("Got match!");
		listener.publish(chunks);
	}
}
