/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleNode;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import amicity.graph.pc.MainController;
import amicity.graph.pc.gui.edit.EdgeTransformer;
import amicity.graph.pc.gui.edit.GraphEditorEventHandler;
import amicity.graph.pc.gui.edit.NodeStrokeTransformer;
import amicity.graph.pc.gui.edit.NodeTransformer;
import amicity.graph.pc.jung.JungGraph;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;

/**
 * @author Badea Catalin
 * @author Tom Nelson
 * 
 */
public class GraphEditor extends JPanel {
	private static final long serialVersionUID = -2023243689258876709L;

    private JungGraph graph;
    private VisualizationViewer<Node,Edge> vv;
    
    public GraphEditor(MainController controller) {
    	controller.registerGraphEditor(this);
        graph = new JungGraph("untitled", false);
        controller.registerGraphEditor(this);
 
        this.setLayout(new BorderLayout());
        
        vv =  new VisualizationViewer<Node,Edge>(graph.getLayout());
        vv.setBackground(Color.lightGray);

        vv.getRenderContext().setVertexLabelTransformer(new NodeTransformer());
        vv.getRenderContext().setEdgeLabelTransformer(new EdgeTransformer());
        vv.getRenderContext().setVertexStrokeTransformer(new NodeStrokeTransformer(vv));
		vv.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.YELLOW));
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);


		Transformer<Node,Shape> vertexSize = new Transformer<Node,Shape>(){
            public Shape transform(Node i){
                //Ellipse2D circle = new Ellipse2D.Double(-15,-15,20, 20);

               return new Rectangle(-20,-10,i.getLabel().length()*10,30);
               //return AffineTransform.getScaleInstance(2, 2).createTransformedShape(circle);
                //else if(i==2) return circle;
                //else return new Rectangle(-20, -10, 40, 20);
                
            }
        };
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
		
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        panel.setPreferredSize(new Dimension(400, 400));
        add(panel, BorderLayout.CENTER);
        
        Factory<Node> vertexFactory = new NodeFactory();
        Factory<Edge> edgeFactory = new EdgeFactory();
        
        final GraphEditorEventHandler graphMouse = 
        	new GraphEditorEventHandler(vv.getRenderContext(), vertexFactory, edgeFactory);

        
        // the EditingGraphMouse will pass mouse event coordinates to the
        // vertexLocations function to set the locations of the vertices as
        // they are created
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());
        
        final ScalingControl scaler = new CrossoverScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });

        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        
        JButton layoutButton = new JButton("perform layout");
        layoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//layout = new CircleLayout<Node,Edge>(graph);
            	FRLayout<Node, Edge> layout = new FRLayout<Node, Edge>(graph);
            	graph.setLayout(new StaticLayout<Node, Edge>(graph, layout));
                vv.getModel().setGraphLayout(layout);
            }
        });

        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        controls.add(layoutButton);;
        add(controls, BorderLayout.SOUTH);
    }
    
    public void loadGraph(JungGraph jungGraph) {
    	this.graph = jungGraph;
    	vv.getModel().setGraphLayout(jungGraph.getLayout());
    	MutableTransformer modelTransformer = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT);
    	double x = -modelTransformer.getTranslateX();
    	double y = -modelTransformer.getTranslateY();
    	modelTransformer.translate(x, y);
    }

	public void loadGraph(JungGraph graph, boolean needsLayout) {
		loadGraph(graph);
		FRLayout<Node, Edge> layout = new FRLayout<Node, Edge>(graph);
    	graph.setLayout(new StaticLayout<Node, Edge>(graph, layout));
        vv.getModel().setGraphLayout(layout);
	}

    
    public JungGraph getGraph() {
    	return graph;
    }
    
    public void undo() {
    	graph.undo();
    	vv.repaint();
    }
    
    public void redo() {
    	graph.redo();
    	vv.repaint();
    }
    
    class NodeFactory implements Factory<Node> {
		@Override
		public Node create() {
			return new SimpleNode("vertex");
		}

    }
    
    class EdgeFactory implements Factory<Edge> {
		@Override
		public Edge create() {
			return new SimpleEdge(null, null, "edge");
		}
    	
    }


}
