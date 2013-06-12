package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
* Applied background and foreground color to single column of a JTable
* in order to distinguish it apart from other columns.
*/ 
class ColorColumnRenderer extends DefaultTableCellRenderer 
{
   Color bkgndColor, fgndColor;
 	
   public ColorColumnRenderer(Color bkgnd, Color foregnd) {
      super(); 
      bkgndColor = bkgnd;
      fgndColor = foregnd;
   }
  	
   public Component getTableCellRendererComponent
	    (JTable table, Object value, boolean isSelected,
	     boolean hasFocus, int row, int column) 
   {
      Component cell = super.getTableCellRendererComponent
         (table, value, isSelected, hasFocus, row, column);
 
      cell.setBackground( bkgndColor );
      cell.setForeground( fgndColor );
      
      this.setHorizontalAlignment( JLabel.CENTER );
     
      return cell;
   }
   
  
}