package amicity.graph.pc;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;



public class Main {
	
	public static void buildGUI() {
		JFrame frame = new JFrame("Graph Editor");
		frame.setContentPane(new Gui());
		
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				buildGUI();
			}
		});
	}
}
