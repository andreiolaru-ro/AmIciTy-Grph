package amicity.graph.pc.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import amicity.graph.pc.MainController;

public class EditorMenu extends JMenuBar implements ActionListener {
	private static final long serialVersionUID = -7316178038249679850L;
	MainController controller;
	JMenu loadItem;
	JMenuItem loadGraphItem;
	JMenuItem loadPatternItem;
	
	final JFileChooser fc = new JFileChooser();
	
	public EditorMenu(final MainController controller) {
		this.controller = controller;
		JMenu menu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New graph");
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewGraph();
			}	
		});
		menu.add(newItem);
		
		loadItem = new JMenu("Load..");
		loadGraphItem = new JMenuItem("Load context graph");
		loadGraphItem.addActionListener(this);
		loadPatternItem = new JMenuItem("Load pattern");
		loadPatternItem.addActionListener(this);
		loadItem.add(loadGraphItem);
		loadItem.add(loadPatternItem);
		

		menu.add(loadItem);
		
		JMenuItem saveItem = new JMenuItem("Save");
		menu.add(saveItem);
		JMenuItem quitItem = new JMenuItem("Quit");
		menu.add(quitItem);
		add(menu);
		
		menu = new JMenu("Edit");
		add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loadGraphItem) {
			int returnVal = fc.showOpenDialog(null);
	
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				controller.loadBareGraph(file, false);
			}
		}
		if (e.getSource() == loadPatternItem) {
			int returnVal = fc.showOpenDialog(null);
	
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				controller.loadBareGraph(file, true);
			}
		}
	}
}
