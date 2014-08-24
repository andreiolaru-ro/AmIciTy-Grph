package amicity.graph.pc;

import java.io.File;

import amicity.graph.pc.gui.GraphEditor;
import amicity.graph.pc.gui.GraphExplorer;
import amicity.graph.pc.gui.GraphList;
import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.Graph;

/*
 * Controls the state of the editor and handles interaction between
 * components.
 */
public class MainController {
	GraphEditor graphEditor;
	GraphList graphList;
	private FileManager fileManager;
	private GraphExplorer graphExplorer;

	public void registerGraphEditor(GraphEditor graphEditor) {
			this.graphEditor = graphEditor;
	}
	
	public void registerGraphList(GraphList graphList) {
		this.graphList = graphList;
	}
	
	public void init() {
		JungGraph graph = JungGraph.createJungGraph();
		graphEditor.loadGraph(graph);
		graphExplorer.addGraph(graph, false);
	}
	
	public void createNewGraph() {
		JungGraph graph = JungGraph.createJungGraph();
		graphEditor.loadGraph(graph);
		graphExplorer.addGraph(graph, false);
	}
	
	public void loadGraph(JungGraph graph) {
		if (graph == graphEditor.getGraph())
			return;
		graphEditor.loadGraph(graph);
	}

	public void registerFileManager(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	public void loadBareGraph(File file, boolean isPattern) {
		JungGraph graph = fileManager.loadBareGraph(file, isPattern);
		graphEditor.loadGraph(graph, true);
		graphExplorer.addGraph(graph, isPattern);
	}

	public void registerGraphExplorer(GraphExplorer graphExplorer) {
		this.graphExplorer = graphExplorer;
		
	}
}
