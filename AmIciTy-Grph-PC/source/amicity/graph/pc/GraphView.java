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
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.Graphs;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author Badea Catalin
 * @author Tom Nelson
 * 
 */
public class GraphView extends JPanel {
	private static final long serialVersionUID = -2023243689258876709L;

    Graph<String,String> graph;
    
    AbstractLayout<String,String> layout;
    VisualizationViewer<String,String> vv;
    
    
    /**
     * create an instance of a simple graph with popup controls to
     * create a graph.
     * 
     */
    public GraphView() {
        
        // create a simple graph for the demo
        graph = Graphs.<String,String>synchronizedDirectedGraph(new DirectedSparseMultigraph<String,String>());

        this.layout = new StaticLayout<String,String>(graph, 
        	new Dimension(400,400));
        
        this.setLayout(new BorderLayout());
        

        vv =  new VisualizationViewer<String,String>(layout);
        vv.setBackground(Color.lightGray);

        vv.getRenderContext().setVertexLabelTransformer(MapTransformer.<String,String>getInstance(
        		LazyMap.<String,String>decorate(new HashMap<String,String>(), new ToStringLabeller<String>())));
        
        vv.getRenderContext().setEdgeLabelTransformer(MapTransformer.<String,String>getInstance(
        		LazyMap.<String,String>decorate(new HashMap<String,String>(), new ToStringLabeller<String>())));

        vv.setVertexToolTipTransformer(vv.getRenderContext().getVertexLabelTransformer());
        
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.YELLOW));
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);


		Transformer<String,Shape> vertexSize = new Transformer<String,Shape>(){
            public Shape transform(String i){
                //Ellipse2D circle = new Ellipse2D.Double(-15,-15,40, 20);
                return new Rectangle(-20,-10,i.length()*10,30);
                //if(i==1) return AffineTransform.getScaleInstance(3, 3).createTransformedShape(circle);
                //else if(i==2) return circle;
                //else return new Rectangle(-20, -10, 40, 20);
            }
        };
        vv.getRenderContext().setVertexShapeTransformer(vertexSize);
		
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        panel.setPreferredSize(new Dimension(400, 400));
        add(panel, BorderLayout.CENTER);
        Factory<String> vertexFactory = new VertexFactory();
        Factory<String> edgeFactory = new EdgeFactory();
        
        final EditingModalGraphMouse<String,String> graphMouse = 
        	new CustomEditingModalGraphMouse<String, String>(vv.getRenderContext(), vertexFactory, edgeFactory);

        
        // the EditingGraphMouse will pass mouse event coordinates to the
        // vertexLocations function to set the locations of the vertices as
        // they are created
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        
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
            	layout = new CircleLayout<String,String>(graph);
            	//layout = new FRLayout<String,String>(graph, new Dimension(600, 600));
                vv.getModel().setGraphLayout(layout, new Dimension(600, 600));
            }
        });
     
        


        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        controls.add(circleLayout);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
        add(controls, BorderLayout.SOUTH);
    }
    
  
    class VertexFactory implements Factory<String> {

    	int i=0;

		public String create() {
			String vertexName = JOptionPane.showInputDialog(vv,"Vertex Name:");
			if (vertexName == null || vertexName.length() == 0)
				return "" + i++;
			return vertexName;
		}
    }
    
    class EdgeFactory implements Factory<String> {

    	int i=0;
    	
		public String create() {
			String edgeName = JOptionPane.showInputDialog(vv,"Edge Name: ");
			if (edgeName == null || edgeName.length() == 0)
				return "" + i++;
			return edgeName;
		}
    }


}
