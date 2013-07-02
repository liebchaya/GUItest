package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;


public class DataFiles {

	public DataFiles(String filesDir){
		m_filesDir = filesDir;
	}
	
	/**
	 * Load data file to table model
	 * line format: term number, term, judgment, group, add group button, add expansion button, term lemmas(invisible)
	 * @param targetTerm
	 * @param fileSufix
	 * @throws IOException 
	 */
	public void loadDataFile2Table(String targetTerm,String fileSufix) throws IOException{
		m_modernPrevJudgments = new HashSet<Integer>();
		m_ancientPrevJudgments = new HashSet<Integer>();
		m_modernGroups = new Vector<String>();
		m_ancientGroups = new Vector<String>();
		// table headline
		String[] cols = {"#", "מונח", "שיפוט", "קבוצה", "הוספת קבוצה", "הוספת הרחבה", "למה", "אב"};
		m_vCols = new Vector();
		Vector v = new Vector();
		for( int i = 0; i < cols.length; ++i )
		{
			m_vCols.addElement(cols[i]);
		}
//		m_vCols.add(v);
		
		BufferedReader reader = new BufferedReader(new FileReader(m_filesDir+"/"+targetTerm+fileSufix));
		// skip the headline
		reader.readLine();
		String line = reader.readLine();
		// fill tables data
		int ancientCounter = 1, modernCounter = 1;
		m_vAncientData = new Vector();
		m_vModernData = new Vector();
		while (line != null) {
			String[] tokens = line.split("\t");
			if (Integer.parseInt(tokens[2])>0) {
				Vector vec = new Vector();
				vec.add(ancientCounter);
				vec.add(tokens[0]);
				int judge = Integer.parseInt(tokens[3]);
				if (judge != -99){
					m_ancientPrevJudgments.add(ancientCounter-1);
					if (judge > 0)
						vec.add(Boolean.TRUE);
					else
						vec.add(Boolean.FALSE);
				} else
					vec.add(Boolean.FALSE);
				ancientCounter ++;
				int group = Integer.parseInt(tokens[4]);
				if (group > -1){
					if (group >= m_ancientGroups.size())
						m_ancientGroups.add(group, tokens[0]);
					vec.add(m_ancientGroups.get(group));
				} else
					vec.add("");
				vec.add("הוסף קבוצה");
				vec.add("הוסף הרחבה");
				vec.add(tokens[1]);
				vec.add(tokens[5]);
				m_vAncientData.add(vec);
				}
			 else {
				Vector vec = new Vector();
				vec.add(modernCounter);
				vec.add(tokens[0]);
				int judge = Integer.parseInt(tokens[3]);
				if (judge != -99){
					m_modernPrevJudgments.add(modernCounter-1);
					if (judge > 0)
						vec.add(Boolean.TRUE);
					else
						vec.add(Boolean.FALSE);
				} else
					vec.add(Boolean.FALSE);
				modernCounter ++;
				int group = Integer.parseInt(tokens[4]);
				if (group > -1){
					if (group >= m_modernGroups.size())
						m_modernGroups.add(group, tokens[0]);
					vec.add(m_modernGroups.get(group));
				} else
					vec.add("");
				vec.add("הוסף קבוצה");
				vec.add("הוסף הרחבה");
				vec.add(tokens[1]);
				vec.add(tokens[5]);
				m_vModernData.add(vec);
			}
			line = reader.readLine();
		}
		reader.close();
	}
	
	
	
	/**
	 * @return the m_vCols
	 */
	public Vector getM_vCols() {
		return m_vCols;
	}



	private Vector m_vModernData;
	private Vector m_vAncientData;
	private Vector m_vCols;
	private String m_filesDir;
	private Set<Integer> m_modernPrevJudgments;
	private Set<Integer> m_ancientPrevJudgments;
	private Vector<String> m_modernGroups;
	private Vector<String> m_ancientGroups;
	/**
	 * @return the m_vModernData
	 */
	public Vector getM_vModernData() {
		return m_vModernData;
	}

	/**
	 * @param m_vModernData the m_vModernData to set
	 */
	public void setM_vModernData(Vector m_vModernData) {
		this.m_vModernData = m_vModernData;
	}

	/**
	 * @return the m_vAncientData
	 */
	public Vector getM_vAncientData() {
		return m_vAncientData;
	}


	/**
	 * @return the m_modernPrevJudgments
	 */
	public Set<Integer> getM_modernPrevJudgments() {
		return m_modernPrevJudgments;
	}


	/**
	 * @return the m_ancientPrevJudgments
	 */
	public Set<Integer> getM_ancientPrevJudgments() {
		return m_ancientPrevJudgments;
	}

	/**
	 * @return the m_modernGroups
	 */
	public Vector<String> getM_modernGroups() {
		return m_modernGroups;
	}

	/**
	 * @return the m_ancientGroups
	 */
	public Vector<String> getM_ancientGroups() {
		return m_ancientGroups;
	}


}
