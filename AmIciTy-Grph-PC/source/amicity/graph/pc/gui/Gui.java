package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import amicity.graph.pc.MainController;

public class Gui extends JPanel {
	private GraphEditor graphEditor;
	private GraphExplorer graphExplorer;


	public Gui(MainController controller) {
		setLayout(new BorderLayout());
		
		this.add(new ToolBar(controller), BorderLayout.NORTH);
		graphEditor = new GraphEditor(controller);
		this.add(graphEditor, BorderLayout.CENTER);
			
		//graph explorer
		graphExplorer = new GraphExplorer(controller);
		this.add(graphExplorer, BorderLayout.WEST);
	}
}
