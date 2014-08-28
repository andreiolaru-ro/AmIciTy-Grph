package amicity.graph.pc;


import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import amicity.graph.pc.gui.EditorMenu;
import amicity.graph.pc.gui.Gui;

import org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel;

public class Main {
    public static void useSubstanceLAF() {
        try {

            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());

        } catch (Exception e) {
            e.getStackTrace();
        }

        try {

            JFrame.setDefaultLookAndFeelDecorated(true);
            //UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
            //UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel");
            //UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel");
            UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel");
            //UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
            //UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel");
            //UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel");
        
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
	
	public static void buildGUI(MainController controller) {
		 useSubstanceLAF();
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
