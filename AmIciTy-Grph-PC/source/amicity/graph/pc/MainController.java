package amicity.graph.pc;

import java.io.File;

import amicity.graph.pc.gui.GraphEditor;
import amicity.graph.pc.gui.GraphExplorer;
import amicity.graph.pc.gui.ToolBar;
import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.Graph;

/*
 * Controls the state of the editor and handles interaction between
 * components.
 */
public class MainController {
	GraphEditor graphEditor;
	private FileManager fileManager;
	private GraphExplorer graphExplorer;
	private Object toolBar;

	public void registerGraphEditor(GraphEditor graphEditor) {
			this.graphEditor = graphEditor;
	}
	
	public void registerGraphExplorer(GraphExplorer graphExplorer) {
		this.graphExplorer = graphExplorer;
	}
	
	public void init() {
		JungGraph graph = new CachedJungGraph("untitled", false);
		graphEditor.loadGraph(graph);
		graphExplorer.addGraph(graph, false);
	}
	
	public void createNewGraph() {
		JungGraph graph = new CachedJungGraph("untitled", false);
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
	
	public void loadBareGraphFromFile(File file, boolean isPattern) {
		JungGraph graph = fileManager.loadBareGraph(file, isPattern);
		graphEditor.loadGraph(graph, true);
		graphExplorer.addGraph(graph, isPattern);
	}
	
	public void loadGraphFromFile(File file, boolean isPattern) {
		JungGraph graph = fileManager.loadGraph(file, isPattern);
		graphEditor.loadGraph(graph, true);
		graphExplorer.addGraph(graph, isPattern);
	}
	
	public void saveCurrentGraph() {
		fileManager.saveGraph((CachedJungGraph) graphEditor.getGraph());
	}

	public void registerToolBar(ToolBar toolBar) {
		this.toolBar = toolBar;		
	}
	
	public GraphExplorer getGraphExplorer() {
		return graphExplorer;
	}
}
