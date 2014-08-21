package amicity.graph.pc;

import net.xqhs.graphs.graph.Graph;

/*
 * Controls the state of the editor and handles interaction between
 * components.
 */
public class MainController {
	GraphEditor graphEditor;

	public void registerGraphEditor(GraphEditor graphEditor) {
		if (graphEditor == null) {
			this.graphEditor = graphEditor;
		}
	}
}
