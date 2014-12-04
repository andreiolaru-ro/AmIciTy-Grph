package amicity.graph.pc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraphLibrary implements Serializable {
	public List<String> graphs;
	public List<String> patterns;
	
	public GraphLibrary() {
		graphs = new ArrayList<String>();
		patterns = new ArrayList<String>();
	}
}
