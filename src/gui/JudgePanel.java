package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;
import javax.swing.JButton;

import code.Constants;
import code.DataFiles;
import code.FileUtils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Vector;
import java.awt.FlowLayout;
import java.awt.CardLayout;

public class JudgePanel{

	final static String ANCIENTPANEL = "עתיק";
    final static String MODERNPANEL = "מודרני";
    
    static Vector<String> comboBoxItems1= new Vector<String>();
    static Vector<String> comboBoxItems2= new Vector<String>();
    
	final static DefaultComboBoxModel ancientComboBoxModel = new DefaultComboBoxModel(comboBoxItems1);
	final static DefaultComboBoxModel modernComboBoxModel = new DefaultComboBoxModel(comboBoxItems2);
	
	final static DefaultTableModel ancientExpDm = new DefaultTableModel();
	final static DefaultTableModel modernExpDm = new DefaultTableModel();
	
	private static JTable ancientTable; 
	private static JTable modernTable;
	
	private static JTable ancientExpTable; 
	private static JTable modernExpTable;

	/**
	 * Create grid view for manual judgment
	 * @param targetTerm
	 * @return
	 * @throws IOException 
	 */
	public static JPanel createJudgementView(String targetTerm) throws IOException {

		JPanel panel = new JPanel(new BorderLayout());
		//Two tabs for modern and ancient annotation
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		JPanel ancientPanel = new JPanel(new BorderLayout()){
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 100;
                return size;
            }
        };
        
        JPanel modernPanel = new JPanel(new BorderLayout()){
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 100;
                return size;
            }
        };


	    final JButton SaveBtn = new JButton("שמור שיפוטים");
	    SaveBtn.setName(targetTerm);
		SaveBtn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
				SubmitJugementGui submit = new SubmitJugementGui();
				Vector<Integer> infoVec = submit.getJudgementInfo();
				if (infoVec!=null){
					try {
						// write annotation file to a new folder
						BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.workingDir+Constants.annotatedDir+"/"+SaveBtn.getName()+Constants.judgmentFileSufix));
						writer.write("\n");
						int rowNum = (infoVec.get(0)<=ancientTable.getRowCount()?infoVec.get(0):ancientTable.getRowCount());
						for(int i=0; i<rowNum; i++){
							writer.write(ancientTable.getValueAt(i, 1).toString()+"\t");
							writer.write(ancientTable.getValueAt(i, 6).toString()+"\t");
							writer.write("1\t");
							writer.write(((Boolean) ancientTable.getValueAt(i, 2)?1:0) +"\t");
							writer.write(ancientComboBoxModel.getIndexOf(ancientTable.getValueAt(i, 3).toString())+"\t");
							writer.write(ancientTable.getValueAt(i, 7).toString()+"\n");
						}
						rowNum = (infoVec.get(1)<=modernTable.getRowCount()?infoVec.get(1):modernTable.getRowCount());
						for(int i=0; i<rowNum; i++){
							writer.write(modernTable.getValueAt(i, 1).toString()+"\t");
							writer.write(modernTable.getValueAt(i, 6).toString()+"\t");
							writer.write("0\t");
							writer.write(((Boolean) modernTable.getValueAt(i, 2)?1:0) +"\t");
							writer.write(modernComboBoxModel.getIndexOf(modernTable.getValueAt(i, 3).toString())+"\t");
							writer.write(modernTable.getValueAt(i, 7).toString()+"\n");
						}
						writer.close();
						// remove judgment file
						File judgmentFile = new File(Constants.workingDir+Constants.judgmentsDir+"/"+SaveBtn.getName()+Constants.judgmentFileSufix);
						judgmentFile.delete();
						
						// write the expansion file
						writer = new BufferedWriter(new FileWriter(Constants.workingDir+Constants.inputDir+"/"+Constants.expFileName,true));
						rowNum = ancientExpTable.getRowCount();
						for(int i=0; i<rowNum; i++){
							String targetTerm = SaveBtn.getName();
							writer.write(targetTerm + "\t");
							writer.write(ancientExpTable.getValueAt(i, 0).toString()+"\t");
							writer.write(ancientExpTable.getValueAt(i, 1).toString()+"\t");
							writer.write("1\n");
						}
						rowNum = modernExpTable.getRowCount();
						for(int i=0; i<rowNum; i++){
							String targetTerm = SaveBtn.getName();
							writer.write(targetTerm + "\t");
							writer.write(modernExpTable.getValueAt(i, 0).toString()+"\t");
							writer.write(modernExpTable.getValueAt(i, 1).toString()+"\t");
							writer.write("0\n");
						}
						writer.close();
						}
						catch(IOException e2) {
						System.out.println("Problem creating table file: " + e2);
						}
					SaveBtn.setEnabled(false);
				}
				}
		});
		panel.add(SaveBtn,  BorderLayout.SOUTH);
		
		loadTableData(targetTerm);
		loadExpTable();
	    
	    
	    DrawJudgementPanel(modernPanel,false);
	    DrawJudgementPanel(ancientPanel,true);

        tabbedPane.addTab(ANCIENTPANEL, ancientPanel);
