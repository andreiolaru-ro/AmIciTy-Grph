package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultCellEditor;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import amicity.graph.pc.MainController;
import amicity.graph.pc.jung.JungGraph;

public class GraphExplorer extends JPanel implements TreeSelectionListener {
	private static final long serialVersionUID = 9187558802298268027L;
	
	class TreeLeafPopup extends JPopupMenu implements ActionListener {
		JMenuItem rename;
		JMenuItem close;
		TreePath path;
		
		public TreeLeafPopup(TreePath path) {
			this.path = path;
			rename = new JMenuItem("rename");
			rename.addActionListener(this);
			close = new JMenuItem("close");
			close.addActionListener(this);
			add(rename);
			add(close);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rename) {
				GraphExplorer.this.tree.startEditingAtPath(path);
			}
			if (e.getSource() == close) {
				System.out.println("close");
			}
		}
	}
	
	class TreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 6140024292002772756L;
		public TreeNode(String name) {
			super(name);
		}
		public boolean isLeaf() {
			return false;
		}
	}
	
	class TreeLeaf extends DefaultMutableTreeNode {
		public TreeLeaf(JungGraph graph) {
			super(graph);
		}
		public boolean isLeaf() {
			return true;
		}
		
		public void setUserObject(Object data) {
			JungGraph graph = (JungGraph)getUserObject();
			graph.setName((String) data);
		}
	}
	
	class TreeEditor extends DefaultTreeCellEditor {

		public Component getTreeCellEditorComponent(JTree tree,
			    Object value, boolean isSelected, boolean expanded,
			    boolean leaf, int row)
		{
			System.out.println(value.getClass());
			return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
			
		}
		
		public TreeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
			super(tree, renderer);
		}
		
		public boolean isCellEditable(EventObject event) {
			System.out.println("called by: " + event);
			if (event == null) {
				return true;
			}
			
			if (!super.isCellEditable(event)) {
				return false;
			}
			
			if (event != null && event.getSource() instanceof JTree && event instanceof MouseEvent) {  
	            MouseEvent mouseEvent = (MouseEvent)event;  
	            JTree tree = (JTree)event.getSource();  
	            TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());  
	            System.out.println(path.getLastPathComponent() instanceof TreeLeaf);
	            return path.getLastPathComponent() instanceof TreeLeaf;  
	        } 

			return false;
		}
	}
	
	class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
	        /* int selRow = tree.getRowForLocation(e.getX(), e.getY());
	         TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
	         if(selRow != -1) {
	             if(e.getClickCount() == 2) {
	            	tree.startEditingAtPath(selPath);
	            	tree.cancelEditing();
	            	System.out.println("LOLLOL");
	             }
	         }
	         */
	     }

		public void mouseClicked(MouseEvent e) {
	         if (SwingUtilities.isRightMouseButton(e)) {

	             int row = tree.getClosestRowForLocation(e.getX(), e.getY());
	             //tree.setSelectionRow(row);
	             TreePath path = tree.getPathForLocation(e.getX(), e.getY());
	             tree.setSelectionPath(path);

	             //TreePath path = tree.getSelectionPath();
	             if (path != null && path.getLastPathComponent() instanceof TreeLeaf) {
	            	 TreeLeafPopup popup = new TreeLeafPopup(path);
	            	 popup.show(e.getComponent(), e.getX(), e.getY());
	             }
	         }
		}
	}
	
	JTree tree;
	JScrollPane treeViewPane;

	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root node");
	DefaultMutableTreeNode contextGraphNode = new TreeNode("Context graphs");
	DefaultMutableTreeNode patternNode = new TreeNode("Patterns");
	
	List<JungGraph> contextGraphList = new ArrayList<JungGraph>();
	List<JungGraph> patternList = new ArrayList<JungGraph>();
	private MainController controller;
	
	public GraphExplorer(MainController controller) {
		this.controller = controller;
		controller.register(this);
		
		root.add(contextGraphNode);
		root.add(patternNode);
		tree = new JTree(root);
		
		tree.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		
		treeViewPane = new JScrollPane(tree);
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.expandRow(0);
		tree.expandRow(1);
		tree.setEditable(true);
		tree.setCellEditor(new TreeEditor(tree, new DefaultTreeCellRenderer()));
		
		setLayout(new BorderLayout());
		treeViewPane.setPreferredSize(new Dimension(160, 160));
		this.add(treeViewPane,BorderLayout.CENTER);
		tree.addMouseListener(new MouseHandler());
	}
		
	public void addGraph(JungGraph graph, boolean isPattern) {
		DefaultMutableTreeNode parent = contextGraphNode;
		if (isPattern)
			parent = patternNode;
		DefaultMutableTreeNode newNode = new TreeLeaf(graph);
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.insertNodeInto(newNode, parent, parent.getChildCount());
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (selectedNode == null || !selectedNode.isLeaf())
			return;
		JungGraph graph = (JungGraph) selectedNode.getUserObject();
		controller.getGraphEditor().openGraph(graph);
	}
	
	public List<JungGraph> getPatterns() {
		ArrayList<JungGraph> list = new ArrayList<JungGraph>();
		for (int i = 0; i < patternNode.getChildCount(); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) patternNode.getChildAt(i);
			list.add((JungGraph)node.getUserObject());
		}
		return list;
	}
	
	public List<JungGraph> getGraphs() {
		ArrayList<JungGraph> list = new ArrayList<JungGraph>();
		for (int i = 0; i < contextGraphNode.getChildCount(); i++) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) contextGraphNode.getChildAt(i);
			list.add((JungGraph)node.getUserObject());
		}
		return list;
	}
}
