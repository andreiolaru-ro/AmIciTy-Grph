package amicity.graph.pc;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Gui extends JPanel {
	private GraphEditor graphEditor;
	private GraphList graphList;


	public Gui(MainController controller) {
		setLayout(new BorderLayout());
		
		this.add(new ToolBar(), BorderLayout.NORTH);
		graphEditor = new GraphEditor(controller);
		this.add(graphEditor, BorderLayout.CENTER);
		
		// graph list
		graphList = new GraphList(controller);
		this.add(graphList, BorderLayout.WEST);
	}
}
