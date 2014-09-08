package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
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

import amicity.graph.pc.MainController;
import amicity.graph.pc.QuickMatchWorker;
import amicity.graph.pc.common.MatchListener;
import amicity.graph.pc.jung.JungGraph;

public class PatternMatchViewer extends JPanel implements Observer, MatchListener, ListSelectionListener {
	private JList<JungGraph> matchList;
	private DefaultListModel<JungGraph> matchListModel;
	AutoMatchViewer viewer;
	MainController controller;
	
	public PatternMatchViewer(MainController controller) {
		controller.register(this);
		this.controller = controller;
		
		matchListModel = new DefaultListModel<JungGraph>();
		matchList = new JList<JungGraph>(matchListModel);
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setLayout(new BorderLayout());
		
		
		JScrollPane pane = new JScrollPane(matchList);
		pane.setBorder(BorderFactory.createTitledBorder("Matching patterns"));
		
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
		viewer.setMatch(new JungGraph("lol", false));
		
		List<GraphPattern> patterns = new ArrayList<GraphPattern>();
		for (JungGraph pattern : controller.getGraphExplorer().getPatterns()) {
			patterns.add(pattern.asGraphPattern());
		}
		JungGraph graph = (JungGraph) o;
		QuickMatchWorker worker = new QuickMatchWorker(this, graph.asSimpleGraph(), patterns);
		worker.execute();
	}

	@Override
	public void publishMatch(List<Match> matches) {
		for (Match match : matches) {
			JungGraph graph = new JungGraph(match.getPattern(), match.getPattern().getUnitName(), false);
			matchListModel.addElement(graph);
			graph.setNeedsLayout(true);
		}
		
		//viewer.setMatch(matchListModel.get(0));
		matchList.setSelectedIndex(0);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JungGraph graph = matchList.getSelectedValue();
		if (graph == null) {
			return;
		}
		viewer.setMatch(graph);
	}
}
