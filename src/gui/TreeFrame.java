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
		
		DefaultMutableTreeNode parent = new DefaultMutableTreeNode("������ ������", true);
		DefaultMutableTreeNode black = new DefaultMutableTreeNode("���� �����");
		DefaultMutableTreeNode blue = new DefaultMutableTreeNode("������� ������");
		DefaultMutableTreeNode nBlue = new DefaultMutableTreeNode("���� ����");
		DefaultMutableTreeNode dBlue = new DefaultMutableTreeNode("���� ��������");
		DefaultMutableTreeNode green = new DefaultMutableTreeNode("����� �����");
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
