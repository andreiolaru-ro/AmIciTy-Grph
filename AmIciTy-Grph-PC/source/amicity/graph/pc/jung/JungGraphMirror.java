package amicity.graph.pc.jung;

import java.util.Map;
import java.util.Queue;

import amicity.graph.pc.common.GraphEvent.Type;
import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.GraphComponent;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.matchingPlatform.TrackingGraph;
import net.xqhs.graphs.matchingPlatform.TrackingGraph.ChangeNotificationReceiver;
import net.xqhs.graphs.matchingPlatform.Transaction;
import net.xqhs.graphs.matchingPlatform.Transaction.Operation;

public class JungGraphMirror extends JungGraph implements ChangeNotificationReceiver
{
	Queue<Transaction> shadowQueue;
	
	public JungGraphMirror(TrackingGraph trackingGraph)
	{
		super(trackingGraph);
		shadowQueue = trackingGraph.getShadowAndNotifications(this);
	}
	
	@Override
	public void notifyChange()
	{
		Transaction t;
		while((t = shadowQueue.poll()) != null)
		{
			for(Map.Entry<GraphComponent, Operation> op : t.toOperationMap().entrySet())
			{
				GraphComponent component = op.getKey();
				switch(op.getValue())
				{
				case ADD:
					if(component instanceof Node)
					{
						graph.addVertex((Node) component);
					}
					else if(component instanceof Edge)
					{
						Edge edge = (Edge) component;
						graph.addEdge(edge, edge.getFrom(), edge.getTo());
					}
					break;
				case REMOVE:
					if(component instanceof Node)
					{
						graph.removeVertex((Node) component);
					}
					else if(component instanceof Edge)
					{
						Edge edge = (Edge) component;
						graph.removeEdge(edge);
					}
					break;
				default:
					throw new RuntimeException("Unexpected transaction: " + t);
				}
			}
		}
		dirty(Type.GraphStructure);
	}
}
