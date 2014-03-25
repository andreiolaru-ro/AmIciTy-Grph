/*
 * Copyright (c) 2003, the JUNG Project and the Regents of the University of
 * California All rights reserved.
 * 
 * This software is open-source under the BSD license; see either "license.txt"
 * or http://jung.sourceforge.net/license.txt for a description.
 * 
 */
package app.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

import util.graph.Node;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotationControls;
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
    
    String instructions =
        "<html>"+
        "<h3>All Modes:</h3>"+
        "<ul>"+
        "<li>Right-click an empty area for <b>Create Vertex</b> popup"+
        "<li>Right-click on a Vertex for <b>Delete Vertex</b> popup"+
        "<li>Right-click on a Vertex for <b>Add Edge</b> menus <br>(if there are selected Vertices)"+
        "<li>Right-click on an Edge for <b>Delete Edge</b> popup"+
        "<li>Mousewheel scales with a crossover value of 1.0.<p>"+
        "     - scales the graph layout when the combined scale is greater than 1<p>"+
        "     - scales the graph view when the combined scale is less than 1"+

        "</ul>"+
        "<h3>Editing Mode:</h3>"+
        "<ul>"+
        "<li>Left-click an empty area to create a new Vertex"+
        "<li>Left-click on a Vertex and drag to another Vertex to create an Undirected Edge"+
        "<li>Shift+Left-click on a Vertex and drag to another Vertex to create a Directed Edge"+
        "</ul>"+
        "<h3>Picking Mode:</h3>"+
        "<ul>"+
        "<li>Mouse1 on a Vertex selects the vertex"+
        "<li>Mouse1 elsewhere unselects all Vertices"+
        "<li>Mouse1+Shift on a Vertex adds/removes Vertex selection"+
        "<li>Mouse1+drag on a Vertex moves all selected Vertices"+
        "<li>Mouse1+drag elsewhere selects Vertices in a region"+
        "<li>Mouse1+Shift+drag adds selection of Vertices in a new region"+
        "<li>Mouse1+CTRL on a Vertex selects the vertex and centers the display on it"+
        "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"+
        "</ul>"+
        "<h3>Transforming Mode:</h3>"+
        "<ul>"+
        "<li>Mouse1+drag pans the graph"+
        "<li>Mouse1+Shift+drag rotates the graph"+
        "<li>Mouse1+CTRL(or Command)+drag shears the graph"+
        "<li>Mouse1 double-click on a vertex or edge allows you to edit the label"+
        "</ul>"+
        "<h3>Annotation Mode:</h3>"+
        "<ul>"+
        "<li>Mouse1 begins drawing of a Rectangle"+
        "<li>Mouse1+drag defines the Rectangle shape"+
        "<li>Mouse1 release adds the Rectangle as an annotation"+
        "<li>Mouse1+Shift begins drawing of an Ellipse"+
        "<li>Mouse1+Shift+drag defines the Ellipse shape"+
        "<li>Mouse1+Shift release adds the Ellipse as an annotation"+
        "<li>Mouse3 shows a popup to input text, which will become"+
        "<li>a text annotation on the graph at the mouse location"+
        "</ul>"+
        "</html>";
    
    /**
     * create an instance of a simple graph with popup controls to
     * create a graph.
     * 
     */
    public GraphView() {
        
        // create a simple graph for the demo
        graph = new SparseMultigraph<String,String>();

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
//        graphMouse.setVertexLocations(vertexLocations);
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
        
        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vv, instructions);
            }});

        AnnotationControls<String,String> annotationControls = 
        	new AnnotationControls<String,String>(graphMouse.getAnnotatingPlugin());
        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
        controls.add(annotationControls.getAnnotationsToolBar());
        controls.add(help);
        add(controls, BorderLayout.SOUTH);
    }
    
  
    class VertexFactory implements Factory<String> {

    	int i=0;

		public String create() {
			String vertexName = JOptionPane.showInputDialog(vv,"Vertex Name:");
			if (vertexName.length() == 0)
				return "" + i++;
			return vertexName;
		}
    }
    
    class EdgeFactory implements Factory<String> {

    	int i=0;
    	
		public String create() {
			String edgeName = JOptionPane.showInputDialog(vv,"Edge Name: ");
			if (edgeName.length() == 0)
				return "" + i++;
			return edgeName;
		}
    }


}
