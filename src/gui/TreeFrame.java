package gui;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TreeFrame frame = new TreeFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TreeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		
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
		contentPane.add(tree);
		setContentPane(contentPane);
		setSize(200,200);
	}

}
