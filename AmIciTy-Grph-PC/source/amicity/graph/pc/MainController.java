package amicity.graph.pc;

import java.io.File;

import amicity.graph.pc.gui.GraphEditor;
import amicity.graph.pc.gui.GraphExplorer;
import amicity.graph.pc.gui.TabbedGraphEditor;
import amicity.graph.pc.gui.ToolBar;
import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.Graph;

/*
 * Controls the state of the editor and handles interaction between
 * components.
 */
public class MainController {
	private FileManager fileManager;
	private GraphExplorer graphExplorer;
	private TabbedGraphEditor graphEditor;
	private Object toolBar;

	public void register(TabbedGraphEditor graphEditor) {
			this.graphEditor = graphEditor;
	}
	
	public void register(GraphExplorer graphExplorer) {
		this.graphExplorer = graphExplorer;
	}
	
	public void register(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	public void register(ToolBar toolBar) {
		this.toolBar = toolBar;		
	}
	
	
	public GraphExplorer getGraphExplorer() {
		return graphExplorer;
	}
	
	public TabbedGraphEditor getGraphEditor() {
		return graphEditor;
	}
	
	public void init() {
		JungGraph graph = new CachedJungGraph("untitled", false);
		graphEditor.openGraph(graph);
	}
	
	public void createNewGraph() {
		JungGraph graph = new CachedJungGraph("untitled", false);
		graphEditor.openGraph(graph);
		graphExplorer.addGraph(graph, false);
	}
	
	public void loadBareGraphFromFile(File file, boolean isPattern) {
		JungGraph graph = fileManager.loadBareGraph(file, isPattern);
		graphEditor.openGraph(graph);
		graphEditor.getCurrentEditor().doGraphLayout();
	}
	
	public void loadGraphFromFile(File file, boolean isPattern) {
		JungGraph graph = fileManager.loadGraph(file, isPattern);
		graphEditor.openGraph(graph);
		graphEditor.getCurrentEditor().doGraphLayout();
		graphExplorer.addGraph(graph, isPattern);
	}
	
	public void saveCurrentGraph() {
		fileManager.saveGraph((CachedJungGraph) graphEditor.getCurrentEditor().getGraph());
	}

	
	public void runQuickMatch(JungGraph graph, JungGraph pattern) {
		System.out.println("Running!!");
		QuickMatchWorker worker = new QuickMatchWorker(graph.asSimpleGraph(), pattern.asGraphPattern());
		worker.execute();
	}
}
