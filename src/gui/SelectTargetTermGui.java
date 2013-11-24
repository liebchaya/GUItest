package gui;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Manage the target term selection using a message box
 * @author HZ
 *
 */
public class SelectTargetTermGui {
	
	private String m_targetTermString=null;

	public SelectTargetTermGui(){
		
	}
	
	
	
	public String getM_targetTerm() {
		return m_targetTermString;
	}




	/**
	 * Show a message box with a combo box that enables target term selection
	 * based on the files in the folder
	 * @param folder
	 * @param fileSufix
	 * @return target term
	 */
	public String getTargetTermByDirFiles(String folder, String fileSufix){
		JPanel panel = new JPanel(new GridLayout(5, 5));
		JLabel label = new JLabel("בחר מונח:                                                ");
		label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(label);  

		Vector<String> comboBoxItems = new Vector<String>();
		
		File dataFolder = new File(folder);
		if (dataFolder.listFiles()==null){
			JOptionPane.showMessageDialog(null, "לא נמצאו ערכים לשיפוט", "ערכים לשיפוט", 1);
			return null;
		}
		for(File f:dataFolder.listFiles())
			if (f.getName().endsWith(fileSufix))
			comboBoxItems.add(f.getName().substring(0, f.getName().indexOf(".")));
	
		DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		
		JComboBox comboBox = new JComboBox(model);
	    comboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);                         
        ((JComponent)comboBox.getRenderer()).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//	    create the combo box editor
	    comboBox.setEditable(false);
	  
	    panel.add(comboBox);
	    
	    int result = JOptionPane.showConfirmDialog(null, panel, "בחירת מונח", JOptionPane.OK_CANCEL_OPTION);  
	    if (result == JOptionPane.OK_OPTION) {
	    	String selection = comboBox.getSelectedItem().toString();
	    	m_targetTermString = selection;
	      return selection;  
	    }
		return null;  
	}
	
	/**
	 * Show a message box with a combo box that enables target term selection
	 * based on the files in the folder
	 * @param folder
	 * @param fileSufix
	 * @return target term
	 * @throws IOException 
	 */
	public String getTargetTerm(String termsFile, String folder, String fileSufix) throws IOException{
		JPanel panel = new JPanel(new GridLayout(5, 5));
		JLabel label = new JLabel("בחר מונח:                                                ");
		label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(label);  

		Vector<String> comboBoxItems = new Vector<String>();
		
		File dataFolder = new File(folder);
		File dataFile = new File(termsFile);
		if (!dataFile.exists()){
			JOptionPane.showMessageDialog(null, "לא נמצאו ערכים", "ערכים חסרים", 1);
			return null;
		}
		BufferedReader reader =  new BufferedReader(new InputStreamReader(
			    new FileInputStream(dataFile), "UTF-8"));
		String line = reader.readLine();
		if ( line ==null || dataFolder.listFiles()==null || dataFolder.listFiles().length == 0){
			JOptionPane.showMessageDialog(null, "לא נמצאו ערכים לשיפוט", "ערכים לשיפוט", 1);
			reader.close();
			return null;
		}
		
		HashSet<String> filesInFolder = new HashSet<String>();
		filesInFolder.addAll(Arrays.asList(dataFolder.list()));
		
		HashMap<String,String> targetTermsMap = new HashMap<String, String>();
		while (line != null) {
			String[] tokens = line.split("\t");
			String curFile = tokens[0]+fileSufix;
			if (filesInFolder.contains(curFile)){
				targetTermsMap.put(tokens[1], tokens[0]);
				comboBoxItems.add(tokens[1]);
			}
			line = reader.readLine();
		}
		reader.close();
	
		DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		
		JComboBox comboBox = new JComboBox(model);
	    comboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);                         
        ((JComponent)comboBox.getRenderer()).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//	    create the combo box editor
	    comboBox.setEditable(false);
	  
	    panel.add(comboBox);
	    
	    int result = JOptionPane.showConfirmDialog(null, panel, "בחירת מונח", JOptionPane.OK_CANCEL_OPTION);  
	    if (result == JOptionPane.OK_OPTION) {
	    	String selection = targetTermsMap.get(comboBox.getSelectedItem().toString());
	    	m_targetTermString = comboBox.getSelectedItem().toString();
	      return selection;  
	    }
		return null;  
	}

}
