package amicity.graph.pc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class GraphExplorer extends JPanel implements ActionListener {
	JTree tree;
	JScrollPane treeViewPane;

	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root node");
	
	public GraphExplorer(MainController controller) {
		tree = new JTree(root);
		treeViewPane = new JScrollPane(tree);
		setLayout(new BorderLayout());
		treeViewPane.setPreferredSize(new Dimension(160, 160));
		this.add(treeViewPane,BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
