package amicity.graph.pc.jung;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.GraphComponent;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.matchingPlatform.TrackingGraph;
import net.xqhs.graphs.matchingPlatform.TrackingGraph.ChangeNotificationReceiver;
import net.xqhs.graphs.matchingPlatform.Transaction;

public class JungGraphMirror extends JungGraph implements ChangeNotificationReceiver
{
	public JungGraphMirror(TrackingGraph trackingGraph)
	{
		super(trackingGraph);
		trackingGraph.registerChangeNotificationReceiver(this);
	}

	@Override
	public void notifyChange(Transaction t)
	{
		switch(t.getOperation()) {
		case ADD:
			GraphComponent component = t.getComponent();
			if (component instanceof Node) {
				graph.addVertex((Node) component);
			} else if (component instanceof Edge) {
				Edge edge = (Edge) component;
				graph.addEdge(edge, edge.getFrom(), edge.getTo());
			}
			break;
		case REMOVE:
			break;
		default:
			throw new RuntimeException("Unexpected transaction: " + t);
		}
	}
}
