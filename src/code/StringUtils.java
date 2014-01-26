package code;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	public static LinkedList<String> convertStringToList(String str){
		LinkedList<String> set = new LinkedList<String>();
		for(String s:str.split(",")){
			s= s.replaceAll("\\[|\\]", "");
			if(!s.isEmpty())
				set.add(s.trim());
		}
		return set;
	}

	public static String cleanString(String str){
		str= str.replaceAll("[\n]+", "\n");
		str= str.replaceAll("\\[[\n| ]+", "");
		str= str.replaceAll("[\n| ]+\\]", "");
		str= str.replaceAll("\\[|\\]", "");
		str= str.replaceAll("[ ]+\n", "\n");
		str= str.replaceAll("\n[ ]+", "\n");
		str= str.replaceAll("[ ]+\t", "\t");
		str= str.replaceAll("\t[ ]+", "\t");
		str= str.replaceAll("\t\n", "\t");
		str= str.replaceAll("\n\t", "\t");
		
		
		str = str.trim();
		return str;
		
//		// using pattern with flags
//		Pattern pattern = Pattern.compile("\n");
//		Matcher matcher = pattern.matcher(str);
//		// using Matcher find(), group(), start() and end() methods
//		int startPos = 0;
//		while (matcher.find()) {
//			String line = str.substring(startPos,matcher.start());
//			if (!line.split("\t")[1].trim().isEmpty()){
//				System.out.println(line);
//			}
//			startPos = matcher.end();
//		}
//		
//		System.out.println(str);
		
	}

}
