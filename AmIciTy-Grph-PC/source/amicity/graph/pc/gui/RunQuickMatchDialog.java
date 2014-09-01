package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import amicity.graph.pc.MainController;
import amicity.graph.pc.jung.JungGraph;

public class RunQuickMatchDialog extends JDialog
								 implements ActionListener,
								 			PropertyChangeListener {
	private static final long serialVersionUID = -1919688825249015165L;

	private JOptionPane options;
	private String[] buttons = {"Cancel", "Run"};

	private MainController controller;
	private DialogBody body;
	
	private class DialogBody extends JPanel {
		JList<JungGraph> graphs;
		JList<JungGraph> patterns;
		
		public DialogBody(List<JungGraph> graphs, List<JungGraph> patterns) {
			DefaultListModel<JungGraph> graphModel = new DefaultListModel();
			DefaultListModel<JungGraph> patternModel = new DefaultListModel<JungGraph>();
			
			for (JungGraph g : graphs) {
				graphModel.addElement(g);
			}

			
			for (JungGraph p : patterns) {
				patternModel.addElement(p);
			}

					
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			
			JPanel left = new JPanel();
			left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
			JPanel right = new JPanel();
			right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));

			this.graphs = new JList<JungGraph>(graphModel);
			if (graphModel.size() > 0) {
				this.graphs.setSelectedIndex(0);
			}
			JLabel graphLabel = new JLabel("Graph:");
			graphLabel.setLabelFor(this.graphs);
			graphLabel.setAlignmentX(LEFT_ALIGNMENT);
			left.add(graphLabel);
			
			left.add(Box.createRigidArea(new Dimension(0, 5)));
			JScrollPane leftScroller = new JScrollPane(this.graphs);
			leftScroller.setAlignmentX(LEFT_ALIGNMENT);
			left.add(leftScroller);
			
			// patterns
			this.patterns = new JList<JungGraph>(patternModel);			
			if (patternModel.size() > 0) {
				this.patterns.setSelectedIndex(0);
			}
			JLabel patternLabel = new JLabel("Pattern:");
			graphLabel.setLabelFor(this.patterns);
			graphLabel.setAlignmentX(LEFT_ALIGNMENT);
			right.add(patternLabel);
			right.add(Box.createRigidArea(new Dimension(0, 5)));
			JScrollPane rightScroller = new JScrollPane(this.patterns);
			rightScroller.setAlignmentX(LEFT_ALIGNMENT);
			right.add(rightScroller);
			
			add(left);
			add(right);
			pack();
		}
	
		public JungGraph getGraph() {
			return graphs.getSelectedValue();
		}
		
		public JungGraph getPattern() {
			return patterns.getSelectedValue();
		}
	}
	
	public RunQuickMatchDialog(MainController controller, List<JungGraph> graphs, List<JungGraph> patterns) {
		super((Frame)null, true);
		this.controller = controller;
		setAlwaysOnTop(true);
		
		body = new DialogBody(graphs, patterns);
		options = new JOptionPane(body, JOptionPane.DEFAULT_OPTION, JOptionPane.YES_NO_OPTION, null, buttons, buttons[1]);
		setContentPane(options);
		pack();
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	close();
            }
        });
        
        options.addPropertyChangeListener(this);
	}
	

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (!isVisible())
			return;
		Object value = options.getValue();
		if (value.equals(buttons[0]))
			close();
		
		if (value.equals(buttons[1])) {
			doRun();
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
	
	private void close() {
        setVisible(false);
	}
	
	private void doRun() {
		controller.runQuickMatch(body.getGraph(), body.getPattern());
		close();
	}
}
