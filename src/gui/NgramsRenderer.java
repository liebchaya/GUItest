package gui;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;

import code.NgramViewer;
import code.StringUtils;

class NgramsRenderer extends DefaultTableCellRenderer {

	
private static final long serialVersionUID = 1L;
private String valueToString = "";

NgramsRenderer(){
	this.setHorizontalAlignment( JLabel.RIGHT );
}
@Override
public void setValue(Object value) {
    if ((value != null)) {
        String stringFormat = value.toString();
        valueToString = NgramViewer.mergeNgrams(StringUtils.convertStringToSet(stringFormat));
        value = valueToString;
    }
    super.setValue(value);
}
}