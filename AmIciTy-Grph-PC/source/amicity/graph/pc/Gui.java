package amicity.graph.pc;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class Gui extends JPanel {
	GraphView graph = new GraphView();
	public Gui() {
		setLayout(new BorderLayout());
		this.add(graph, BorderLayout.CENTER);
	}
}
