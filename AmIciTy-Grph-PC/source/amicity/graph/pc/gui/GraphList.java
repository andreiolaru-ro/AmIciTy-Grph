package amicity.graph.pc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import amicity.graph.pc.MainController;
import amicity.graph.pc.jung.JungGraph;

public class GraphList extends JPanel {
	private static final long serialVersionUID = -4380811685612628394L;

	MainController controller;
	
	private static String[] columnNames = {
		"Name",
		"Description"
	};
	
	private JTable graphListView;
	private JungGraphTabelModel graphList;
	JScrollPane graphListPane;
	
	class JungGraphTabelModel extends AbstractTableModel {
		List<JungGraph> graphs = new ArrayList<JungGraph>();
		
		@Override
		public boolean isCellEditable(int row, int col) { 
			return true; 
		}
		
		public void setValueAt(Object value, int row, int col) {
			String val = (String) value;
			JungGraph graph = graphs.get(row);
			if (col == 0)
				graph.setName(val);
			else
				graph.setDescription(val);
			fireTableCellUpdated(row, col);
		}
		
		public void addGraph(JungGraph graph) {
			graphs.add(graph);
			fireTableDataChanged();
		}
		
		public List<JungGraph> getGraphs() {
			return graphs;
		}
		
		@Override
		public int getRowCount() {
			return graphs.size();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
		
		@Override
		public String getColumnName(int column) {
		    return columnNames[column];
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			JungGraph graph = graphs.get(rowIndex);
			switch (columnIndex) {
			case 0:
				return graph.getName();
			case 1:
				return graph.getDescription();
			default:
				return "UNKNOWN_ERR";
			}
		}
		
	}
	
	public GraphList(final MainController controller) {
		this.controller = controller;
		controller.registerGraphList(this);
		
		setLayout(new BorderLayout());

		graphList = new JungGraphTabelModel();
		graphListView = new JTable(graphList);
		graphListPane = new JScrollPane(graphListView);
		graphListPane.setPreferredSize(new Dimension(160, 160));
		add(graphListPane);
		
		graphListView.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = graphListView.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}
				
				controller.loadGraph(graphList.getGraphs().get(selectedRow));
			}});
	}
	
	public JungGraph createNewGraph() {
		JungGraph graph = JungGraph.createJungGraph();
		int last = getCount();
		graphList.addGraph(graph);
		graphListView.setRowSelectionInterval(last, last);
		return graph;
	}
	
	public void addGraph(JungGraph graph, boolean select) {
		int last = getCount();
		graphList.addGraph(graph);
		if (select) {
			graphListView.setRowSelectionInterval(last, last);
		}
	}
	
	public JungGraph getSelectedGraph() {
		int selectedRow = graphListView.getSelectedRow();
		if (selectedRow == -1)
			return null;
		return graphList.getGraphs().get(selectedRow);
	}
	
	public int getCount() {
		return graphList.getRowCount();
	}
}
