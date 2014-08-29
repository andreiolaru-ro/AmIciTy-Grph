package amicity.graph.pc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import amicity.graph.pc.MainController;
import amicity.graph.pc.jung.JungGraph;

public class ToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	private MainController controller;
	
	private JButton newB;
	private JButton saveB;
	private JButton runB;
	private JButton undoB;
	private JButton redoB;

	public ToolBar(MainController controller) {
		controller.register(this);
		this.controller = controller;
		
		newB = new JButton("New");
		this.add(newB);
		saveB = new JButton("Save");
		this.add(saveB);
		this.addSeparator();
		undoB = new JButton("Undo");
		this.add(undoB);
		redoB = new JButton("Redo");
		this.add(redoB);
		this.addSeparator();
		JButton runB = new JButton("Run");
		this.add(runB);
		runB.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<JungGraph> graphs = controller.getGraphExplorer().getGraphs();
		List<JungGraph> patterns = controller.getGraphExplorer().getPatterns();
		
		JDialog dialog = new RunQuickMatchDialog(controller, graphs, patterns);
		dialog.setVisible(true);
	}
}
