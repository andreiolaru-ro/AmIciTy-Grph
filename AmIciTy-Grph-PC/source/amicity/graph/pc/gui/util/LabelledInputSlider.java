package amicity.graph.pc.gui.util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class LabelledInputSlider extends JPanel {
	/**
	 * serial id.
	 */
	private static final long serialVersionUID = -6728229438116682620L;
	
	
	protected Label label;
	protected JSlider kSlider;
	
	public LabelledInputSlider() {
		super(new BorderLayout());
		
		label = new Label("k threshold: ");

		kSlider = new JSlider(JSlider.HORIZONTAL, 0, 10, 0);
		kSlider.setMajorTickSpacing(10);
		kSlider.setMinorTickSpacing(1);
		kSlider.setPaintTicks(true);
		kSlider.setPaintLabels(true);


		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(label);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(kSlider);
		
        setBorder(BorderFactory.createEmptyBorder(30, 20, 5, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
	}

	public void addChangeListener(ChangeListener listener) {
		kSlider.addChangeListener(listener);
	}
}
