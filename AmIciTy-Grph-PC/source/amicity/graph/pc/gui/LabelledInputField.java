package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

public class LabelledInputField extends JPanel {
	protected Label label;
	protected JFormattedTextField textField;
	
	public LabelledInputField() {
		super(new BorderLayout());
		
		label = new Label("k threshold: ");
		textField = new JFormattedTextField(NumberFormat.getNumberInstance());
		textField.setValue(new Long(0));
		
		textField.setColumns(3);
		
		JPanel labelPane = new JPanel(new GridLayout(0,1));
		labelPane.add(label);
		
		JPanel fieldPane = new JPanel(new GridLayout(0,1));
		fieldPane.add(textField);
		
        setBorder(BorderFactory.createEmptyBorder(30, 20, 5, 20));
        add(labelPane, BorderLayout.CENTER);
        add(fieldPane, BorderLayout.LINE_END);
	}
	
	public void addChangeListener(PropertyChangeListener listener) {
		textField.addPropertyChangeListener("value", listener);
	}
}
