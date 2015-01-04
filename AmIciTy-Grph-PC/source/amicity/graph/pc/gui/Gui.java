package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import amicity.graph.pc.MainController;
import amicity.graph.pc.jung.JungGraph;

public class Gui extends JPanel {
	GraphExplorer graphExplorer;
	private TabbedGraphEditor tabbedPane;


	public Gui(MainController controller) {
		setLayout(new BorderLayout());
		
		this.add(new ToolBar(controller), BorderLayout.NORTH);
		
		
		tabbedPane = new TabbedGraphEditor(controller);
		this.add(tabbedPane, BorderLayout.CENTER);

		//graph explorer
		graphExplorer = new GraphExplorer();
		controller.register(graphExplorer);
		
		JPanel sideBar = new JPanel();
		sideBar.setLayout(new BoxLayout(sideBar, BoxLayout.Y_AXIS));
		JLabel sideBarTitle = new JLabel("<html><b>OPEN FILES</b></html>");
		sideBarTitle.setLabelFor(graphExplorer);
		sideBar.add(sideBarTitle);
		sideBarTitle.add(Box.createRigidArea(new Dimension(0, 5)));
		graphExplorer.setAlignmentX(LEFT_ALIGNMENT);
		sideBar.add(graphExplorer);

		this.add(sideBar, BorderLayout.WEST);
		PatternMatchViewer patternViewer = new PatternMatchViewer(controller);
		patternViewer.setVisible(false);
		this.add(patternViewer, BorderLayout.EAST);
	}
}
