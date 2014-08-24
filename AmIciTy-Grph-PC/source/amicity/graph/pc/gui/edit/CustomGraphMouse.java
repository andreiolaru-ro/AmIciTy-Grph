package amicity.graph.pc.gui.edit;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;

public class CustomGraphMouse<V, E> implements VisualizationViewer.GraphMouse {

	public CustomGraphMouse() {
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked.");
    }
	
	

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("mouse pressed.");
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		System.out.println("mouse released");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("mouse entered.");
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("mouse exited.");
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		System.out.println("mouse dragged");
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println("mouse moved.");
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("mouse wheel moved.");
		
	}
	
	V getVertexFromEvent(MouseEvent e) {
		 final VisualizationViewer<V,E> vv =
	                (VisualizationViewer<V,E>)e.getSource();
	     final Point2D p = e.getPoint();
	     GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
	     if(pickSupport != null) {
	    	 Graph<V,E> graph = vv.getModel().getGraphLayout().getGraph();	            	
	         return pickSupport.getVertex(vv.getModel().getGraphLayout(), p.getX(), p.getY());
	     }
	     return null;
	}
}
