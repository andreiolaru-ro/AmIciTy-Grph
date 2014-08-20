package amicity.graph.pc;

import net.xqhs.graphs.graph.Graph;

/*
 * Controls the state of the editor and handles interaction between
 * components.
 */
public class MainController {
	GraphView graphView;

	public void registerGraphView(GraphView graphView) {
		if (graphView == null) {
			this.graphView = graphView;
		}
	}
}
