package gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import code.NgramViewer;
import code.StringUtils;

class AddEditGroupButtonRenderer extends JButton implements TableCellRenderer {

	  /**
	 * 
	 */
	private static final long serialVersionUID = -6192443981155186455L;

	public AddEditGroupButtonRenderer() {
	    setOpaque(true);
	  }

	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
	    if (isSelected) {
	    	System.out.println(row + " " +column);
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else {
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("Button.background"));
	    }
	    setText((value == null) ? "" : value.toString());
	    return this;
	  }
	}

	/**
	 * @version 1.0 11/09/98
	 */

	class AddEditGroupButtonEditor extends DefaultCellEditor {
	  /**
		 * 
		 */
		private static final long serialVersionUID = 5697922254476725878L;

	protected JButton button;

	  private String label;
	  private String data;
	  private String tableName;

	  private boolean isPushed;

	  public AddEditGroupButtonEditor(JCheckBox checkBox) {
	    super(checkBox);
	
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
	    if (isSelected) {
	      button.setForeground(table.getSelectionForeground());
	      button.setBackground(table.getSelectionBackground());
	    } else {
	      button.setForeground(table.getForeground());
	      button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    data = table.getValueAt(row, 1).toString();
	    button.setText(label);
	    isPushed = true;
	    tableName = table.getName();
	    return button;
	  }

	  public Object getCellEditorValue() {
	    if (isPushed) {
	      // 
	      //
	    	data = NgramViewer.mergeNgrams(StringUtils.convertStringToList(data));
	    	 int selection = JOptionPane.showConfirmDialog(
                     null
             , " ��� ��� ���� ������� ������ �� ������:" + data + "?"
             , "����� ����� ����"
             , JOptionPane.OK_CANCEL_OPTION
             , JOptionPane.INFORMATION_MESSAGE);
           
	    	 if (selection == JOptionPane.OK_OPTION)
	    		 if (tableName.equals(EditJudgePanel.ANCIENTPANEL)){
	    			 boolean bFound = false;
	    			 for(int i=0; i<EditJudgePanel.ancientComboBoxModel.getSize(); i++)
	    				 if(EditJudgePanel.ancientComboBoxModel.getElementAt(i).equals(data)){
	    					 JOptionPane.showMessageDialog(null, "������ ��� �����", "����� ������ �����", 0);
	    					 bFound = true;
	    				 }
	    			 if (!bFound)
	    				 EditJudgePanel.ancientComboBoxModel.addElement(data);
	    		 }
	    		 else {
	    			 boolean bFound = false;
					 for(int i=0; i<EditJudgePanel.modernComboBoxModel.getSize(); i++)
						 if(EditJudgePanel.modernComboBoxModel.getElementAt(i).equals(data)){
							 JOptionPane.showMessageDialog(null, "������ ��� �����", "����� ������ �����", 0);
							 bFound = true;
					 }
					 if (!bFound)
						 EditJudgePanel.modernComboBoxModel.addElement(data);
	    		 }
	    }
	    isPushed = false;
	    return new String(label);
	  }

	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
	    super.fireEditingStopped();
	  }
	}