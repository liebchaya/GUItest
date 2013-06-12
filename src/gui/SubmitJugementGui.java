package gui;

import java.awt.ComponentOrientation;
import java.awt.GridLayout;
import java.awt.TrayIcon.MessageType;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SubmitJugementGui {

	public SubmitJugementGui(){
		
	}
	
	public boolean getJudgementInfo(){
		JPanel panel = new JPanel(new GridLayout(5, 5));
		JTextField modern = new JTextField(10);  
		JTextField ancient = new JTextField(10);
		JLabel label = new JLabel("���� ���� ���� ����� ���� ������� ��������:");
		JLabel label2 = new JLabel("���� ���� ���� ����� ���� ������� ������:");
		label.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(label);  
		
	  
	    panel.add(label);
	    panel.add(modern);
	    panel.add(label2); 
	    panel.add(ancient); 
	    
	    int result = JOptionPane.showConfirmDialog(null, panel, "����� ����� �����", JOptionPane.OK_CANCEL_OPTION);  
	    if (result == JOptionPane.OK_OPTION) {
	    	String selection1 = modern.getText();
	    	String selection2 = ancient.getText();
	    	try {
	    	    Integer.parseInt(selection1);
	    	    Integer.parseInt(selection2);
	    	} catch(NumberFormatException e) {
//	    		JOptionPane.showMessageDialog(null, "���� ������ ������ ����", "����� ����� ������",MessageType.ERROR);
	    		JOptionPane.showMessageDialog(null, "���� ������ ������ ����", "����� ����� ������",JOptionPane.ERROR_MESSAGE);
	    		return false;
	    	}
	      return true;  
	    }
		return false;  
	}
}
