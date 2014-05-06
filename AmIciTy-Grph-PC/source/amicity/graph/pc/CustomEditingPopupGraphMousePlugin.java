package amicity.graph.pc;

import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.visualization.control.EditingPopupGraphMousePlugin;

public class CustomEditingPopupGraphMousePlugin<V, E> extends EditingPopupGraphMousePlugin<V,E> {

	public CustomEditingPopupGraphMousePlugin(Factory<V> vertexFactory,
			Factory<E> edgeFactory) {
		super(vertexFactory, edgeFactory);
		// TODO Auto-generated constructor stub
	}

	@Override
    protected void handlePopup(MouseEvent e) {
		popup = new JPopupMenu();
		super.handlePopup(e);
	}
}
