package amicity.graph.pc;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.GraphDescription;
import net.xqhs.graphs.graph.Node;

public class JungGraphDescription extends GraphDescription {
	JungGraph graph;
	public JungGraphDescription(JungGraph graph) {
		this.graph = graph;
	}
	
	public static List<Point2D.Double> ParseDescription(GraphDescription descr) {
		String[] points = descr.toString().split("\\)");
		List<Point2D.Double> result = new ArrayList<Point2D.Double>();
		boolean skip = true;
		for (String p : points) {
			if (skip) {
				skip = false;
				continue;
			}
			String [] coords = p.substring(1).split(", ");
			System.out.println(coords[0]);
			System.out.println(coords[1]);
			Point2D.Double res = new Point2D.Double(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
			result.add(res);
		}
		
		return result;
	}
	
	public static boolean isPattern(GraphDescription descr) {
		String[] entries = descr.toString().split("\\)");
		return Boolean.parseBoolean(entries[0].substring(1));
	}
	
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("(" + graph.isPattern() + ")");
		for (Node node : graph.getVertices()) {
			Point2D p = graph.getLayout().transform(node);
			buffer.append("(" + p.getX() + ", " + p.getY() + ")");
		}
		
		return buffer.toString();
	}

	


}

