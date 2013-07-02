package gui;

import java.awt.ComponentOrientation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import code.Constants;

public class TreePanel{

/**
 * Dynamically draw of the thesaurus' tree view
 * @param targetTerm
 * @return
 */
public static JPanel getTreePanel(String targetTerm){

		JPanel panel = new JPanel(); 
		
//		load the thesaurus file to a hash map
		HashMap<String,Vector<String>> treeMap = new HashMap<String, Vector<String>>();
		try {
			String path = Constants.workingDir+Constants.thesaurusDir+"/"+targetTerm+Constants.thesaurusFileSufix;
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			line = reader.readLine();
			
			while(line != null){
				String[] tokens = line.split("\t");
				if(treeMap.containsKey(tokens[1]))
					treeMap.get(tokens[1]).add(tokens[0]);
				else {
					Vector<String> vec = new Vector<String>();
					vec.add(tokens[0]);
					treeMap.put(tokens[1], vec);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		build the tree view
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(targetTerm, true);
		Queue<DefaultMutableTreeNode> qe=new LinkedList<DefaultMutableTreeNode>();
		qe.add(root);
		while(!qe.isEmpty()){
			DefaultMutableTreeNode curNode = qe.poll();
			String curName = curNode.toString();
			System.out.println(curName);
			if (!treeMap.containsKey(curName))
				continue;
			for(String exp:treeMap.get(curName)){
				DefaultMutableTreeNode expNode = new DefaultMutableTreeNode(exp);
				curNode.add(expNode);
				qe.add(expNode);
			}
		}
		JTree tree = new JTree(root);
		tree.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		panel.add(tree);
		return panel;
	}

}
