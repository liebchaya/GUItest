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
import javax.swing.JTextField;

public class AddTargetTermGui {

	public AddTargetTermGui(){
		
	}
	
	public String getTargetTerm(){
		JPanel panel = new JPanel(new GridLayout(5, 5));
		JTextField text0 = new JTextField(10);  
		JLabel label = new JLabel("����� ������:                                                ");
		JLabel label2 = new JLabel("���� ���� ���:                                              ");
		label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(label);  
		
		Vector<String> comboBoxItems = new Vector<String>(Arrays.asList(
			    "���� 1", "���� 2", "���� 3", "���� 4", "���� 5", "���� 6", "���� 7", "���� 8", "���� 9", "���� 10", "���� 11"));
		DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		
		JComboBox comboBox = new JComboBox(model);
	    comboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);                         
        ((JComponent)comboBox.getRenderer()).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
//	    // Create the combo box editor
	    comboBox.setEditable(false);
	  
	    panel.add(comboBox);
	    panel.add(label2); 
	    panel.add(text0); 
	    
	    int result = JOptionPane.showConfirmDialog(null, panel, "����� ���� ���", JOptionPane.OK_CANCEL_OPTION);  
	    if (result == JOptionPane.OK_OPTION) {
	    	String selection = text0.getText();
	      return selection;  
	    }
		return null;  
	}
}
