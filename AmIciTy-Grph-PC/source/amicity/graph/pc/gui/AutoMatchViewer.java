
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
public class AutoMatchViewer extends JPanel {
	private static final long serialVersionUID = -2023243689258876709L;

	private JungGraph graph;
	private VisualizationViewer<Node, Edge> vv;

	public AutoMatchViewer(JungGraph aGraph) {
		this.graph = aGraph;

		this.setLayout(new BorderLayout());

		vv = new VisualizationViewer<Node, Edge>(graph.getLayout());
		vv.setBackground(Color.lightGray);

		vv.getRenderContext().setVertexLabelTransformer(new NodeTransformer());
		vv.getRenderContext().setEdgeLabelTransformer(new EdgeTransformer());
		vv.getRenderContext().setVertexStrokeTransformer(
				new NodeStrokeTransformer(vv));
		vv.getRenderContext().setEdgeLabelRenderer(
				new DefaultEdgeLabelRenderer(Color.YELLOW));
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		


		Transformer<Node, Shape> vertexSize = new Transformer<Node, Shape>() {
			public Shape transform(Node i) {
				// Ellipse2D circle = new Ellipse2D.Double(-15,-15,20, 20);

				int length = i.getLabel().length() * 10;
				length = length > 30 ? length : 30;
				return new Rectangle(-20, -10, length, 30);
				// return AffineTransform.getScaleInstance(2,
				// 2).createTransformedShape(circle);
				// else if(i==2) return circle;
				// else return new Rectangle(-20, -10, 40, 20);

			}
		};
		vv.getRenderContext().setVertexShapeTransformer(vertexSize);

		final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
		vv.setPreferredSize(new Dimension(400, 400));
		add(vv, BorderLayout.CENTER);

		Factory<Node> vertexFactory = new NodeFactory();
		Factory<Edge> edgeFactory = new EdgeFactory();

		final GraphEditorEventHandler graphMouse = new GraphEditorEventHandler(
				vv.getRenderContext(), vertexFactory, edgeFactory);

		// the EditingGraphMouse will pass mouse event coordinates to the
		// vertexLocations function to set the locations of the vertices as
		// they are created
		//vv.setGraphMouse(graphMouse);
		//vv.addKeyListener(graphMouse.getModeKeyListener());

		//final ScalingControl scaler = new CrossoverScalingControl();

	}

	public void doGraphLayout() {
		FRLayout<Node, Edge> layout = new FRLayout<Node, Edge>(graph);
		graph.setLayout(new StaticLayout<Node, Edge>(graph, layout));
		vv.getModel().setGraphLayout(layout);
		graph.setLayout(layout);
		graph.setNeedsLayout(false);
	}
	
	public void setMatchAutoLayout(MatchPair match) {
		this.graph = match.pattern;
		if (match.layout == null) {
			match.layout = new FRLayout<Node, Edge>(graph, new Dimension(400, 400));
		}
		vv.getModel().setGraphLayout(match.layout);
		double scale = vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).getScale();
		vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW).setScale(0.9, 0.9, vv.getCenter());
		System.out.println("Scale is: " + scale);
	}
	
	public void setMatch(MatchPair match) {
		setMatchAutoLayout(match);
		return;
		/*this.graph = match.pattern;
		vv.getModel().setGraphLayout(this.graph.getLayout());
		vv.getRenderContext().setVertexFillPaintTransformer(new NodeColorTransformer(match));
		vv.getRenderContext().setEdgeDrawPaintTransformer(new EdgeColorTransformer(match));
		*/	
		}
	
	public void setMatch(JungGraph graph) {
		this.graph = graph;
		vv.getModel().setGraphLayout(this.graph.getLayout());
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
			// TODO: remove this;
			System.err.println("Viewer shouldn't be edittable.");
			return null;
		}

	}

	class EdgeFactory implements Factory<Edge> {
		@Override
		public Edge create() {
			return new SimpleEdge(null, null, "edge");
		}

	}

}
