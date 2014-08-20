/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package amicity.graph.pc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleNode;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import amicity.graph.pc.jung.JungGraph;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author Badea Catalin
 * @author Tom Nelson
 * 
 */
public class GraphView extends JPanel {
	private static final long serialVersionUID = -2023243689258876709L;

    JungGraph graph;
    
    AbstractLayout<Node,Edge> layout;
    VisualizationViewer<Node,Edge> vv;
    
    
    public GraphView() {
        graph = JungGraph.createJungGraph();
        this.layout = new StaticLayout<Node,Edge>(graph, 
        	new Dimension(400,400));
        
        this.setLayout(new BorderLayout());
        

        vv =  new VisualizationViewer<Node,Edge>(layout);
        vv.setBackground(Color.lightGray);


       vv.getRenderContext().setVertexLabelTransformer(new NodeTransformer());
       vv.getRenderContext().setEdgeLabelTransformer(new EdgeTransformer());
        
		vv.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.YELLOW));
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);


		Transformer<Node,Shape> vertexSize = new Transformer<Node,Shape>(){
            public Shape transform(Node i){
                //Ellipse2D circle = new Ellipse2D.Double(-15,-15,40, 20);
                return new Rectangle(-20,-10,i.getLabel().length()*10,30);
                //if(i==1) return AffineTransform.getScaleInstance(3, 3).createTransformedShape(circle);
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
        
        final GraphEditorController graphMouse = 
        	new GraphEditorController(vv.getRenderContext(), vertexFactory, edgeFactory);

        
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
        
        JButton circleLayout = new JButton("perform layout");
        circleLayout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	layout = new CircleLayout<Node,Edge>(graph);
            	//layout = new FRLayout<String,String>(graph, new Dimension(600, 600));
                vv.getModel().setGraphLayout(layout, new Dimension(600, 600));
            }
        });


        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        controls.add(circleLayout);;
        add(controls, BorderLayout.SOUTH);
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
