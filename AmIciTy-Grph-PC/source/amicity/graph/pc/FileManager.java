package amicity.graph.pc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import amicity.graph.pc.jung.JungGraph;
import net.xqhs.graphs.graph.SimpleGraph;

public class FileManager {
	MainController controller;
	FileManager(MainController controller) {
		this.controller = controller;
		controller.registerFileManager(this);
	}

	public void loadJungGraph(File file) {
	}

	public JungGraph loadBareGraph(File file, boolean isPattern) {
		SimpleGraph simpleGraph = new SimpleGraph();
		try {
			simpleGraph.readFrom(new FileInputStream(file));
			return new JungGraph(simpleGraph, file.getName(), isPattern);
		
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
				    "Unable to read from file.",
				    "I/O Error",
				    JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
}
