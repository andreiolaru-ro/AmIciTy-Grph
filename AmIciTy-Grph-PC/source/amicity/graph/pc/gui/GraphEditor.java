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
import amicity.graph.pc.jung.SettableNodeP;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
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
public class GraphEditor extends JungGraphViewer {
	private static final long serialVersionUID = -2023243689258876709L;

	
	public GraphEditor(JungGraph aGraph) {
		super(aGraph);
		Factory<Node> vertexFactory = new NodeFactory(this.graph);
		Factory<Edge> edgeFactory = new EdgeFactory();

		final GraphEditorEventHandler eventHandler = new GraphEditorEventHandler(
				vv.getRenderContext(), vertexFactory, edgeFactory);

		// the EditingGraphMouse will pass mouse event coordinates to the
		// vertexLocations function to set the locations of the vertices as
		// they are created
		vv.setGraphMouse(eventHandler);
		vv.addKeyListener(eventHandler.getModeKeyListener());

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
				scaler.scale(vv, 1 / 1.1f, vv.getCenter());
			}
		});

		JButton layoutButton = new JButton("force-directed layout");
		layoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FRLayout<Node, Edge> newLayout = new FRLayout<Node, Edge>(graph, vv.getSize());
				layout = newLayout;
				vv.getModel().setGraphLayout(layout);
			}
		});
		
		JButton circleButton = new JButton("circle layout");
		circleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// layout = new CircleLayout<Node,Edge>(graph);
				CircleLayout<Node, Edge> newLayout = new CircleLayout<Node, Edge>(graph);
				layout = newLayout;
				vv.getModel().setGraphLayout(layout);
			}
		});

		JPanel controls = new JPanel();
		controls.add(plus);
		controls.add(minus);
		controls.add(layoutButton);
		controls.add(circleButton);
		add(controls, BorderLayout.SOUTH);
	}


	public JungGraph getGraph() {
		return graph;
	}
	
	public void setGraph(JungGraph graph) {
		this.graph = graph;
		doGraphLayout();
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
		JungGraph graph;
		
		public NodeFactory(JungGraph graph) {
			this.graph = graph;
		}
		
		public String getNextNameFor(String baseName) {
			int i = 2;
			String name = baseName;
			while (graphContains(name)) {
				name = baseName + i++;
			}
			
			return name;
		}
		
		boolean graphContains(String name) {
			for (Node node : graph.getVertices()) {
				if (node.getLabel().equals(name)) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		public Node create() {

			String name = getNextNameFor("vertex");
			return new SettableNodeP(name);
		}

	}

	class EdgeFactory implements Factory<Edge> {
		@Override
		public Edge create() {
			return new SimpleEdge(null, null, "edge");
		}

	}

}
