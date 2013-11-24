package gui;

import java.awt.Component;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;

public class MyCellEditor extends AbstractCellEditor implements TableCellEditor {

	
    public MyCellEditor(JComboBox combox, Set<Integer> prev) {
		super();
		combox.setSelectedItem(null);
		checkbox = new DefaultCellEditor(combox);
		prevRows = prev;
	}

	DefaultCellEditor other = new DefaultCellEditor(new JTextField());
    DefaultCellEditor checkbox = null;
    Set<Integer> prevRows = null;

    private DefaultCellEditor lastSelected;

    @Override
    public Object getCellEditorValue() {

        return lastSelected.getCellEditorValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int column) {
        if(!prevRows.contains(row)) {
            lastSelected = checkbox;
            return checkbox.getTableCellEditorComponent(table, value, isSelected, row, column);
        }
        other = new DefaultCellEditor(new JTextField(table.getModel().getValueAt(row, column).toString()));
        lastSelected = other;
        return other.getTableCellEditorComponent(table, value, isSelected, row, column);
    }

	
}