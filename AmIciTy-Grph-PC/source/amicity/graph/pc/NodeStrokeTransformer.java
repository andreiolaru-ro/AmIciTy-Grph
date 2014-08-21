package amicity.graph.pc;

import java.awt.BasicStroke;
import java.awt.Stroke;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;

import org.apache.commons.collections15.Transformer;

import amicity.graph.pc.jung.JungGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedInfo;

public class NodeStrokeTransformer implements Transformer<Node,Stroke>
    {
        protected boolean highlight = false;
        protected Stroke heavy = new BasicStroke(5);
        protected Stroke medium = new BasicStroke(3);
        protected Stroke light = new BasicStroke(1);
        protected VisualizationViewer<Node, Edge> vv;
        
        public NodeStrokeTransformer(VisualizationViewer<Node, Edge> vv)
        {
        	this.vv = vv;
        	setHighlight(true);
        }
        
        public void setHighlight(boolean highlight)
        {
            this.highlight = highlight;
        }
        
        public Stroke transform(Node v)
        {
        	PickedInfo<Node> pi = vv.getPickedVertexState();
        	Graph<Node, Edge> graph = vv.getModel().getGraphLayout().getGraph();
        	
            if (highlight)
            {
                if (pi.isPicked(v))
                    return heavy;
                else
                {
                	for(Node w : graph.getNeighbors(v)) {
//                    for (Iterator iter = graph.getNeighbors(v)v.getNeighbors().iterator(); iter.hasNext(); )
//                    {
//                        Vertex w = (Vertex)iter.next();
                        if (pi.isPicked(w))
                            return medium;
                    }
                    return light;
                }
            }
            else
                return light; 
        }
    }