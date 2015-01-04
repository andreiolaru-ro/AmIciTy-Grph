package amicity.graph.pc;

import java.io.File;
import java.util.List;

import amicity.graph.pc.gui.GraphExplorer;
import amicity.graph.pc.gui.PatternMatchViewer;
import amicity.graph.pc.gui.TabbedGraphEditor;
import amicity.graph.pc.gui.ToolBar;
import amicity.graph.pc.gui.util.GraphUIEvent;
import amicity.graph.pc.gui.util.GraphUIEventListener;
import amicity.graph.pc.jung.JungGraph;

/*
 * Controls the state of the editor and handles interaction between
 * components.
 */
public class MainController implements GraphUIEventListener {
	private FileManager fileManager;
	private GraphExplorer graphExplorer;
	private TabbedGraphEditor graphEditor;
	private PatternMatchViewer patternViewer;
	private ToolBar	toolBar;

	public void register(TabbedGraphEditor graphEditor) {
			this.graphEditor = graphEditor;
	}
	
	public void register(GraphExplorer graphExplorer) {
		this.graphExplorer = graphExplorer;
		graphExplorer.addEventListener(this);
	}
	
	public void register(FileManager fileManager) {
		this.fileManager = fileManager;
	}
	
	public void register(ToolBar toolBar) {
		this.toolBar = toolBar;		
	}
	
	public void register(PatternMatchViewer patternViewer) {
		this.patternViewer = patternViewer;
	}
	
	
	public GraphExplorer getGraphExplorer() {
		return graphExplorer;
	}
	
	public TabbedGraphEditor getGraphEditor() {
		return graphEditor;
	}
	
	public void init() {
		JungGraph graph = new CachedJungGraph("untitled", false);
		//graphEditor.openGraph(graph);
	}
	
	public void createNewGraph(boolean isPattern) {
		JungGraph graph = new CachedJungGraph("untitled", isPattern);
		graphEditor.openGraph(graph);
		graphExplorer.addGraph(graph, isPattern);
		if (!isPattern) {
			graph.addObserver(patternViewer);
		}
	}
	
	public void loadBareGraphFromFile(File file, boolean isPattern) {
		JungGraph graph = fileManager.loadBareGraph(file, isPattern);
		graphEditor.openGraph(graph);
		graphEditor.getCurrentEditor().doGraphLayout();
		graphExplorer.addGraph(graph, isPattern);
		
		if (!isPattern) {
			graph.addObserver(patternViewer);
		}
	}
	
	public void loadGraphFromFile(File file) {
		JungGraph graph = fileManager.loadGraph(file);
		graphEditor.openGraph(graph);
		//graphEditor.getCurrentEditor().doGraphLayout();
		graphExplorer.addGraph(graph, graph.isPattern());
		
		if (!graph.isPattern()) {
			graph.addObserver(patternViewer);
		}
	}
	
	public void saveCurrentGraph() {
		fileManager.saveGraph((CachedJungGraph) graphEditor.getCurrentEditor().getGraph());
	}
	
	public void saveGraphLibrary() {
		List<JungGraph> graphs = graphExplorer.getGraphs();
		List<JungGraph> patterns = graphExplorer.getPatterns();
		
		fileManager.saveAsLibrary(graphs, patterns);
	}
	
	public void loadGraphLibrary() {
		List<JungGraph> graphs = fileManager.loadLibrary();
		if (graphs == null) {
			return;
		}
		for (JungGraph graph : graphs) {
			graphExplorer.addGraph(graph, graph.isPattern());
			if (!graph.isPattern()) {
				graph.addObserver(patternViewer);
				graphEditor.openGraph(graph);
			}
		}
	}


	public void hideShowMatchingPatterns(boolean visible) {
		this.patternViewer.setVisible(true);		
	}

	@Override
	public void handleEvent(GraphUIEvent event)
	{
		if (event.getSource() == graphExplorer && event.getType() == GraphUIEvent.Type.SelectedGraphChanged) {
			JungGraph graph = (JungGraph) event.getData();
			
			assert graph != null;
			graphEditor.openGraph(graph);
		}
	}
}
