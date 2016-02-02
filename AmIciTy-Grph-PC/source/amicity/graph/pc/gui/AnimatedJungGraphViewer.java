package amicity.graph.pc.gui;

import java.util.Observable;
import java.util.Observer;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.util.Relaxer;
import edu.uci.ics.jung.algorithms.layout.util.VisRunner;
import edu.uci.ics.jung.algorithms.util.IterativeContext;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.util.Animator;
import amicity.graph.pc.gui.edit.GraphEditorEventHandler;
import amicity.graph.pc.gui.edit.GraphEditorEventHandler.FeatureMask;
import amicity.graph.pc.jung.JungGraph;

public class AnimatedJungGraphViewer extends JungGraphViewer implements Observer
{
	private static final long	serialVersionUID	= -4397951128112758926L;

	public AnimatedJungGraphViewer(JungGraph aGraph)
	{
		super(aGraph);
		layout = new FRLayout<Node, Edge>(graph);
		vv.setGraphLayout(layout);
		this.graph.addObserver(this);
	}
	
	@Override
	public void doGraphLayout() {
		layout.setSize(vv.getSize());
		layout.initialize();
		Relaxer relaxer = new VisRunner((IterativeContext) layout);
		relaxer.stop();
		relaxer.prerelax();
		
		StaticLayout<Node, Edge> staticLayout = new StaticLayout<Node,Edge>(graph, layout, vv.getSize());
		LayoutTransition<Node,Edge> lt = new LayoutTransition<Node,Edge>(vv, vv.getGraphLayout(),
											staticLayout);
		Animator animator = new Animator(lt);
		animator.start();
		vv.repaint();
		
		FeatureMask mask = new FeatureMask(FeatureMask.TRANSLATE_SUPPORT | FeatureMask.SCALING_SUPPORT);
		GraphEditorEventHandler eventHandler = new GraphEditorEventHandler(vv.getRenderContext(),
													null, null, mask);
		vv.setGraphMouse(eventHandler);
	}
	

	@Override
	public void update(Observable o, Object arg)
	{
		doGraphLayout();
	}
}
