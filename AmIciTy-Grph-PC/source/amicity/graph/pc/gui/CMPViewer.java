package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.xqhs.graphs.context.ContinuousMatchingProcess;
import net.xqhs.graphs.context.ContinuousMatchingProcess.MatchNotificationReceiver;
import net.xqhs.graphs.matcher.Match;
import amicity.graph.pc.MainController;
import amicity.graph.pc.gui.util.LabelledInputSlider;
import amicity.graph.pc.jung.JungGraph;
import amicity.graph.pc.jung.MatchPair;

public class CMPViewer extends JPanel implements MatchNotificationReceiver, ListSelectionListener, ChangeListener {
	private static final long	serialVersionUID	= 1L;
	private JList<MatchPair> matchList;
	private DefaultListModel<MatchPair> matchListModel;
	private List<MatchPair> allMatches;
	AutoMatchViewer viewer;
	MainController controller;
	LabelledInputSlider kField;
	int threshold = 0;
	
	public CMPViewer() {
		allMatches = new ArrayList<MatchPair>();
		matchListModel = new DefaultListModel<MatchPair>();
		matchList = new JList<MatchPair>(matchListModel);
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		
		
		JScrollPane pane = new JScrollPane(matchList);
		pane.setBorder(BorderFactory.createTitledBorder("Matching patterns"));
		
		kField = new LabelledInputSlider();
		kField.addChangeListener(this);
		this.add(kField, BorderLayout.NORTH);
		this.add(pane, BorderLayout.CENTER);

		viewer = new AutoMatchViewer(new JungGraph("lol", true));
		viewer.setBorder(BorderFactory.createTitledBorder(""));
		this.add(viewer, BorderLayout.SOUTH);
		this.setPreferredSize(new Dimension(420, 400));
		
		matchList.addListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		MatchPair pair = matchList.getSelectedValue();
		if (pair == null) {
			return;
		}
		viewer.setMatch(pair);
	}
	
	@Override
	public void stateChanged(ChangeEvent evt) {
		JSlider slider = (JSlider) evt.getSource();
		
		threshold = slider.getValue();
		System.out.println("New threshold: " + threshold);
		for (MatchPair pair : allMatches) {
			if (pair.match.getK() > threshold && matchListModel.contains(pair)) {
				matchListModel.removeElement(pair);
			} else if (pair.match.getK() <= threshold && !matchListModel.contains(pair)) {
				matchListModel.addElement(pair);
			}
		}

		matchList.setSelectedIndex(0);
		valueChanged(null);
		
	}

	@Override
	public void receiveMatchNotification(ContinuousMatchingProcess platform, Match m)
	{
		// FIXME: how do I invalidate matches?
		JungGraph graph = new JungGraph(m.getGraph());
		JungGraph pattern = new JungGraph(m.getPattern());
		
		MatchPair pair = new MatchPair(graph, pattern, m);
		matchListModel.addElement(pair);
		System.out.println("Selected index is: " + matchList.getSelectedIndex());
		System.out.println("Model size is: " + matchListModel.size());
		if (matchList.getSelectedIndex() == -1) {
			matchList.setSelectedIndex(0);
		}
	}
}
