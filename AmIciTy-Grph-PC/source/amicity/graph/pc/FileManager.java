package amicity.graph.pc;

import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.SimpleGraph;

public class FileManager {
	MainController controller;
	FileManager(MainController controller) {
		this.controller = controller;
		controller.register(this);
	}

	public void loadJungGraph(File file) {
	}

	public JungGraph loadBareGraph(File file, boolean isPattern) {
		SimpleGraph simpleGraph = new SimpleGraph();
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
	
	public JungGraph loadGraph(File file, boolean isPattern) {
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
		if (graph.getFilepath() == null) {
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			  graph.setFilepath(fileChooser.getSelectedFile());
			} else {
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


}
