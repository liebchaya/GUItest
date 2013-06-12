package gui;

import java.awt.ComponentOrientation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreePanel{


public static JPanel getTreePanel(){

		JPanel panel = new JPanel(); 
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode("זכויות יוצרים", true);
		DefaultMutableTreeNode black = new DefaultMutableTreeNode("קנין רוחני");
		DefaultMutableTreeNode blue = new DefaultMutableTreeNode("מרחיקין מצודות");
		DefaultMutableTreeNode nBlue = new DefaultMutableTreeNode("משיג גבול");
		DefaultMutableTreeNode dBlue = new DefaultMutableTreeNode("יורד לאומנותו");
		DefaultMutableTreeNode green = new DefaultMutableTreeNode("מעתיק ספרים");
		parent.add(black);
		parent.add(blue);
		blue.add(nBlue);
		nBlue.add(dBlue);
		parent.add(green );
		JTree tree = new JTree(parent);
		tree.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(tree);
		return panel;
	}

}
