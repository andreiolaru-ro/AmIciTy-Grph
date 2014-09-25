package amicity.graph.pc.gui;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;

import org.gpl.JSplitButton.JSplitButton;
import org.gpl.JSplitButton.action.SplitButtonActionListener;

import amicity.graph.pc.MainController;
import amicity.graph.pc.jung.JungGraph;

public class ToolBar extends JToolBar implements ActionListener {
	private static final long serialVersionUID = 1L;
	private MainController controller;

	private JSplitButton newB;
	private JButton saveB;
	private JButton runB;
	private JMenuItem newGraphItem;
	private JMenuItem newPatternItem;
	private JButton undoB;
	private JButton redoB;

	public ToolBar(MainController controller) {
		controller.register(this);
		this.controller = controller;

		JPopupMenu newButtonPopup = new JPopupMenu();
		newGraphItem = new JMenuItem("Context Graph");
		newGraphItem.addActionListener(this);
		newPatternItem = new JMenuItem("Graph Pattern");
		newPatternItem.addActionListener(this);
		newButtonPopup.add(newGraphItem);
		newButtonPopup.add(newPatternItem);

		newB = new JSplitButton("New");
		newB.setPopupMenu(newButtonPopup);
		newB.addSplitButtonActionListener(new SplitButtonActionListener() {

			public void buttonClicked(ActionEvent e) {
				System.out.println("new button clicked.");
			}

			public void splitButtonClicked(ActionEvent e) {
				System.out.println("Split Part licked");
			}
		});

		this.add(newB);
		saveB = new JButton("Save");
		this.add(saveB);
		this.addSeparator();
		undoB = new JButton("Undo");
		this.add(undoB);
		undoB.addActionListener(this);
		redoB = new JButton("Redo");
		redoB.addActionListener(this);
		this.add(redoB);
		this.addSeparator();
		runB = new JButton("Run");
		this.add(runB);
		runB.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == runB) {
			List<JungGraph> graphs = controller.getGraphExplorer().getGraphs();
			List<JungGraph> patterns = controller.getGraphExplorer()
					.getPatterns();

			JDialog dialog = new RunQuickMatchDialog(controller, graphs,
					patterns);
			dialog.setVisible(true);
		}
		if (e.getSource() == newGraphItem) {
			controller.createNewGraph(false);
		}
		if(e.getSource() == newPatternItem) {
			controller.createNewGraph(true);
		}
		
		if (e.getSource() == undoB) {
			System.out.println("undo");
			controller.getGraphEditor().undo();
		}
		
		if (e.getSource() == redoB) {
			controller.getGraphEditor().redo();
		}
	}
}
