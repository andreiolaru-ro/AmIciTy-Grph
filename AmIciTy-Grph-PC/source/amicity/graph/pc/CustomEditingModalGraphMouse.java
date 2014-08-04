package amicity.graph.pc;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.MapTransformer;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMousePlugin;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
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
public class CustomEditingModalGraphMouse<V, E> extends EditingModalGraphMouse<V,E> {

	public CustomEditingModalGraphMouse(RenderContext<V, E> rc,
			Factory<V> vertexFactory, Factory<E> edgeFactory) {
		super(rc, vertexFactory, edgeFactory);
		setModeKeyListener(new ModeKeyAdapter());
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
			System.out.println("key pressed: " + e.getKeyCode());
			switch(e.getKeyCode()) {
			case 10:
				openEditPopup(e.getSource());
			}
		}
	}
	

    @Override
    protected void loadPlugins() {
            pickingPlugin = new CustomPickingGraphMousePlugin<V, E>();
            animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<V, E>();
            // Drag translate
            translatingPlugin = new TranslatingGraphMousePlugin(
                            InputEvent.BUTTON1_MASK);
            // zoom in/out
            scalingPlugin = new ScalingGraphMousePlugin(
                            new CrossoverScalingControl(), 0, in, out);
            //rotatingPlugin = new RotatingGraphMousePlugin();
            //shearingPlugin = new ShearingGraphMousePlugin();
            editingPlugin = new CustomEditingGraphMousePlugin<V, E>(CTRL_MASK, vertexFactory,
                            edgeFactory);
            //labelEditingPlugin = new CustomLabelEditingPlugin<V, E>(24);
            
            annotatingPlugin = new AnnotatingGraphMousePlugin<V, E>(rc);
            
            popupEditingPlugin = new CustomEditingPopupGraphMousePlugin<V, E>(
                            vertexFactory, edgeFactory);
            
            
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
    
    private
    void openEditPopup(Object source) {
    	final VisualizationViewer<V,E> vv =
                (VisualizationViewer<V,E>)source;
    	
    	Transformer<V,String> vs = vv.getRenderContext().getVertexLabelTransformer();
    	Map<V,String> nodeTransformer = ((MapTransformer)vs).getMap();
    	Transformer<E, String> es = vv.getRenderContext().getEdgeLabelTransformer();
    	Map<E, String> edgeTransformer = ((MapTransformer)es).getMap();
    	
    	Set<V> pickedNodes = vv.getPickedVertexState().getPicked();
    	for (V node : pickedNodes) {
    		String newLabel = JOptionPane.showInputDialog("New Vertex Label for "+ nodeTransformer.get(node));
    		if (newLabel != null && newLabel.length() != 0)
    			nodeTransformer.put(node, newLabel);
    		vv.repaint();
    	}

    	Set<E> pickedEdges = vv.getPickedEdgeState().getPicked();
    	if (pickedEdges.size() == 0)
    		return;
    	String newLabel = JOptionPane.showInputDialog("New Edge Label:");
    	if (newLabel != null && newLabel.length() != 0) {
    		for (E edge : pickedEdges) {
    			edgeTransformer.put(edge,  newLabel);
    		}
    		vv.repaint();
    	}
    	
    }
}
