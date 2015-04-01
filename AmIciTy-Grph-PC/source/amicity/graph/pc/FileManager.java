package amicity.graph.pc;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.Graph;
import net.xqhs.graphs.graph.GraphDescription;
import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.pattern.GraphPattern;
import net.xqhs.graphs.representation.text.TextGraphRepresentation;

public class FileManager {
	String LIBRARY_FILENAME = ".graph_lib";
	MainController controller;
	FileManager(MainController controller) {
		this.controller = controller;
		controller.register(this);
	}

	public void loadJungGraph(File file) {
	}

	public JungGraph loadBareGraph(File file, boolean isPattern) {
		GraphPattern simpleGraph = new GraphPattern();
		try {
			simpleGraph.readFrom(new FileInputStream(file));
			// pass null filepath since we don't want to overwrite the file.
			return new CachedJungGraph(simpleGraph, null, file.getName(), isPattern);
		
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
				    "Unable to read from file.",
				    "I/O Error",
				    JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
	
	public JungGraph loadGraph(File file) {
		FileInputStream in;
		try {
			GraphPattern p = new GraphPattern();
			in = new FileInputStream(file);
			TextGraphRepresentation g = new TextGraphRepresentation(p);
			g.readRepresentation(in);
			if (p.getDescription() == null) {
				System.out.println("ERROR reading: " + file);
			}
			boolean isPattern = JungGraphDescription.isPattern(p.getDescription());
			
			Map<String, Point2D.Double> nodesLocation = JungGraphDescription.ParseDescription(p.getDescription());
			CachedJungGraph graph = new CachedJungGraph(p, file, file.getName(), isPattern);
			

			for (Node v : graph.getVertices()) {
				System.out.println("v=" + v.toString());
				graph.getLayout().setLocation(v, nodesLocation.get(v.toString()));
			}
			
			return graph;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Unable to read from file.",
				    "I/O Error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

		return null;
	}

	public void saveGraph(CachedJungGraph graph) {
		System.out.println("Saving graph: "+ graph.getFilepath());
		if (graph.getFilepath() == null) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			  graph.setFilepath(fileChooser.getSelectedFile());
			} else {
				System.out.println("ERROR getting filepath.");
				return;
			}
		}
		try {
			Graph p = graph.asGraphPattern();
			GraphDescription description = new JungGraphDescription(graph);
			p.setDescription(description);
			TextGraphRepresentation g = new TextGraphRepresentation(p);
			g.update();
			System.out.println(g.displayRepresentation());
			JungGraphDescription.ParseDescription(description);
			FileWriter output = new FileWriter(graph.getFilepath());
			output.write(g.displayRepresentation());
			output.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Unable to write to file.",
				    "I/O Error",
				    JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return;
		}
	}

	public void saveAsLibrary(List<JungGraph> graphs, List<JungGraph> patterns) {
		Set<String> uniqueNames = new TreeSet<String>();
		boolean error = false;
		for (JungGraph grph : graphs) {
			if (uniqueNames.contains(grph.getName())) {
				error = true;
				break;
			}
			uniqueNames.add(grph.getName());
		}
		
		for (JungGraph grph : patterns) {
			if (uniqueNames.contains(grph.getName())) {
				error = true;
				break;
			}
			uniqueNames.add(grph.getName());
		}
		if (error) {
			JOptionPane.showMessageDialog(null,
				    "Saving multiple names with the same name is not allowed.",
				    "Unique names error",
				    JOptionPane.ERROR_MESSAGE);
			return;
		}
		
	    JFileChooser chooser = new JFileChooser();
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	File directory = chooser.getSelectedFile().getParentFile();
	    	File libfile = chooser.getSelectedFile();
	    	String separator = File.separator;
	    	System.out.println("separator is: " + separator);
	    	
	    	GraphLibrary lib = new GraphLibrary();
	    	for (JungGraph grph : graphs) {
	    		CachedJungGraph c = (CachedJungGraph) grph;
	    		File name = new File(directory + separator + c.getName());
	    		c.setFilepath(name);
	    		saveGraph(c);
	    		lib.graphs.add(c.getName());
	    	}
	    	
	    	for (JungGraph grph : patterns) {
	    		CachedJungGraph c = (CachedJungGraph) grph;
	    		File name = new File(directory + separator + c.getName());
	    		c.setFilepath(name);
	    		saveGraph(c);
	    		lib.patterns.add(c.getName());
	    	}
	    	
	    	try {
	    		
				FileWriter output = new FileWriter(libfile);
				output.write(lib.toString());
				output.close();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
					    "Unable to write to file.",
					    "I/O Error",
					    JOptionPane.WARNING_MESSAGE);
				e.printStackTrace();
				return;
			}
	    }
	    
	}
	
	public List<JungGraph> loadLibrary() {
		List<JungGraph> graphs = new ArrayList<JungGraph>();
		
	    JFileChooser chooser = new JFileChooser(); 

	    if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
	    	return null;
	    }

	    File libfile = chooser.getSelectedFile();
	    File directory = libfile.getParentFile();
	    
		try {
			FileInputStream in = new FileInputStream(libfile);
			GraphLibrary lib = new GraphLibrary(in);
			in.close();
			for (String grphname : lib.graphs) {
				graphs.add(loadGraph(new File(directory + File.separator + grphname)));
			}
			for (String grphname : lib.patterns) {
				graphs.add(loadGraph(new File(directory + File.separator + grphname)));
			}
			
			return graphs;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Unable to read from file.",
				    "I/O Error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
				    "Something is wrong: " + e.getMessage(),
				    "Internal Error",
				    JOptionPane.ERROR_MESSAGE);
		}
	    
		return null;
	}

}
