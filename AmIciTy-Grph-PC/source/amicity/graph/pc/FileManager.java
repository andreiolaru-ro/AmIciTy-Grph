package amicity.graph.pc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.SimpleGraph;
import net.xqhs.graphs.pattern.GraphPattern;

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
			in = new FileInputStream(file);
			ObjectInputStream input = new ObjectInputStream(in);
			PackedGraph packedGraph = (PackedGraph) input.readObject();
			CachedJungGraph graph = new CachedJungGraph(packedGraph);
			graph.setFilepath(file);
			return graph;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Unable to read from file.",
				    "I/O Error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
				    "Something is wrong.",
				    "Internal Error",
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
			FileOutputStream of = new FileOutputStream(graph.getFilepath());
			ObjectOutputStream output = new ObjectOutputStream(of);
			output.writeObject(graph.pack());
			
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
	    JFileChooser chooser = new JFileChooser(); 
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	File directory = chooser.getSelectedFile();
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
				FileOutputStream of = new FileOutputStream(new File(directory + separator + LIBRARY_FILENAME));
				ObjectOutputStream output = new ObjectOutputStream(of);
				output.writeObject(lib);
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
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
	    	return null;
	    }

	    File libfile = new File(chooser.getSelectedFile() + File.separator + LIBRARY_FILENAME);
		try {
			FileInputStream in = new FileInputStream(libfile);
			ObjectInputStream input = new ObjectInputStream(in);
			GraphLibrary lib = (GraphLibrary) input.readObject();
			input.close();
			for (String grphname : lib.graphs) {
				graphs.add(loadGraph(new File(chooser.getSelectedFile() + File.separator + grphname)));
			}
			for (String grphname : lib.patterns) {
				graphs.add(loadGraph(new File(chooser.getSelectedFile() + File.separator + grphname)));
			}
			
			return graphs;

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				    "Unable to read from file.",
				    "I/O Error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null,
				    "Something is wrong.",
				    "Internal Error",
				    JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	    
		return null;
	}

}