//      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab(MODERNPANEL, modernPanel);
//      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
       panel.add(tabbedPane);
       
       return panel;
	}

	/**
	 * Load empty expansion lists
	 */
	private static void loadExpTable(){
		
		Vector dummyHeader = new Vector();
	    dummyHeader.addElement("");
	    dummyHeader.addElement("");
	    ancientExpDm.setDataVector(new Vector(),dummyHeader);
	    ancientExpTable = new JTable(ancientExpDm){
	    	@Override
	            public Component prepareRenderer(TableCellRenderer renderer,
	                    int rowIndex, int vColIndex) {
	                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
	                    c.setBackground(getBackground());
	                return c;
	            }};
	            ancientExpTable.getColumnModel().getColumn(1).setMinWidth(0);  
	            ancientExpTable.getColumnModel().getColumn(1).setMaxWidth(0);
	    		
	    modernExpDm.setDataVector(new Vector(),dummyHeader);
	    modernExpTable = new JTable(modernExpDm){
	    	@Override
            public Component prepareRenderer(TableCellRenderer renderer,
                    int rowIndex, int vColIndex) {
                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
                    c.setBackground(getBackground());
                return c;
            }};
            
	}
	
	private static void loadTableData(String targetTerm) throws IOException{
		final DataFiles df = new DataFiles(Constants.workingDir+Constants.judgmentsDir);
		// load previous groups to the list box
		df.loadDataFile2Table(targetTerm,Constants.judgmentFileSufix);
		for(String element:df.getM_ancientGroups())
			ancientComboBoxModel.addElement(element);
		for(String element:df.getM_modernGroups())
			modernComboBoxModel.addElement(element);
		
		//load data tables
		DefaultTableModel ancientDm = new DefaultTableModel(){
		      public boolean isCellEditable(int rowIndex, int mColIndex) {
		          if(df.getM_ancientPrevJudgments().contains(rowIndex)){
		        	  return false;
		          }
		          return true;
		        }
		};
		ancientDm.setDataVector(df.getM_vAncientData(),df.getM_vCols());
	    
	    ancientTable = new JTable(ancientDm){ 	
			@Override
        public Component prepareRenderer(TableCellRenderer renderer,
                int rowIndex, int vColIndex) {
            Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
            if (df.getM_ancientPrevJudgments().contains(rowIndex)) {
                c.setBackground(new Color(241,241,241));
            } else {
                c.setBackground(getBackground());
            }
            if (vColIndex == 0)
            	c.setBackground(new Color(204,255,153));
            return c;
        }};
	    ancientTable.setName(ANCIENTPANEL);
	    JTableHeader ancientHeader = ancientTable.getTableHeader();
	    ancientHeader.setBackground(new Color(255,255,153));
//	    header.setForeground(Color.WHITE);
	    
	    DefaultTableModel modernDm = new DefaultTableModel(){
	    	public boolean isCellEditable(int rowIndex, int mColIndex) {
		          if(df.getM_modernPrevJudgments().contains(rowIndex)){
		        	  return false;
		          }
		          return true;
		        }
		};
	    modernDm.setDataVector(df.getM_vModernData(),df.getM_vCols());
	    
	    modernTable = new JTable(modernDm){ 	
			@Override
	        public Component prepareRenderer(TableCellRenderer renderer,
	                int rowIndex, int vColIndex) {
	            Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
	            if (df.getM_modernPrevJudgments().contains(rowIndex)) {
	                c.setBackground(new Color(241,241,241));
	            } else {
	                c.setBackground(getBackground());
	            }
	            if (vColIndex == 0)
	            	c.setBackground(new Color(204,255,153));
	            return c;
	        }};
	    modernTable.setName(MODERNPANEL);
	    JTableHeader modernHeader = modernTable.getTableHeader();
	    modernHeader.setBackground(new Color(255,255,153));
	}
	/*
	 * Draw the judgment panel with the annotation table
	 */
	private static void DrawJudgementPanel(JPanel panel, boolean isAncient){
		JTable table;
		JTable expTable;
		DefaultComboBoxModel model;
		
		if (isAncient) {
			table = ancientTable;
			model = ancientComboBoxModel;
			expTable = ancientExpTable;
		}
		else {
			table = modernTable;
			model = modernComboBoxModel;
			expTable = modernExpTable;
		}
		
		table.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		table.getColumnModel().getColumn(6).setMinWidth(0);  
		table.getColumnModel().getColumn(6).setMaxWidth(0);  
		table.getTableHeader().getColumnModel().getColumn(6).setMinWidth(0);  
		table.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(0);
		
		table.getColumnModel().getColumn(7).setMinWidth(0);  
		table.getColumnModel().getColumn(7).setMaxWidth(0);  
		table.getTableHeader().getColumnModel().getColumn(7).setMinWidth(0);  
		table.getTableHeader().getColumnModel().getColumn(7).setMaxWidth(0);
		
		
//        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(6));    
//        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(6));
	
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment( JLabel.RIGHT );
		table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
	    
	    JComboBox comboBox = new JComboBox(model);
	    comboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);                         
        ((JComponent)comboBox.getRenderer()).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//	    // Create the combo box editor
