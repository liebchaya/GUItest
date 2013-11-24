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
import code.NgramViewer;
import code.StringUtils;

public class TreePanel{

/**
 * Dynamically draw of the thesaurus' tree view
 * @param targetTermId
 * @return
 */
public static JPanel getTreePanel(String targetTermId, String targetTerm){

		JPanel panel = new JPanel(); 
		
//		load the thesaurus file to a hash map
		HashMap<String,Vector<String>> treeMap = new HashMap<String, Vector<String>>();
		try {
			String path = Constants.workingDir+Constants.thesaurusDir+"/"+targetTermId+Constants.thesaurusFileSufix;
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			line = reader.readLine();
			
			while(line != null){
				String[] tokens = line.split("\t");
				String t0 = (tokens[0].contains(",")?NgramViewer.mergeNgrams(StringUtils.convertStringToSet("["+tokens[0]+"]")):tokens[0]);
				t0 = t0.replaceAll("\\[|\\]", "");
				String t1 = (tokens[1].contains(",")?NgramViewer.mergeNgrams(StringUtils.convertStringToSet("["+tokens[1]+"]")):tokens[1]);
				t1 = t1.replaceAll("\\[|\\]", "");
				if(treeMap.containsKey(t1))
					treeMap.get(t1).add(t0);
				else {
					Vector<String> vec = new Vector<String>();
					vec.add(t0);
					treeMap.put(t1, vec);
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
