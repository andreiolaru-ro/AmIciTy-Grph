package testing.nihal;

import java.awt.Dimension;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.AggregateLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;


public class layouts {
	private Forest<Integer, String> g = new DelegateForest<Integer, String>();
	//private Layout<Integer, String> layout = new CircleLayout(g);
	//private Layout<Integer, String> layout = new SpringLayout(g);
	private TreeLayout<Integer, String> layout = new TreeLayout<Integer,String>(g);
	public void Initialize()
	{
		for(int i=0;i<=15;i++)
			g.addVertex((Integer) i);
		
		for(int i=1;i<=30;i++)
			g.addEdge(((Integer)i).toString(), (i-1), i);
			
		g.addEdge("89", 15, 7);
		g.addEdge("100", 3, 10);
		
		//layout.setSize(new Dimension(300,300)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		BasicVisualizationServer<Integer,String> vv =
		new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(350,350)); //Sets the viewing area size
		//JFrame frame = new JFrame("Circle Layout");
		JFrame frame = new JFrame("Spring Layout");
		//JFrame frame = new JFrame("FRLayout");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
		frame.setVisible(true);

	}
	
}