//	    JComboBox comboBox = new JComboBox(ComboBoxTableModel.getValidStates());
	    comboBox.setEditable(false);
	    DefaultCellEditor editor = new DefaultCellEditor(comboBox);
//
//	    // Assign the editor to the second column
	    TableColumnModel tcm = table.getColumnModel();
	    tcm.getColumn(3).setCellEditor(editor);
//	    
	    table.getColumn("הוספת קבוצה").setCellRenderer(new AddGroupButtonRenderer());
	    table.getColumn("הוספת קבוצה").setCellEditor(
	            new AddGroupButtonEditor(new JCheckBox()));
	    
	    table.getColumn("הוספת הרחבה").setCellRenderer(new AddExpansionButtonRenderer());
	    table.getColumn("הוספת הרחבה").setCellEditor(
	            new AddExpansionButtonEditor(new JCheckBox()));
	    
	    TableColumn tc = tcm.getColumn(2); 
	    tc.setCellRenderer(table.getDefaultRenderer(Boolean.class)); 
	    tc.setCellEditor(table.getDefaultEditor(Boolean.class));
	    
	    table.getColumnModel().getColumn(0).setMinWidth(20);
	    table.getColumnModel().getColumn(0).setMaxWidth(20);
	    table.getColumnModel().getColumn(0).setPreferredWidth(20);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
	    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    
	    expTable.setShowGrid(false);
	    expTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    expTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
	    JScrollPane scrollTable = new JScrollPane(expTable);
	    scrollTable.setColumnHeader(null);
	    scrollTable.setPreferredSize(new Dimension(50, 0));
	    Box tableBox = new Box(BoxLayout.Y_AXIS);
	    JLabel expLabel = new JLabel("הרחבות");
	    tableBox.add(expLabel);
	    tableBox.add(scrollTable);
	    tableBox.setOpaque(true);
	    tableBox.setBackground(Color.ORANGE);
		panel.add(tableBox,  BorderLayout.WEST);
		
		JScrollPane pane = new JScrollPane( table );
		panel.add( pane, BorderLayout.CENTER );
	}


}
