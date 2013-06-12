package gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
    
   
	static Vector<String> comboBoxItems1 = new Vector<String>(Arrays.asList(
		    "קבוצה 1", "קבוצה 2", "קבוצה 3"));
	
	static Vector<String> comboBoxItems2 = new Vector<String>(Arrays.asList(
		    "קבוצה 1", "קבוצה 2", "קבוצה 3"));
	
	final static DefaultComboBoxModel ancientModel = new DefaultComboBoxModel(comboBoxItems1);
	final static DefaultComboBoxModel modernModel = new DefaultComboBoxModel(comboBoxItems2);
	
	final static DefaultTableModel dm1 = new DefaultTableModel();
	final static DefaultTableModel dm2 = new DefaultTableModel();
	
	private static JTable ancientTable; 
	private static JTable modernTable;
	
	private static JTable ancientExpTable; 
	private static JTable modernExpTable;

	public static JPanel createMainView() {

		JPanel panel = new JPanel(new BorderLayout());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		JPanel contentPane = new JPanel(new BorderLayout()){
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 100;
                return size;
            }
        };
        
        JPanel card2 = new JPanel(new BorderLayout()){
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
		SaveBtn.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("C:\\outputTable.txt"));
				int rowNum = ancientTable.getRowCount();
				for(int i=0; i<rowNum; i++){
					oos.writeUTF(ancientTable.getValueAt(i, 0).toString());
					oos.writeUTF(ancientTable.getValueAt(i, 1).toString());
					oos.writeUTF(ancientTable.getValueAt(i, 2).toString());
					oos.writeUTF(ancientTable.getValueAt(i, 3).toString());
				}
				oos.close();
				}
				catch(IOException e2) {
				System.out.println("Problem creating table file: " + e2);
				return;
				}
				System.out.println("JTable correctly saved to file " + "C:\\outputTable.txt");
				SubmitJugementGui submit = new SubmitJugementGui();
				if (submit.getJudgementInfo())
					SaveBtn.setEnabled(false);
				}
		});
//		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
//		contentPane.add(SaveBtn,  BorderLayout.SOUTH);
		panel.add(SaveBtn,  BorderLayout.SOUTH);
		
		
		 loadTableData(true);
		 loadExpTable(true);
	    
	    
	    DrawJudgementPanel(card2,false);
//        card2.add(new JTextField("TextField", 20));

        tabbedPane.addTab(ANCIENTPANEL, contentPane);
//        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        tabbedPane.addTab(MODERNPANEL, card2);
//        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
//		contentPane.add(table, BorderLayout.CENTER);
        
      panel.add(tabbedPane);
      
       DrawJudgementPanel(contentPane,true);
       
       return panel;
       
//       JMenuBar menubar = new JMenuBar();
//       menubar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//       JMenu filemenu = new JMenu("תזארוס");
//       filemenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//       JMenuItem fileItem1 = new JMenuItem("הצג תזארוס");
//       fileItem1.addActionListener(new ActionListener(){
//           @Override
//           public void actionPerformed(ActionEvent ae) {
//        	   AddTargetTermGui t = new AddTargetTermGui();
//        	   System.out.println(t.getTargetTerm());
//        	   TreeFrame tf = new TreeFrame();
//        	   tf.setVisible(true);
//        	   MainFrame.getFrames()[0].setVisible(false);
//           }
//       });
//       filemenu.add(fileItem1);
//       menubar.add(filemenu);
//       this.setJMenuBar(menubar);
	}

	
	private static void loadExpTable(boolean isAncient){
		
		Vector dummyHeader = new Vector();
	    dummyHeader.addElement("");
	    dm1.setDataVector(strArray2Vector(new String[]{"מונח 1","מונח 2","מונח 3"}), dummyHeader);
	    ancientExpTable = new JTable(dm1);
	    ancientExpTable.setName(ANCIENTPANEL);
	    
	    dm2.setDataVector(strArray2Vector(new String[]{"מונח 1","מונח 2","מונח 3"}), dummyHeader);
	    modernExpTable = new JTable(dm2);
	    modernExpTable.setName(MODERNPANEL);
	}
	
	private static void loadTableData(boolean isAncient){
		DefaultTableModel dm1 = new DefaultTableModel();
	    dm1.setDataVector(new Object[][] { {1, "מילה 1", Boolean.FALSE, comboBoxItems1.get(0), "הוסף קבוצה", "הוסף הרחבה" },
	        {2, "מילה 2", Boolean.TRUE, comboBoxItems1.get(0), "הוסף קבוצה", "הוסף הרחבה"} }, new Object[] {"#", "מונח", "שיפוט", "קבוצה", "הוספת קבוצה", "הוספת הרחבה"  });
	    
	    ancientTable = new JTable(dm1);
	    ancientTable.setName(ANCIENTPANEL);
	    
	    DefaultTableModel dm2 = new DefaultTableModel();
	    dm2.setDataVector(new Object[][] { {1, "מילה 1", Boolean.FALSE, comboBoxItems2.get(0), "הוסף קבוצה", "הוסף הרחבה" },
		        {2, "מילה 2", Boolean.TRUE, comboBoxItems2.get(0), "הוסף קבוצה", "הוסף הרחבה"} }, new Object[] {"#", "מונח", "שיפוט", "קבוצה", "הוספת קבוצה", "הוספת הרחבה" });
	    
	    modernTable = new JTable(dm2);
	    modernTable.setName(MODERNPANEL);
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
			model = ancientModel;
			expTable = ancientExpTable;
		}
		else {
			table = modernTable;
			model = modernModel;
			expTable = modernExpTable;
		}
		
		table.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
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
	    table.getColumnModel().getColumn(0).setCellRenderer(new ColorColumnRenderer(Color.yellow, Color.black));
	    
//	    DefaultTableModel dm1 = new DefaultTableModel();
//		Vector dummyHeader = new Vector();
//	    dummyHeader.addElement("");
//	    dm1.setDataVector(strArray2Vector(new String[]{"מונח 1","מונח 2","מונח 3"}), dummyHeader);
//	    JTable table1 = new JTable(dm1);
	    expTable.setShowGrid(false);
	    expTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    expTable.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
	    JScrollPane scrollTable = new JScrollPane(expTable);
	    scrollTable.setColumnHeader(null);
	    scrollTable.setPreferredSize(new Dimension(50, 0));
	    Box tableBox = new Box(BoxLayout.Y_AXIS);
	    tableBox.add(new JLabel("הרחבות"));
	    tableBox.add(scrollTable);
		panel.add(tableBox,  BorderLayout.WEST);
		
		JScrollPane pane = new JScrollPane( table );
		panel.add( pane, BorderLayout.CENTER );
	}
	
	private static Vector strArray2Vector(String[] str) {
	    Vector vector = new Vector();
	    for (int i = 0; i < str.length; i++) {
	      Vector v = new Vector();
	      v.addElement(str[i]);
	      vector.addElement(v);
	    }
	    return vector;
	  }


}
