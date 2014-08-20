package amicity.graph.pc;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Gui extends JPanel {
	private GraphView graph = new GraphView();
	
	private static Object[] columnNames = {
		"Name",
		"Description"
	};
	private JTable graphListView;
	private DefaultTableModel graphList;
	JScrollPane graphListPane;

	public Gui() {
		setLayout(new BorderLayout());
		this.add(graph, BorderLayout.CENTER);
		
		// graph list
		graphList = new DefaultTableModel(null, columnNames);
		graphListView = new JTable(graphList);
		graphListPane = new JScrollPane(graphListView);
		graphListPane.setPreferredSize(new Dimension(160, 160));
		this.add(graphListPane, BorderLayout.WEST);
	}
}
