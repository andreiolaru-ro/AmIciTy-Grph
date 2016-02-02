
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleEdge;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.graph.SimpleNode;
import net.xqhs.graphs.matcher.Match;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import amicity.graph.pc.MainController;
import amicity.graph.pc.gui.edit.EdgeTransformer;
import amicity.graph.pc.gui.edit.GraphEditorEventHandler;
import amicity.graph.pc.gui.edit.NodeStrokeTransformer;
import amicity.graph.pc.gui.edit.NodeTransformer;
import amicity.graph.pc.jung.JungGraph;
import amicity.graph.pc.jung.MatchPair;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
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
public class AutoMatchViewer extends JungGraphViewer {
	private static final long serialVersionUID = -2023243689258876709L;
	
	private Map<JungGraph, Layout<Node, Edge>> layoutMap;

	public AutoMatchViewer(JungGraph aGraph) {
		super(aGraph);
		vv.setBackground(Color.lightGray);
		layoutMap = new HashMap<JungGraph, Layout<Node, Edge>>();
	}
		
	public void setMatch(MatchPair match) {
		this.graph = match.pattern;
		if (match.layout == null) {
			match.layout = new FRLayout<Node, Edge>(graph, vv.getSize());
		}
		vv.getModel().setGraphLayout(match.layout);
		double scale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
		vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setScale(0.9, 0.9, vv.getCenter());
		System.out.println("Scale is: " + scale);
	}
	
	public JungGraph getGraph() {
		return graph;
	}
}
