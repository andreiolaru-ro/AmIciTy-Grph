package testing;

import javax.swing.JFrame;

/**
 * @author 'Nihal Ablachim'
 * The class which contains the main method.
 */
public class MainDisplay {
	public static void main(String[] args) {
	
    JungGraphDisplay jgd=new JungGraphDisplay();
	JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//frame.getContentPane().add(jgd.bvs);
	frame.pack();
	frame.setVisible(true);	
}

}
