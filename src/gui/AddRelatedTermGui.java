package gui;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeCellEditor;

import code.Constants;
import code.FileUtils;

public class AddRelatedTermGui {

	public AddRelatedTermGui(){
		
	}
	
	/**
	 * Show a message box with a text box that enables manual inserting of a related term 
	 * @param target_term_id
	 * @param group_id
	 * @param generation
	 * @return exit code
	 * @throws IOException 
	 */
	public int getRelatedTerm(int target_term_id, int group_id, int generation) throws IOException{
//		JPanel panel = new JPanel(new GridLayout(5, 5));
		JPanel panel = new JPanel();
		
		//Create a two dimensional array to hold the data for the JTable.
        Object[][] data = {{false,false,""},{false,false,""},{false,false,""},{false,false,""},{false,false,""},
        				   {false,false,""},{false,false,""},{false,false,""},{false,false,""},{false,false,""},
        				   {false,false,""},{false,false,""},{false,false,""},{false,false,""},{false,false,""},
        				   {false,false,""},{false,false,""},{false,false,""},{false,false,""}};
        
        //A string array containing the column names for the JTable.
        String[] columnNames = {"הרחבה","עתיק","מונח קשור"};
        
        //Create the JTable using the data array and column name array.
        JTable table = new JTable(data, columnNames);
        
        //Create a JScrollPane to contain for the JTable
        JScrollPane sp = new JScrollPane(table);
        
        table.getColumn("הרחבה").setCellRenderer(table.getDefaultRenderer(Boolean.class));
	    table.getColumn("הרחבה").setCellEditor(
	    		table.getDefaultEditor(Boolean.class));
	    
	    table.getColumn("עתיק").setCellRenderer(table.getDefaultRenderer(Boolean.class));
	    table.getColumn("עתיק").setCellEditor(
	    		table.getDefaultEditor(Boolean.class));
	    
		table.getTableHeader().setFont(new Font("Dialg", Font.PLAIN, 20));
		table.setRowHeight(table.getRowHeight()+5);
		table.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		table.getColumn("עתיק").setMaxWidth(70);
		table.getColumn("הרחבה").setMaxWidth(70);
		table.getColumn("מונח קשור").setMaxWidth(350);
		
		panel.add(sp);
		
		
//		JTextField text0 = new JTextField(10);  
//		JLabel label = new JLabel("הכנס מונח חדש:                                              ");
//		label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//		panel.add(label);  
//	    panel.add(text0); 
//	    
//	    JCheckBox ancientButton = new JCheckBox("עתיק");
//	    JCheckBox expansionButton = new JCheckBox("הרחבה");
//	    ancientButton.setHorizontalAlignment(JCheckBox.LEADING);
//	    ancientButton.setHorizontalTextPosition(JCheckBox.LEADING);
//	    expansionButton.setHorizontalAlignment(JCheckBox.LEADING);
//	    expansionButton.setHorizontalTextPosition(JCheckBox.LEADING);
//
//	    
//	    panel.add(ancientButton);
//	    panel.add(expansionButton); 
//	    
		int groupId = group_id;
		int counter = 0;
	    int result = JOptionPane.showConfirmDialog(null, panel, "הוספת מונח קשור ידנית", JOptionPane.OK_CANCEL_OPTION);
	    BufferedWriter expWriter = null;
	    if (result == JOptionPane.OK_OPTION) {
	    	String dir = Constants.workingDir+Constants.annotatedDir+"/"+target_term_id+Constants.judgmentFileSufix;
    		String fileContent = FileUtils.readFileAsString(dir).trim();
    		BufferedWriter writer = new BufferedWriter(new FileWriter(dir));
    		writer.write("\n");
	    	for(int i=0;i<table.getRowCount();i++){
	    		String term = table.getValueAt(i, 2).toString();
	    		if(term.trim().isEmpty())
	    			continue;
	    		
	    		int period = ((Boolean) table.getValueAt(i, 1)?1:0);
	    		boolean expansion = (Boolean) table.getValueAt(i, 0);
	    		groupId++;
	    		writer.write("["+term.trim()+"]\t["+term.trim()+"]\t"+period+"\t1\t"+ groupId + "\t" + target_term_id + "\t" + generation + "\t1\n");
	    		counter++;
	    		
	    		if (expansion){
	    			if (expWriter== null){
	    				dir = Constants.workingDir+Constants.inputDir+"/"+Constants.expFileName;
	    				expWriter = new BufferedWriter(new FileWriter(dir,true));
	    			}
	    			expWriter.write(target_term_id + "\t" +term.trim()+"\t"+target_term_id+"\t"+period+ "\t1\n");
	    		}
	    	}
	    	writer.write(fileContent.trim());
    		writer.close();
    		if(expWriter!=null)
    			expWriter.close();
    		if(counter==0)
    			JOptionPane.showMessageDialog(null, "לא הוכנס ערך חדש", "הכנסת נתונים שגויה", 0);
	    }
	    return counter;
//	    	String selection = text0.getText();
//	    	boolean ancient = ancientButton.isSelected();
//	    	boolean expansion = expansionButton.isSelected();
//	    	if(selection.trim().isEmpty()){
//	    		JOptionPane.showMessageDialog(null, "לא הוכנס ערך חדש", "הכנסת נתונים שגויה", 0);
//	    		selection = null;
//	    	}
//	    	else{
//	    		String dir = Constants.workingDir+Constants.annotatedDir+"/"+target_term_id+Constants.judgmentFileSufix;
//	    		String fileContent = FileUtils.readFileAsString(dir);
//	    		BufferedWriter writer = new BufferedWriter(new FileWriter(dir));
//	    		String period = "1";
//	    		if(!ancient)
//	    			period = "0";
//	    		writer.write("\n["+selection+"]\t["+selection+"]\t"+period+"\t1\t"+ group_id + "\t" + target_term_id + "\t" + generation + "\t1\n");
//	    		writer.write(fileContent.trim());
//	    		writer.close();
//	    		if (expansion){
//	    			dir = Constants.workingDir+Constants.inputDir+"/"+Constants.expFileName;
//	    			writer = new BufferedWriter(new FileWriter(dir,true));
//	    			writer.write(target_term_id + "\t" + selection+"\t"+target_term_id+"\t"+period+ "\t1\n");
//		    		writer.close();
//	    		}
//	    	}
//	      return 1;  
//	    }
	}
	
	
}
