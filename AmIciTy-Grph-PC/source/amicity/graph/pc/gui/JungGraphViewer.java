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

import javax.swing.JPanel;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;

import org.apache.commons.collections15.Transformer;

import amicity.graph.pc.gui.edit.EdgeTransformer;
import amicity.graph.pc.gui.edit.NodeStrokeTransformer;
import amicity.graph.pc.gui.edit.NodeTransformer;
import amicity.graph.pc.jung.JungGraph;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.renderers.DefaultEdgeLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * @author Badea Catalin
 * @author Tom Nelson
 * 
 */
public class JungGraphViewer extends JPanel {
	private static final long serialVersionUID = -2023243689258876709L;

	protected JungGraph graph;
	protected VisualizationViewer<Node, Edge> vv;
	

	public JungGraphViewer(JungGraph aGraph) {
		this.graph = aGraph;

		this.setLayout(new BorderLayout());
		vv = new VisualizationViewer<Node, Edge>(graph.getLayout());
		
		vv.setBackground(Color.white);

		vv.getRenderContext().setVertexLabelTransformer(new NodeTransformer());
		vv.getRenderContext().setEdgeLabelTransformer(new EdgeTransformer());
		vv.getRenderContext().setVertexStrokeTransformer(new NodeStrokeTransformer(vv));
		vv.getRenderContext().setEdgeLabelRenderer(new DefaultEdgeLabelRenderer(Color.YELLOW));
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		Transformer<Node, Shape> vertexSize = new Transformer<Node, Shape>() {
			public Shape transform(Node i) {
				int length = i.getLabel().length() * 10;
				length = length > 30 ? length : 30;
				return new Rectangle(-20, -10, length, 30);
			}
		};
		vv.getRenderContext().setVertexShapeTransformer(vertexSize);

		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		panel.setPreferredSize(new Dimension(400, 400));
		add(panel, BorderLayout.CENTER);
	}

	public void doGraphLayout() {
		FRLayout<Node, Edge> layout = new FRLayout<Node, Edge>(graph, vv.getSize());
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
}
