package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.xqhs.graphs.graph.Node;
import net.xqhs.graphs.matcher.Match;
import net.xqhs.graphs.pattern.GraphPattern;

import com.sun.j3d.utils.scenegraph.io.retained.Controller;

import amicity.graph.pc.JungQuickMatchWorker;
import amicity.graph.pc.MainController;
import amicity.graph.pc.QuickMatchWorker;
import amicity.graph.pc.common.JungMatchListener;
import amicity.graph.pc.common.MatchListener;
import amicity.graph.pc.jung.JungGraph;
import amicity.graph.pc.jung.JungMatch;
import amicity.graph.pc.jung.MatchPair;

public class PatternMatchViewer extends JPanel implements Observer, JungMatchListener, ListSelectionListener, PropertyChangeListener {
	private JList<MatchPair> matchList;
	private DefaultListModel<MatchPair> matchListModel;
	private List<MatchPair> allMatches;
	AutoMatchViewer viewer;
	MainController controller;
	LabelledInputField kField;
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
		
		kField = new LabelledInputField();
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

		viewer.setMatch(new JungGraph("lol", false));
		
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
		
		//viewer.setMatch(matchListModel.get(0));
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
	public void propertyChange(PropertyChangeEvent evt) {
		Long value = (Long) evt.getNewValue();
		if (value == null)
			return;
		threshold = value.intValue();
		viewer.setMatch(new JungGraph("lol", false));
		for (MatchPair pair : allMatches) {
			if (pair.match.getK() > threshold && matchListModel.contains(pair)) {
				matchListModel.removeElement(pair);
			} else if (pair.match.getK() <= threshold && !matchListModel.contains(pair)){
				matchListModel.addElement(pair);
			}
		}

		matchList.setSelectedIndex(0);
	}
}
