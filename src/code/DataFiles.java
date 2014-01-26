package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;


public class DataFiles {

	public DataFiles(String filesDir){
		m_filesDir = filesDir;
	}
	
	public void loadExpansionsData(String expFile) throws IOException{
		m_ancientExpanMap = new HashMap<Integer, LinkedList<String>>();
		m_modernExpanMap = new HashMap<Integer, LinkedList<String>>();
		
		File file = new File(expFile);
	    FileInputStream fis = new FileInputStream(file);
	    byte[] data = new byte[(int)file.length()];
	    fis.read(data);
	    fis.close();
	    String content = new String(data);
	    content = StringUtils.cleanString(content);
	 // using pattern with flags
		Pattern pattern = Pattern.compile("\n");
		Matcher matcher = pattern.matcher(content);
		// using Matcher find(), group(), start() and end() methods
		int startPos = 0;
		while (matcher.find()) {
			String line = content.substring(startPos,matcher.start());
			if (line.split("\t")[1].trim().isEmpty()){
				startPos = matcher.end();
				continue;
			}
			String[] tokens = line.split("\t");
			if(Integer.parseInt(tokens[3])==1){
				if (m_ancientExpanMap.containsKey(Integer.parseInt(tokens[0])))
					m_ancientExpanMap.get(Integer.parseInt(tokens[0])).add(tokens[1]+"\t"+tokens[2]);
				else{
					LinkedList<String> lst = new LinkedList<String>(); 
					lst.add(tokens[1]+"\t"+tokens[2]);
					m_ancientExpanMap.put(Integer.parseInt(tokens[0]),lst);
				}
			} else {
				if (m_modernExpanMap.containsKey(Integer.parseInt(tokens[0])))
					m_modernExpanMap.get(Integer.parseInt(tokens[0])).add(tokens[1]+"\t"+tokens[2]);
				else{
					LinkedList<String> lst = new LinkedList<String>(); 
					lst.add(tokens[1]+"\t"+tokens[2]);
					m_modernExpanMap.put(Integer.parseInt(tokens[0]),lst);
				}
			}
			startPos = matcher.end();
		}
		
		String[] tokens = content.substring(startPos).split("\t");
		if(Integer.parseInt(tokens[3])==1){
			if (m_ancientExpanMap.containsKey(Integer.parseInt(tokens[0])))
				m_ancientExpanMap.get(Integer.parseInt(tokens[0])).add(tokens[1]+"\t"+tokens[2]);
			else{
				LinkedList<String> lst = new LinkedList<String>(); 
				lst.add(tokens[1]+"\t"+tokens[2]);
				m_ancientExpanMap.put(Integer.parseInt(tokens[0]),lst);
			}
		} else {
			if (m_modernExpanMap.containsKey(Integer.parseInt(tokens[0])))
				m_modernExpanMap.get(Integer.parseInt(tokens[0])).add(tokens[1]+"\t"+tokens[2]);
			else{
				LinkedList<String> lst = new LinkedList<String>(); 
				lst.add(tokens[1]+"\t"+tokens[2]);
				m_modernExpanMap.put(Integer.parseInt(tokens[0]),lst);
			}
		}
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
		loadGroupsVectors(targetTerm);
		HashMap<Integer,Integer> modernConvertMap = convertHashMap(false);
		HashMap<Integer,Integer> ancientConvertMap = convertHashMap(true);
		// table headline
		String[] cols = {"#", "מונח", "שיפוט", "קבוצה", "ה.קבוצה", "ה.הרחבה", "למה", "אב", "דור"};
		m_vCols = new Vector();
		Vector v = new Vector();
		for( int i = 0; i < cols.length; ++i )
		{
			m_vCols.addElement(cols[i]);
		}
//		m_vCols.add(v);
		
		BufferedReader reader = new BufferedReader(new FileReader(m_filesDir+"/"+targetTerm+fileSufix));
		// skip the headline
		String line = reader.readLine();
		if (line.isEmpty()||line.startsWith("Term"))
			line = reader.readLine();
		System.out.println(line);
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
//					if (group >= m_ancientGroups.size())
//						m_ancientGroups.add(group, tokens[0]);
//					vec.add(m_ancientGroups.get(group));
					vec.add(m_ancientGroups.get(ancientConvertMap.get(group)));
				} else
					vec.add("");
				vec.add("הוסף");
				vec.add("הוסף");
				vec.add(tokens[1]);
				vec.add(tokens[5]);
				vec.add(tokens[6]);
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
//					if (group >= m_modernGroups.size())
//						m_modernGroups.add(group, tokens[0]);
//					vec.add(m_modernGroups.get(group));
					vec.add(m_modernGroups.get(modernConvertMap.get(group)));
				} else
					vec.add("");
				vec.add("הוסף");
				vec.add("הוסף");
				vec.add(tokens[1]);
				vec.add(tokens[5]);
				vec.add(tokens[6]);
				m_vModernData.add(vec);
			}
			line = reader.readLine();
		}
		reader.close();
	}
	
	
	/**
	 * Load data file to table model
	 * line format: term number, term, judgment, group, add group button, add expansion button, term lemmas(invisible)
	 * @param targetTerm
	 * @param fileSufix
	 * @throws IOException 
	 */
	public void loadDataFile2Table4Edit(String targetTerm,String fileSufix) throws IOException{
		m_modernPrevJudgments = new HashSet<Integer>();
		m_ancientPrevJudgments = new HashSet<Integer>();
		Vector<Integer> vecPair = loadGroupsVectors4Edit(targetTerm);
		HashMap<Integer,Integer> modernConvertMap = convertHashMap(false);
		HashMap<Integer,Integer> ancientConvertMap = convertHashMap(true);
		// table headline
		String[] cols = {"#", "מונח", "שיפוט", "קבוצה", "ה.קבוצה", "ה.הרחבה", "למה", "אב","דור"};
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
		System.out.println(line);
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
//				vec.add(NgramViewer.mergeNgrams(StringUtils.convertStringToSet(tokens[0])));
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
					if (group > m_maxGroupId)
						m_maxGroupId = group;
//					if (group >= m_ancientGroups.size())
//						m_ancientGroups.add(group, tokens[0]);
//					vec.add(m_ancientGroups.get(group));
					if (!ancientConvertMap.containsKey(group)){
						String desc = NgramViewer.mergeNgrams(StringUtils.convertStringToList(tokens[0]));
						m_ancientGroups.add(vecPair.get(0),desc);
//						m_ancientGroups.add(vecPair.get(0),tokens[0]);
						m_ancientGroupsConvertMap.put(vecPair.get(0), group);
						ancientConvertMap.put(group, vecPair.get(0));
						vecPair.set(0,vecPair.get(0)+1);
					}
					vec.add(m_ancientGroups.get(ancientConvertMap.get(group)));
				} else
					vec.add("");
				vec.add("הוסף");
				vec.add("הוסף");
				vec.add(tokens[1]);
				vec.add(tokens[5]);
				vec.add(tokens[6]);
				m_vAncientData.add(vec);
				}
			 else {
				Vector vec = new Vector();
				vec.add(modernCounter);
				vec.add(tokens[0]);
//				vec.add(NgramViewer.mergeNgrams(StringUtils.convertStringToSet(tokens[0])));
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
					if (group > m_maxGroupId)
						m_maxGroupId = group;
//					if (group >= m_modernGroups.size())
//						m_modernGroups.add(group, tokens[0]);
//					vec.add(m_modernGroups.get(group));
					if (!modernConvertMap.containsKey(group)){
						modernConvertMap.put(group, vecPair.get(1));
						String desc = NgramViewer.mergeNgrams(StringUtils.convertStringToList(tokens[0]));
//						m_modernGroups.add(vecPair.get(1),tokens[0]);
						m_modernGroups.add(vecPair.get(1),desc);
						m_modernGroupsConvertMap.put(vecPair.get(1), group);
						vecPair.set(1,vecPair.get(1)+1);
					}
					vec.add(m_modernGroups.get(modernConvertMap.get(group)));
				} else
					vec.add("");
				vec.add("הוסף");
				vec.add("הוסף");
				vec.add(tokens[1]);
				vec.add(tokens[5]);
				vec.add(tokens[6]);
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
	private HashMap<Integer,Integer> m_modernGroupsConvertMap;
	private HashMap<Integer,Integer> m_ancientGroupsConvertMap;
	private int m_maxGroupId = Integer.MIN_VALUE;
	private HashMap<Integer,LinkedList<String>> m_ancientExpanMap;
	private HashMap<Integer,LinkedList<String>> m_modernExpanMap;
	
	
	public HashMap<Integer, LinkedList<String>> getM_ancientExpanMap() {
		return m_ancientExpanMap;
	}

	public void setM_ancientExpanMap(
			HashMap<Integer, LinkedList<String>> m_ancientExpanMap) {
		this.m_ancientExpanMap = m_ancientExpanMap;
	}

	public HashMap<Integer, LinkedList<String>> getM_modernExpanMap() {
		return m_modernExpanMap;
	}

	public void setM_modernExpanMap(
			HashMap<Integer, LinkedList<String>> m_modernExpanMap) {
		this.m_modernExpanMap = m_modernExpanMap;
	}

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
	
	
	/**
	 * @return the m_modernGroupsConvertMap
	 */
	public HashMap<Integer, Integer> getM_modernGroupsConvertMap() {
		return m_modernGroupsConvertMap;
	}

	/**
	 * @return the m_ancientGroupsConvertMap
	 */
	public HashMap<Integer, Integer> getM_ancientGroupsConvertMap() {
		return m_ancientGroupsConvertMap;
	}


	/**
	 * @return the m_maxGroupId
	 */
	public int getM_maxGroupId() {
		return m_maxGroupId;
	}

	
	/**
	 * Load groups data
	 * @param targetTerm
	 * @throws IOException
	 */
	private void loadGroupsVectors(String targetTerm) throws IOException{
		m_modernGroupsConvertMap = new HashMap<Integer, Integer>();
		m_ancientGroupsConvertMap = new HashMap<Integer, Integer>();
		int modernIndex = 1, ancientIndex = 1;
		m_modernGroups = new Vector<String>();
		m_modernGroups.add("");
		m_ancientGroups = new Vector<String>();
		m_ancientGroups.add("");
		File groupsFile = new File(m_filesDir+"/"+targetTerm+Constants.groupsFileSufix);
		if (groupsFile.exists()){
			BufferedReader reader = new BufferedReader(new FileReader(groupsFile));
			String line = reader.readLine();
			while (line != null){
				String[] tokens = line.split("\t");
				int oldCount = Integer.parseInt(tokens[2]);
				int groupId = Integer.parseInt(tokens[0]);
				if (groupId > m_maxGroupId)
					m_maxGroupId = groupId;
				if (oldCount > 0){
					String desc = NgramViewer.mergeNgrams(StringUtils.convertStringToList(tokens[1]));
					m_ancientGroups.add(ancientIndex,desc);
//					m_ancientGroups.add(ancientIndex,tokens[1]);
					m_ancientGroupsConvertMap.put(ancientIndex, groupId);
					ancientIndex++;
				}
				else {
					String desc = NgramViewer.mergeNgrams(StringUtils.convertStringToList(tokens[1]));
					m_modernGroups.add(modernIndex,desc);
//					m_modernGroups.add(modernIndex,tokens[1]);
					m_modernGroupsConvertMap.put(modernIndex, groupId);
					modernIndex++;
				}
				line = reader.readLine();
			}
			reader.close();
		}
		
	}
	
	/**
	 * Load groups data
	 * @param targetTerm
	 * @throws IOException
	 */
	private Vector<Integer> loadGroupsVectors4Edit(String targetTerm) throws IOException{
		m_modernGroupsConvertMap = new HashMap<Integer, Integer>();
		m_ancientGroupsConvertMap = new HashMap<Integer, Integer>();
		int modernIndex = 1, ancientIndex = 1;
		m_modernGroups = new Vector<String>();
		m_modernGroups.add("");
		m_ancientGroups = new Vector<String>();
		m_ancientGroups.add("");
		File groupsFile = new File(m_filesDir+"/"+targetTerm+Constants.groupsFileSufix);
		if (groupsFile.exists()){
			BufferedReader reader = new BufferedReader(new FileReader(groupsFile));
			String line = reader.readLine();
			while (line != null){
				String[] tokens = line.split("\t");
				int oldCount = Integer.parseInt(tokens[2]);
				int groupId = Integer.parseInt(tokens[0]);
				if (groupId > m_maxGroupId)
					m_maxGroupId = groupId;
				if (oldCount > 0){
					String desc = NgramViewer.mergeNgrams(StringUtils.convertStringToList(tokens[1]));
					m_ancientGroups.add(ancientIndex,desc);
//					m_ancientGroups.add(ancientIndex,tokens[1]);
					m_ancientGroupsConvertMap.put(ancientIndex, groupId);
					ancientIndex++;
				}
				else {
					String desc = NgramViewer.mergeNgrams(StringUtils.convertStringToList(tokens[1]));
					m_modernGroups.add(modernIndex,desc);
//					m_modernGroups.add(modernIndex,tokens[1]);
					m_modernGroupsConvertMap.put(modernIndex, groupId);
					modernIndex++;
				}
				line = reader.readLine();
			}
			reader.close();
		}
		Vector<Integer> indexVec = new Vector<Integer>();
		indexVec.add(0,ancientIndex);
		indexVec.add(1,modernIndex);
		return indexVec;
	}
	
	private HashMap<Integer,Integer> convertHashMap(boolean isAncient){
		HashMap<Integer,Integer> convMap = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> map;
		if(isAncient)
			map = m_ancientGroupsConvertMap;
		else
			map = m_modernGroupsConvertMap;
		for(int key:map.keySet())
			convMap.put(map.get(key), key);
		return convMap;
	}


}
