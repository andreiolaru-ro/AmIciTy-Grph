package amicity.graph.pc;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
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

import amicity.graph.pc.jung.JungGraph;

public class GraphExplorer extends JPanel implements TreeSelectionListener {
	private static final long serialVersionUID = 9187558802298268027L;
	
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

		public TreeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
			super(tree, renderer);
		}
		
		public boolean isCellEditable(EventObject event) {
			if (!super.isCellEditable(event)) {  
	            return false;  
	        }  
	        
			if (event != null && event.getSource() instanceof JTree && event instanceof MouseEvent) {  
	            MouseEvent mouseEvent = (MouseEvent)event;  
	            JTree tree = (JTree)event.getSource();  
	            TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());  
	            return path.getLastPathComponent() instanceof TreeLeaf;  
	        } 
			return false;
		}
	}
	
	class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
	         int selRow = tree.getRowForLocation(e.getX(), e.getY());
	         TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
	         if(selRow != -1) {
	             if(e.getClickCount() == 2) {
	            	tree.startEditingAtPath(selPath);
	            	tree.cancelEditing();
	            	System.out.println("LOLLOL");
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
		controller.registerGraphExplorer(this);
		
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
		//tree.addMouseListener(new MouseHandler());
		tree.setCellEditor(new TreeEditor(tree, new DefaultTreeCellRenderer()));
		
		setLayout(new BorderLayout());
		treeViewPane.setPreferredSize(new Dimension(160, 160));
		this.add(treeViewPane,BorderLayout.CENTER);
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
		if (!selectedNode.isLeaf())
			return;
		JungGraph graph = (JungGraph) selectedNode.getUserObject();
		controller.loadGraph(graph);
	}
}
