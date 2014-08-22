package amicity.graph.pc;

import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.Graph;

/*
 * Controls the state of the editor and handles interaction between
 * components.
 */
public class MainController {
	GraphEditor graphEditor;
	GraphList graphList;

	public void registerGraphEditor(GraphEditor graphEditor) {
			this.graphEditor = graphEditor;
	}
	
	public void registerGraphList(GraphList graphList) {
		this.graphList = graphList;
	}
	
	public void init() {
		if (graphList.getCount() == 0) {
			graphEditor.loadGraph(graphList.createNewGraph());
		}
	}
	
	public void createNewGraph() {
		graphEditor.loadGraph(graphList.createNewGraph());
	}
	
	public void loadGraph(JungGraph graph) {
		if (graph == graphEditor.getGraph())
			return;
		graphEditor.loadGraph(graph);
	}
}
