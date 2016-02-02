package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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

import net.xqhs.graphs.matcher.Match;
import amicity.graph.pc.JungQuickMatchWorker;
import amicity.graph.pc.MainController;
import amicity.graph.pc.common.JungMatchListener;
import amicity.graph.pc.gui.util.LabelledInputSlider;
import amicity.graph.pc.jung.JungGraph;
import amicity.graph.pc.jung.JungMatch;
import amicity.graph.pc.jung.MatchPair;

public class PatternMatchViewer extends JPanel implements Observer, JungMatchListener, ListSelectionListener, ChangeListener {
	private static final long	serialVersionUID	= 1L;
	private JList<MatchPair> matchList;
	private DefaultListModel<MatchPair> matchListModel;
	private List<MatchPair> allMatches;
	AutoMatchViewer viewer;
	MainController controller;
	LabelledInputSlider kField;
	int threshold = 0;
	
	public PatternMatchViewer(MainController controller) {
		controller.register(this);
		this.controller = controller;
		
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
	public void update(Observable o, Object arg) {
		matchListModel.clear();
		allMatches.clear();
	
		// clear viewer
		
		JungGraph graph = (JungGraph) o;
		List<JungMatch> matches = new ArrayList<JungMatch>();
		for (JungGraph pattern : controller.getGraphExplorer().getPatterns()) {
			matches.add(new JungMatch(graph, pattern));
		}
		
		JungQuickMatchWorker worker = new JungQuickMatchWorker(this, matches);
		worker.execute();
	}

	@Override
	public void publish(List<JungMatch> matches) {
		for (JungMatch match : matches) {
			for (Match m : match.getMatches()) {
				MatchPair pair = new MatchPair(match.getGraph(), match.getPattern(), m);
				allMatches.add(pair);
				if (pair.match.getK() <= threshold) {
					matchListModel.addElement(pair);
				}	
			}
		}
		
		matchList.setSelectedIndex(0);
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
}
