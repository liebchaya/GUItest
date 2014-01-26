package gui;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import code.NgramViewer;
import code.StringUtils;

/**
 * Manage the target term selection using a message box
 * @author HZ
 *
 */
public class ShowFullCellTextGui {


	public ShowFullCellTextGui(){
		
	}
	




	/**
	 * Show a message box with a text
	 * based on the files in the folder
	 * @param folder
	 * @param fileSufix
	 * @return target term
	 */
	public String showWindow(String content, boolean edit, int row, boolean isAncient){
		JPanel panel = new JPanel();
		JTextArea area = new JTextArea(20,20);
		area.setFont(new Font("Dialg", Font.PLAIN, 20));
		content = NgramViewer.mergeNgrams(StringUtils.convertStringToList(content));
		content = content.replaceAll(",","\n\n");
		area.setLineWrap(true);  
		area.setWrapStyleWord(true);  
		area.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);  
		area.setText(content);
		if (!edit)
			area.setEditable(false);
		
		JScrollPane sp = new JScrollPane(area);
		sp.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		panel.add(sp);
		
		if(!edit){
			JOptionPane.showMessageDialog(null, panel, "מונח", JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		else {
	    int result = JOptionPane.showConfirmDialog(null, panel, "הרחבה", JOptionPane.OK_CANCEL_OPTION);  
	    if (result == JOptionPane.OK_OPTION) {
	    	String selection = area.getText();
	    	if(isAncient)
	    		JudgePanel.ancientExpDm.setValueAt(selection, row, 0);
	    	else
	    		JudgePanel.modernExpDm.setValueAt(selection, row, 0);
	      return selection;  
	    	}
		}
		return null;  
	}
	

}
