package amicity.graph.pc;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;



public class Main {
	
	public static void buildGUI(MainController controller) {
		JFrame frame = new JFrame("Graph Editor");
		frame.setContentPane(new Gui(controller));
		
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setJMenuBar(new EditorMenu(controller));
	}


	public static void main(String[] args) {
		final MainController mainController = new MainController();
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildGUI(mainController);
			}
		});
	}
}
