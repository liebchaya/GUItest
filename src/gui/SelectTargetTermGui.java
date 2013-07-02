package gui;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.io.File;
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

	public SelectTargetTermGui(){
		
	}
	
	/**
	 * Show a message box with a combo box that enables target term selection
	 * based on the files in the folder
	 * @param folder
	 * @param fileSufix
	 * @return target term
	 */
	public String getTargetTerm(String folder, String fileSufix){
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
	      return selection;  
	    }
		return null;  
	}
}
