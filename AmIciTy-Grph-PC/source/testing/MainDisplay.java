package testing;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;
import java.io.ByteArrayInputStream;

import core.interfaces.Logger;
import core.interfaces.Logger.Level;

import util.logging.Log;
import util.logging.Unit.UnitConfigData;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

import util.graph.Edge;
import util.graph.ContextGraph;
import util.graph.Node;

/**
 * @author 'Nihal Ablachim'
 * The class which contains the main method.
 */
public class MainDisplay {
	private static String	unitName	= "graphMatcherTestMain";
	
	public static ContextGraph createCONTEXTGraph()
	{
		Logger log = Log.getLogger(unitName);
		log.trace("Hello World");
		ContextGraph graph=new ContextGraph();
		/*Node node1,node2,node3;
	    Edge edge1_2,edge1_3;
	    
	    node1=new Node("Nihal");
	    node2=new Node("student");
	    node3=new Node("Bucharest");
	    
	    edge1_2=new Edge(node1,node2,"isa");
	    edge1_3=new Edge(node1,node3,"in");
	    
	    graph.addNode(node1);
	    graph.addNode(node2);
	    graph.addNode(node3);
	    
	    graph.addEdge(edge1_2);
	    graph.addEdge(edge1_3);*/
	    
	    String input = "";
		input += "AIConf -> conftime;";
		input += "conftime -isa> interval;";
		input += "AIConf -> CFP;";
		input += "CFP -> AIConf;";
		input += "CFP -isa> document;";
		input += "CFP -contains> 05012011;";
		input += "05012011 -isa> date;";
		input += "CFP -contains> 30032011;";
		input += "30032011 -isa> date;";
		input += "AIConf -> 30032011;";
		input += "CFP -contains> conftime;";
		graph = graph.readFrom(new ByteArrayInputStream(input.getBytes()), new UnitConfigData().setName("G").setLevel(Level.INFO).setLink(unitName));
		log.info(graph.toString());
	    
	    return graph;
	}
	
	public static void main(String[] args) {
    
	JungGraphDisplay jgd=new JungGraphDisplay();
	jgd.setGraph(createCONTEXTGraph());
	jgd.createJungGraph();
	jgd.setLayout();
	jgd.setBackgroundColor(new Color(239,239,239));
	jgd.setLayoutSize(600,600);
	Iterator<Node> iterator=jgd.jungGraph.getVertices().iterator();
	jgd.setNodeLabelPosition(iterator.next(), Position.CNTR);
	jgd.setNodesColor(new Color(255,255,198));
	//jgd.setEdgesColor(new Color(61,37,24));
	jgd.setNodeShape(iterator.next(),new Ellipse2D.Double(-15,-15,40, 20));
	jgd.addNode(new Node("Adrian"));
	jgd.createFrame("Context Graph Display");jgd.zoomIn();jgd.zoomOut();

}

}
