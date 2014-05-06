package amicity.graph.pc;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

public class CustomPopupGraphMousePlugin extends AbstractPopupGraphMousePlugin implements MouseListener {

    public CustomPopupGraphMousePlugin() {
        this(MouseEvent.BUTTON3_MASK);
    }
    public CustomPopupGraphMousePlugin(int modifiers) {
        super(modifiers);
    }

    /**
     * If this event is over a station (vertex), pop up a menu to
     * allow the user to perform a few actions; else, pop up a menu over the layout/canvas
     *
     * @param e
     */
    protected void handlePopup(MouseEvent e) {
        @SuppressWarnings("unchecked")
		final VisualizationViewer<Number, Number> vv =
                (VisualizationViewer<Number, Number>)e.getSource();
        final Point2D p = e.getPoint();
        final Point2D ivp = p;

        GraphElementAccessor<Number, Number> pickSupport = vv.getPickSupport();
        if(pickSupport != null) {

            JPopupMenu popup = new JPopupMenu();

            final Number station = pickSupport.getVertex(vv.getGraphLayout(), ivp.getX(), ivp.getY());

            if(station != null) {
                boolean isRadio = true;
                popup.add(new AbstractAction("Baus") {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
					}
                });

                if(popup.getComponentCount() > 0) {
                    popup.show(vv, e.getX(), e.getY());
                }

            } else { //to pop-up over the canvas/layout rather than over the station

	            popup.add(new AbstractAction("Create Unit") {
	                public void actionPerformed(ActionEvent e) {
	                    //do something here
	                }
	            });
	
	
	            if(popup.getComponentCount() > 0) {
	                popup.show(vv, e.getX(), e.getY());
	            }
            }
        }
    }
}