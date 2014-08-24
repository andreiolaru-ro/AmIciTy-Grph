package amicity.graph.pc.gui.edit;

import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;

public class CustomEditingPopupGraphMousePlugin<V, E> extends EditingPopupGraphMousePlugin<V,E> {

	public CustomEditingPopupGraphMousePlugin(Factory<V> vertexFactory,
			Factory<E> edgeFactory) {
		super(vertexFactory, edgeFactory);
	}

	@Override
    protected void handlePopup(MouseEvent e) {
		popup = new JPopupMenu();
		super.handlePopup(e);
	}
}
