package amicity.graph.pc;

import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.Modifier;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.annotations.AnnotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.AnimatedPickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.LabelEditingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.RotatingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ScalingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.ShearingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.TranslatingGraphMousePlugin;

public class CustomEditingModalGraphMouse<V, E> extends EditingModalGraphMouse<V,E> {

	public CustomEditingModalGraphMouse(RenderContext<V, E> rc,
			Factory<V> vertexFactory, Factory<E> edgeFactory) {
		super(rc, vertexFactory, edgeFactory);
	}
	
    @Override
    protected void loadPlugins() {
            pickingPlugin = new PickingGraphMousePlugin<V, E>();
            animatedPickingPlugin = new AnimatedPickingGraphMousePlugin<V, E>();
            translatingPlugin = new TranslatingGraphMousePlugin(
                            InputEvent.BUTTON1_MASK);
            scalingPlugin = new ScalingGraphMousePlugin(
                            new CrossoverScalingControl(), 0, in, out);
            rotatingPlugin = new RotatingGraphMousePlugin();
            shearingPlugin = new ShearingGraphMousePlugin();
            editingPlugin = new EditingGraphMousePlugin<V, E>(vertexFactory,
                            edgeFactory);
            labelEditingPlugin = new CustomLabelEditingPlugin<V, E>(24);
            
            annotatingPlugin = new AnnotatingGraphMousePlugin<V, E>(rc);
            popupEditingPlugin = new CustomEditingPopupGraphMousePlugin<V, E>(
                            vertexFactory, edgeFactory);
            add(scalingPlugin);
            setMode(Mode.TRANSFORMING);
    }
}
