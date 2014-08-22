package amicity.graph.pc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class EditorMenu extends JMenuBar {
	private static final long serialVersionUID = -7316178038249679850L;
	//MainController controller;
	
	final JFileChooser fc = new JFileChooser();
	
	public EditorMenu(final MainController controller) {
		//this.controller = aController;
		JMenu menu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New graph");
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewGraph();
			}	
		});
		menu.add(newItem);
		
		JMenuItem loadItem = new JMenuItem("Load graph");
		loadItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int returnVal = fc.showOpenDialog(null);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            controller.loadBareGraph(file);
		        }
			}
		});
		menu.add(loadItem);
		
		JMenuItem saveItem = new JMenuItem("Save");
		menu.add(saveItem);
		JMenuItem quitItem = new JMenuItem("Quit");
		menu.add(quitItem);
		add(menu);
		
		menu = new JMenu("Edit");
		add(menu);
	}
}
