package amicity.graph.pc;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import amicity.graph.pc.gui.util.Pair;
import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.GraphDescription;
import net.xqhs.graphs.graph.Node;

public class JungGraphDescription extends GraphDescription {
	JungGraph graph;
	public JungGraphDescription(JungGraph graph) {
		this.graph = graph;
	}
	
	public static Map<String, Point2D.Double> ParseDescription(GraphDescription descr) {
		String[] points = descr.toString().split("\\)");
		Map<String, Point2D.Double> result = new HashMap<String, Point2D.Double>();
		boolean skip = true;
		for (String p : points) {
			if (skip) {
				skip = false;
				continue;
			}
			Entry<String, String> entry = parseKeyValue(p.substring(1));
			
			String [] coords = entry.getValue().split(", ");
			System.out.println(coords[0]);
			System.out.println(coords[1]);
			Point2D.Double res = new Point2D.Double(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
			result.put(entry.getKey(), res);
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
			String value = p.getX() +", " + p.getY();
			System.out.println(node);
			String blob = keyValue(node.toString(), value);
			buffer.append("(" + blob + ")");
		}
		
		return buffer.toString();
	}

	public static Entry<String, String> parseKeyValue(String text) {
		String[] kv = text.split("=");

		return new Pair<String, String>(kv[0], kv[1]);
	}
	
	public String keyValue(String key, String value) {
		return key + "=" + value;
	}


}

