package amicity.graph.pc;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.Serializable;

import net.xqhs.graphs.graph.Edge;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.pattern.GraphPattern;
import amicity.graph.pc.jung.JungGraph;

import java.util.*;

public class CachedJungGraph extends JungGraph {
	File filepath;
	boolean dirty;

	public CachedJungGraph(GraphPattern simpleGraph, File path, String name,
			boolean isPattern) {
		super(simpleGraph, name, isPattern);
		dirty = true;
	}
	
	public CachedJungGraph(String name, boolean isPattern) {
		super(name, isPattern);
		dirty = true;
	}

	public void setFilepath(File filepath) {
		this.filepath = filepath;
	}

	public File getFilepath() {
		return filepath;
	}
}
