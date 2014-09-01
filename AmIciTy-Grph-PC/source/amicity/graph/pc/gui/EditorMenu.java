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
	JMenuItem loadItem;
	JMenu importItem;
	JMenuItem loadGraphItem;
	JMenuItem loadPatternItem;
	JMenuItem saveItem;
	
	
	// Edit
	JMenuItem undoItem;
	JMenuItem redoItem;
	
	final JFileChooser fc = new JFileChooser();
	
	public EditorMenu(final MainController controller) {
		this.controller = controller;
		JMenu menu = new JMenu("File");
		JMenuItem newItem = new JMenuItem("New graph");
		newItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.createNewGraph(false);
			}	
		});
		menu.add(newItem);
		
		loadItem = new JMenuItem("Load");
		loadItem.addActionListener(this);
		menu.add(loadItem);
		
		importItem = new JMenu("Import..");
		loadGraphItem = new JMenuItem("Import context graph");
		loadGraphItem.addActionListener(this);
		loadPatternItem = new JMenuItem("Import pattern");
		loadPatternItem.addActionListener(this);
		importItem.add(loadGraphItem);
		importItem.add(loadPatternItem);
		

		menu.add(importItem);
		
		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(this);
		menu.add(saveItem);
		JMenuItem quitItem = new JMenuItem("Quit");
		menu.add(quitItem);
		add(menu);
		
		menu = new JMenu("Edit");
		undoItem = new JMenuItem("Undo");
		menu.add(undoItem);
		undoItem.addActionListener(this);
		redoItem = new JMenuItem("Redo");
		menu.add(redoItem);
		redoItem.addActionListener(this);
		add(menu);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == loadItem) {
			int returnVal = fc.showOpenDialog(null);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				controller.loadGraphFromFile(file, false);
			}
		}
		
		if (e.getSource() == loadGraphItem) {
			int returnVal = fc.showOpenDialog(null);
	
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				controller.loadBareGraphFromFile(file, false);
			}
		}
		if (e.getSource() == loadPatternItem) {
			int returnVal = fc.showOpenDialog(null);
	
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				controller.loadBareGraphFromFile(file, true);
			}
		}
		
		if (e.getSource() == saveItem) {
			System.out.println("save!");
			controller.saveCurrentGraph();
		}
		
		if (e.getSource() == undoItem) {
			controller.getGraphEditor().undo();
		}
		
		if (e.getSource() == redoItem) {
			controller.getGraphEditor().redo();
		}
	}
}
