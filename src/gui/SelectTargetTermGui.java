package gui;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SelectTargetTermGui {

	public SelectTargetTermGui(){
		
	}
	
	public String getTargetTerm(){
		JPanel panel = new JPanel(new GridLayout(5, 5));
		JLabel label = new JLabel("בחר מונח:                                                ");
		label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(label);  
		
		Vector<String> comboBoxItems = new Vector<String>(Arrays.asList(
			    "מונח 1", "מונח 2", "מונח 3", "מונח 4", "מונח 5", "מונח 6", "מונח 7", "מונח 8", "מונח 9", "מונח 10", "מונח 11"));
		DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		
		JComboBox comboBox = new JComboBox(model);
	    comboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);                         
        ((JComponent)comboBox.getRenderer()).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//	    // Create the combo box editor
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
