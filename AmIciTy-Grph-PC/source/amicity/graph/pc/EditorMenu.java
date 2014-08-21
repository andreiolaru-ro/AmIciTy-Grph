package amicity.graph.pc;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class EditorMenu extends JMenuBar {
	private static final long serialVersionUID = -7316178038249679850L;
	
	public EditorMenu(MainController controller) {
		JMenu menu = new JMenu("File");
		add(menu);
		menu = new JMenu("Edit");
		add(menu);
	}
}
