package amicity.graph.pc.gui.edit;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class CustomPickingGraphMousePlugin<V, E> extends PickingGraphMousePlugin<V, E> {
	 @SuppressWarnings("unchecked")
	    public void mousePressed(MouseEvent e) {
	        down = e.getPoint();
	        VisualizationViewer<V,E> vv = (VisualizationViewer)e.getSource();
	        GraphElementAccessor<V,E> pickSupport = vv.getPickSupport();
	        PickedState<V> pickedVertexState = vv.getPickedVertexState();
	        PickedState<E> pickedEdgeState = vv.getPickedEdgeState();
	        if(pickSupport != null && pickedVertexState != null) {
	            Layout<V,E> layout = vv.getGraphLayout();
	            if(e.getModifiers() == modifiers) {
	                rect.setFrameFromDiagonal(down,down);
	                // p is the screen point for the mouse event
	                Point2D ip = e.getPoint();

	                vertex = pickSupport.getVertex(layout, ip.getX(), ip.getY());
	                if(vertex != null) {
	                    if(pickedVertexState.isPicked(vertex) == false) {
	                    	pickedVertexState.clear();
	                    	pickedEdgeState.clear();
	                    	pickedVertexState.pick(vertex, true);
	                    }
	                    // layout.getLocation applies the layout transformer so
	                    // q is transformed by the layout transformer only
	                    Point2D q = layout.transform(vertex);
	                    // transform the mouse point to graph coordinate system
	                    Point2D gp = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(Layer.LAYOUT, ip);

	                    offsetx = (float) (gp.getX()-q.getX());
	                    offsety = (float) (gp.getY()-q.getY());
	                } else if((edge = pickSupport.getEdge(layout, ip.getX(), ip.getY())) != null) {
	                    pickedEdgeState.clear();
	                    pickedVertexState.clear();
	                    pickedEdgeState.pick(edge, true);
	                } else {
	                    vv.addPostRenderPaintable(lensPaintable);
	                	pickedEdgeState.clear();
	                    pickedVertexState.clear();
	                }
	                
	            } else if(e.getModifiers() == addToSelectionModifiers) {
	                vv.addPostRenderPaintable(lensPaintable);
	                rect.setFrameFromDiagonal(down,down);
	                Point2D ip = e.getPoint();
	                vertex = pickSupport.getVertex(layout, ip.getX(), ip.getY());
	                if(vertex != null) {
	                    boolean wasThere = pickedVertexState.pick(vertex, !pickedVertexState.isPicked(vertex));
	                    if(wasThere) {
	                        vertex = null;
	                    } else {

	                        // layout.getLocation applies the layout transformer so
	                        // q is transformed by the layout transformer only
	                        Point2D q = layout.transform(vertex);
	                        // translate mouse point to graph coord system
	                        Point2D gp = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(Layer.LAYOUT, ip);

	                        offsetx = (float) (gp.getX()-q.getX());
	                        offsety = (float) (gp.getY()-q.getY());
	                    }
	                } else if((edge = pickSupport.getEdge(layout, ip.getX(), ip.getY())) != null) {
	                    pickedEdgeState.pick(edge, !pickedEdgeState.isPicked(edge));
	                }
	            }
	        }
	        if(vertex != null) e.consume();
	    }

	    @SuppressWarnings("unchecked")
	    public void mouseDragged(MouseEvent e) {
	        if(locked == false) {
	            VisualizationViewer<V,E> vv = (VisualizationViewer)e.getSource();
	            if(vertex != null) {
	                Point p = e.getPoint();
	                Point2D graphPoint = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(p);
	                Point2D graphDown = vv.getRenderContext().getMultiLayerTransformer().inverseTransform(down);
	                Layout<V,E> layout = vv.getGraphLayout();
	                double dx = graphPoint.getX()-graphDown.getX();
	                double dy = graphPoint.getY()-graphDown.getY();
	                PickedState<V> ps = vv.getPickedVertexState();
	                
	                for(V v : ps.getPicked()) {
	                    Point2D vp = layout.transform(v);
	                    vp.setLocation(vp.getX()+dx, vp.getY()+dy);
	                    layout.setLocation(v, vp);
	                }
	                down = p;

	            } else {
	                Point2D out = e.getPoint();
	                if(e.getModifiers() == this.addToSelectionModifiers) {
	                    rect.setFrameFromDiagonal(down,out);
	                }
	            }
	            if(vertex != null) e.consume();
	            vv.repaint();
	        }
	    }
}
