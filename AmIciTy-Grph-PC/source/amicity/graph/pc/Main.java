package amicity.graph.pc;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import amicity.graph.pc.gui.EditorMenu;
import amicity.graph.pc.gui.Gui;



public class Main {
	
	public static void buildGUI(MainController controller) {
		JFrame frame = new JFrame("Graph Editor");
		frame.setContentPane(new Gui(controller));
		
		frame.setSize(1000, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setJMenuBar(new EditorMenu(controller));
	}


	public static void main(String[] args) {
		final MainController mainController = new MainController();
		FileManager fileManager = new FileManager(mainController);
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildGUI(mainController);
				mainController.init();
			}
		});
	}
}
