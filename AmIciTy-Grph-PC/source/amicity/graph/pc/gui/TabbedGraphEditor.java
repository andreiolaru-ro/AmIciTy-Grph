package amicity.graph.pc.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import amicity.graph.pc.CachedJungGraph;
import amicity.graph.pc.MainController;
import amicity.graph.pc.jung.JungGraph;

public class TabbedGraphEditor extends JTabbedPane {
	private class TabTitle extends JPanel {
		JLabel label;

		public TabTitle(String name) {
			label = new JLabel(name);
			this.add(label);
		}
	}

	private static final long serialVersionUID = 8734686913334030625L;
	private boolean dragging = false;
	private Image tabImage = null;
	private Point currentMouseLocation = null;
	private int draggedTabIndex = 0;
	private Component newTab;

	private Map<JungGraph, Component> openedGraphs;
	private MainController controller;

	private void addTab(JungGraph graph) {
		GraphEditor editor = new GraphEditor(graph);
		openedGraphs.put(graph, editor);
		remove(newTab);
		addTab(graph.getName(), editor);
		setSelectedComponent(editor);
		addTab("+", newTab);
	}

	public void openGraph(JungGraph graph) {
		Component editor = openedGraphs.get(graph);
		if (editor != null) {
			System.out.println("already opened this graph");
			setSelectedComponent(editor);
		} else {
			addTab(graph);
		}
	}

	public void undo() {
		if (getTabCount() <= 1)
			return;
		GraphEditor editor = (GraphEditor) getSelectedComponent();
		editor.undo();
	}

	public void redo() {
		if (getTabCount() <= 1)
			return;
		GraphEditor editor = (GraphEditor) getSelectedComponent();
		editor.redo();
	}

	public GraphEditor getCurrentEditor() {
		if (getTabCount() <= 1)
			return null;
		GraphEditor editor = (GraphEditor) getSelectedComponent();
		return editor;
	}

	public TabbedGraphEditor(MainController controller) {
		controller.register(this);
		this.controller = controller;

		openedGraphs = new HashMap<JungGraph, Component>();

		newTab = new JPanel();
		this.addTab("+", newTab);

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent
						.getSource();

				if (sourceTabbedPane.getSelectedComponent() == newTab) {
					TabbedGraphEditor.this.openGraph(new CachedJungGraph("untitled", false));
				}
				
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {

				if (!dragging) {
					// Gets the tab index based on the mouse position
					int tabNumber = getUI().tabForCoordinate(
							TabbedGraphEditor.this, e.getX(), e.getY());

					if (tabNumber > 0) {
						System.out.println("TabNumber: " + tabNumber);
						draggedTabIndex = tabNumber;

						Rectangle bounds = getUI().getTabBounds(
								TabbedGraphEditor.this, tabNumber);

						// Paint the tabbed pane to a buffer
						Image totalImage = new BufferedImage(getWidth(),
								getHeight(), BufferedImage.TYPE_INT_ARGB);
						Graphics totalGraphics = totalImage.getGraphics();
						totalGraphics.setClip(bounds);
						// Don't be double buffered when painting to a static
						// image.
						setDoubleBuffered(false);
						paintComponent(totalGraphics);

						// Paint just the dragged tab to the buffer
						tabImage = new BufferedImage(bounds.width,
								bounds.height, BufferedImage.TYPE_INT_ARGB);
						Graphics graphics = tabImage.getGraphics();
						graphics.drawImage(totalImage, 0, 0, bounds.width,
								bounds.height, bounds.x, bounds.y, bounds.x
										+ bounds.width, bounds.y
										+ bounds.height, TabbedGraphEditor.this);

						TabbedGraphEditor.this.remove(newTab);
						dragging = true;
						repaint();
					}
				} else {
					currentMouseLocation = e.getPoint();

					// Need to repaint
					repaint();
				}

				super.mouseDragged(e);
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {

				if (dragging) {
					int tabNumber = getUI().tabForCoordinate(
							TabbedGraphEditor.this, e.getX(), 10);

					if (tabNumber >= 0) {
						Component comp = getComponentAt(draggedTabIndex);
						String title = getTitleAt(draggedTabIndex);
						removeTabAt(draggedTabIndex);
						insertTab(title, null, comp, null, tabNumber);
					}
					addTab("+", newTab);
				}

				dragging = false;
				tabImage = null;
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Are we dragging?
		if (dragging && currentMouseLocation != null && tabImage != null) {
			// Draw the dragged tab
			g.drawImage(tabImage, currentMouseLocation.x,
					currentMouseLocation.y, this);
		}
	}
}