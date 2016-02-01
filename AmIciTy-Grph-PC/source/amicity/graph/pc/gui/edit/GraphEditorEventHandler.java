package amicity.graph.pc.gui.edit;

import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.MapTransformer;

import amicity.graph.pc.common.GraphEvent.Type;
import amicity.graph.pc.gui.edit.plugins.PickingPlugin;
import amicity.graph.pc.gui.edit.plugins.EditingPlugin;
import amicity.graph.pc.gui.util.OSValidator;
import amicity.graph.pc.jung.JungGraph;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;


/**
 * We're extending a modal graph editor since it has the most complete list of features
 * by default, but we're ignoring the editor modes and just add different key modifiers
 * for each plugin.
 * 
 * @author catalin
 *
 * @param <V>
 * @param <E>
 */
public class GraphEditorEventHandler extends EditingModalGraphMouse<Node, Edge> {

	int[] defaultKeyMap = {10, 127, 82};
	int[] macKeyMap = {10, 8, 82};
	int[] keyMap;
	
	public enum FeatureMask {
		PICK_SUPPORT(1),
		TRANSLATE_SUPPORT(2),
		SCALING_SUPPORT(4),
		EDITING_SUPPORT(8),
		KEYBOARD_SUPPORT(16);
		
		private int value;
		private FeatureMask(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public boolean hasFeature(FeatureMask featureMask) {
			return ((value & featureMask.getValue()) == 0);
		}
	}
	
	FeatureMask mask;
	
	public GraphEditorEventHandler(RenderContext<Node, Edge> rc,
									Factory<Node> vertexFactory, Factory<Edge> edgeFactory, FeatureMask map) {
		super(rc, vertexFactory, edgeFactory);
		if (map.hasFeature(FeatureMask.KEYBOARD_SUPPORT)) {
			setModeKeyListener(new ModeKeyAdapter());		
			if (OSValidator.isMac()) {
				keyMap = macKeyMap;
			} else {
				keyMap = defaultKeyMap;
			}
		}
		
	}
	
	public GraphEditorEventHandler(RenderContext<Node, Edge> rc,
			Factory<Node> vertexFactory, Factory<Edge> edgeFactory) {
		super(rc, vertexFactory, edgeFactory);
		setModeKeyListener(new ModeKeyAdapter());
		
		if (OSValidator.isMac()) {
			keyMap = macKeyMap;
		} else {
			keyMap = defaultKeyMap;
		}
	}
	
	final int SHIFT_MASK = 17;
	final int CTRL_MASK = 18;
	final int ALT_MASK = 24;
	
	public class ModeKeyAdapter extends KeyAdapter {
		@Override
	    public void keyTyped(KeyEvent event) {
			char keyChar = event.getKeyChar();
			System.out.println("Key typed: " + keyChar);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
	    	final VisualizationViewer<Node,Edge> vv =
	                (VisualizationViewer<Node, Edge>)e.getSource();
	    	JungGraph graph = (JungGraph) vv.getModel().getGraphLayout().getGraph();
	    	
			System.out.println("key pressed: " + e.getKeyCode());
			if (keyMap[0] == e.getKeyCode()) {
				openEditPopup(e.getSource());
			} else if (keyMap[1] == e.getKeyCode()) {
				delete(e.getSource());
			} else if (keyMap[2] == e.getKeyCode()) {
				graph.dirty(Type.GraphStructure);
			}
		}
	}
	

