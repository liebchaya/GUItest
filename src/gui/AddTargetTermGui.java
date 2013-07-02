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
import javax.swing.JTextField;

public class AddTargetTermGui {

	public AddTargetTermGui(){
		
	}
	
	/**
	 * Show a message box with a combo box that enables target term selection
	 * based on the files in the folder or new target term insertion via text box
	 * @param folder
	 * @param fileSufix
	 * @return target term
	 */
	public String getTargetTerm(String folder, String fileSufix){
		JPanel panel = new JPanel(new GridLayout(5, 5));
		JTextField text0 = new JTextField(10);  
		JLabel label = new JLabel("����� ������:                                                ");
		JLabel label2 = new JLabel("���� ���� ���:                                              ");
		label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(label);  
		
		Vector<String> comboBoxItems = new Vector<String>();
		
		File dataFolder = new File(folder);
		if (dataFolder.listFiles()==null){
			JOptionPane.showMessageDialog(null, "�� ����� ����� �����", "����� ������", 1);
			return null;
		}
		for(File f:dataFolder.listFiles())
			if (f.getName().endsWith(fileSufix))
			comboBoxItems.add(f.getName().substring(0, f.getName().indexOf(".")));
		
		DefaultComboBoxModel model = new DefaultComboBoxModel(comboBoxItems);
		JComboBox comboBox = new JComboBox(model);
	    comboBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);                         
        ((JComponent)comboBox.getRenderer()).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	    // create the combo box editor
	    comboBox.setEditable(false);
	  
	    panel.add(comboBox);
	    panel.add(label2); 
	    panel.add(text0); 
	    
	    int result = JOptionPane.showConfirmDialog(null, panel, "����� ���� ���", JOptionPane.OK_CANCEL_OPTION);  
	    if (result == JOptionPane.OK_OPTION) {
	    	String selection = text0.getText();
	    	if(selection.trim().isEmpty()){
	    		JOptionPane.showMessageDialog(null, "�� ����� ��� ���", "����� ������ �����", 0);
	    		selection = null;
	    	}
	      return selection;  
	    }
		return null;  
	}
}
