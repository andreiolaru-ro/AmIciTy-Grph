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
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainController controller;

	public ToolBar(MainController controller) {
		controller.registerToolBar(this);
		this.controller = controller;
		
		JButton newB = new JButton("New");
		this.add(newB);
		JButton saveB = new JButton("Save");
		this.add(saveB);
		this.addSeparator();
		JButton run = new JButton("Run");
		this.add(run);
		run.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<JungGraph> graphs = controller.getGraphExplorer().getGraphs();
		List<JungGraph> patterns = controller.getGraphExplorer().getPatterns();
		
		JDialog dialog = new RunQuickMatchDialog(null, graphs, patterns);
		dialog.setVisible(true);
	}
}