    @Override
    protected void loadPlugins() {
            pickingPlugin = new PickingPlugin<Node, Edge>();
            animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<Node, Edge>();
            // Drag translate
            translatingPlugin = new TranslatingGraphMousePlugin(
                            InputEvent.BUTTON1_MASK);

            // zoom in/out
            scalingPlugin = new ScalingGraphMousePlugin(
                            new CrossoverScalingControl(), 0, in, out);
            //rotatingPlugin = new RotatingGraphMousePlugin();
            //shearingPlugin = new ShearingGraphMousePlugin();
            editingPlugin = new EditingPlugin(ALT_MASK, vertexFactory,
                            edgeFactory);
            //labelEditingPlugin = new CustomLabelEditingPlugin<V, E>(24);
            
            annotatingPlugin = new AnnotatingGraphMousePlugin<Node, Edge>(rc);
            
            
            add(scalingPlugin);
            add(pickingPlugin);
            add(translatingPlugin);
            //add(labelEditingPlugin);
            add(editingPlugin);

            //setMode(Mode.TRANSFORMING);
    }
    
    @Override
	public
    void add(GraphMousePlugin plugin) {
    	System.out.println("add plugin: " + plugin.toString());
    	super.add(plugin);
    }
    
    @Override
    public
    void remove(GraphMousePlugin plugin) {
    	System.out.println("remove " + plugin.toString());
    	super.remove(plugin);
    }
    
    
	public String getNextNameFor(JungGraph graph, String currentName) {
		int i = 0;
		
		String id = "0";
		String baseName = currentName;
		for (int k = currentName.length() - 1; k >= 1; k++) {
			if (currentName.charAt(k) <= '9' && currentName.charAt(k) >= '0') {
				id = currentName.charAt(k) + id;
				i++;
			} else {
				break;
			}
		}
		
		if (i > 0) {
			baseName = currentName.substring(0, currentName.length() - i);
			i = Integer.parseInt(id);
		} else {
			i = 2;
		}
		
		String name = baseName;
		while (graphContains(graph, name)) {
			name = baseName + i++;
		}
		
		return name;
	}
	
	boolean graphContains(JungGraph graph, String name) {
		for (Node node : graph.getVertices()) {
			if (node.getLabel().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
    
    private
    void openEditPopup(Object source) {
    	final VisualizationViewer<Node,Edge> vv =
                (VisualizationViewer<Node, Edge>)source;
    	    	
    	Set<Node> pickedNodes = vv.getPickedVertexState().getPicked();
    	JungGraph graph = (JungGraph) vv.getModel().getGraphLayout().getGraph();
    	for (Node node : pickedNodes) {
    		String newLabel = JOptionPane.showInputDialog("New Vertex Label for "+ node.getLabel());
    		

    		
    		if (newLabel != null && newLabel.length() != 0) {
        		if (newLabel.length() > 1 && newLabel.charAt(0) == '?') {
        			continue;			
        		}
        		
        		if (newLabel.equals("?") && !graph.isPattern()) {
        			continue;
        		}
        		
        		if (!newLabel.equals("?"))
        			newLabel = getNextNameFor(graph, newLabel);
    			graph.setLabelWithHistory(node, newLabel);
    		}
    		vv.repaint();
    	}

    	Set<Edge> pickedEdges = vv.getPickedEdgeState().getPicked();
    	if (pickedEdges.size() == 0)
    		return;
    	String newLabel = JOptionPane.showInputDialog("New Edge Label:");
    	if (newLabel != null && newLabel.length() != 0) {
    		for (Edge edge : pickedEdges) {
    			graph.setLabelWithHistory(edge, newLabel);
    			edge.setLabel(newLabel);
    		}
    		vv.repaint();
    	}
    }
    
    private void delete(Object source) {
    	final VisualizationViewer<Node,Edge> vv =
                (VisualizationViewer<Node, Edge>)source;
    	    	
    	Set<Node> pickedNodes = vv.getPickedVertexState().getPicked();
    	JungGraph graph = (JungGraph) vv.getModel().getGraphLayout().getGraph();
    	for (Node node : pickedNodes) {
    		graph.removeVertexWithHistory(node);
    		vv.repaint();
    	}

    	Set<Edge> pickedEdges = vv.getPickedEdgeState().getPicked();
    	if (pickedEdges.size() == 0)
    		return;
    	
    	for (Edge edge : pickedEdges) {
    		graph.removeEdge(edge);
    	}
    	vv.repaint();
    }
}
