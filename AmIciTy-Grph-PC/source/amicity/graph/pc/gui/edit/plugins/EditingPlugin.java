package amicity.graph.pc.gui.edit.plugins;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.pattern.NodeP;

import org.apache.commons.collections15.Factory;

import amicity.graph.pc.jung.JungGraph;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingGraphMousePlugin;

public class EditingPlugin extends EditingGraphMousePlugin<Node, Edge> {	
	public EditingPlugin(int i, Factory<Node> vertexFactory,
			Factory<Edge> edgeFactory) {
		super(i, vertexFactory, edgeFactory);
		this.cursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	}

	@SuppressWarnings("unchecked")
	public void mousePressed(MouseEvent e) {
		System.out.println("Modifiers: " + modifiers);
	    System.out.println("CustomEditingGraphMousePlugin::mousePressed> modifier= " + e.getModifiers());
		if(e.getModifiers() == modifiers) {
			

            final VisualizationViewer<Node,Edge> vv =
                (VisualizationViewer<Node,Edge>)e.getSource();
            final Point2D p = e.getPoint();
            GraphElementAccessor<Node,Edge> pickSupport = vv.getPickSupport();
            if(pickSupport != null) {
            	JungGraph graph = (JungGraph) vv.getModel().getGraphLayout().getGraph();
            	// set default edge type
            	
            	edgeIsDirected = EdgeType.DIRECTED;

            	
                final Node vertex = pickSupport.getVertex(vv.getModel().getGraphLayout(), p.getX(), p.getY());
                if(vertex != null) { // get ready to make an edge
                    startVertex = vertex;
                    down = e.getPoint();
                    transformEdgeShape(down, down);
                    vv.addPostRenderPaintable(edgePaintable);
                    if((e.getModifiers() & MouseEvent.SHIFT_MASK) != 0
                    		&& vv.getModel().getGraphLayout().getGraph() instanceof UndirectedGraph == false) {
                        edgeIsDirected = EdgeType.DIRECTED;
                    }
                    if(edgeIsDirected == EdgeType.DIRECTED) {
                        transformArrowShape(down, e.getPoint());
                        vv.addPostRenderPaintable(arrowPaintable);
                    }
                } else { // make a new vertex
                	Node newVertex = vertexFactory.create();
                    Layout<Node,Edge> layout = vv.getModel().getGraphLayout();
                    graph.addVertexWithHistory(newVertex);
                    layout.setLocation(newVertex, vv.getRenderContext().getMultiLayerTransformer().inverseTransform(e.getPoint()));
                }
            }
            vv.repaint();
        }
    }
	
	public void mouseReleased(MouseEvent e) {
        if(checkModifiers(e)) {
            final VisualizationViewer<Node,Edge> vv =
                (VisualizationViewer<Node,Edge>)e.getSource();
            final Point2D p = e.getPoint();
            Layout<Node, Edge> layout = vv.getModel().getGraphLayout();
            GraphElementAccessor<Node,Edge> pickSupport = vv.getPickSupport();
            if(pickSupport != null) {
                final Node vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
                if(vertex != null && startVertex != null) {
                    JungGraph graph = 
                    	(JungGraph) vv.getGraphLayout().getGraph();
                    	Edge newEdge = new SimpleEdge(startVertex, vertex, "edge");
                    	graph.addEdgeWithHistory(newEdge);
                    vv.repaint();
                }
            }
            startVertex = null;
            down = null;
            edgeIsDirected = EdgeType.UNDIRECTED;
            vv.removePostRenderPaintable(edgePaintable);
            vv.removePostRenderPaintable(arrowPaintable);
        }
    }
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// System.out.println("Mouse dragged");
		super.mouseDragged(e);
	}
	
    private void transformEdgeShape(Point2D down, Point2D out) {
    	System.out.println("from :" + down + ". to: " + out);

        float x1 = (float) down.getX();
        float y1 = (float) down.getY();
        float x2 = (float) out.getX();
        float y2 = (float) out.getY();

        AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);
        
        float dx = x2-x1;
        float dy = y2-y1;
        float thetaRadians = (float) Math.atan2(dy, dx);
        xform.rotate(thetaRadians);
        float dist = (float) Math.sqrt(dx*dx + dy*dy);
        xform.scale(dist / rawEdge.getBounds().getWidth(), 1.0);
        edgeShape = xform.createTransformedShape(rawEdge);
    }
    
    private void transformArrowShape(Point2D down, Point2D out) {
        float x1 = (float) down.getX();
        float y1 = (float) down.getY();
        float x2 = (float) out.getX();
        float y2 = (float) out.getY();

        AffineTransform xform = AffineTransform.getTranslateInstance(x2, y2);
        
        float dx = x2-x1;
        float dy = y2-y1;
        float thetaRadians = (float) Math.atan2(dy, dx);
        xform.rotate(thetaRadians);
    }
}
