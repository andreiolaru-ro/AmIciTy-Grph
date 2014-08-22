package amicity.graph.pc;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class ToolBar extends JToolBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ToolBar() {
		JButton newB = new JButton("New");
		this.add(newB);
		JButton saveB = new JButton("Save");
		this.add(saveB);
		this.addSeparator();
		JButton run = new JButton("Run");
		this.add(run);
		
	}
}
