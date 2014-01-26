package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import code.NgramViewer;
import code.StringUtils;

public class Add1missingRow {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File annoFolder = new File("C:\\Documents and Settings\\HZ\\QEsys\\annotatedLast\\");
		File judgFolder = new File("C:\\Documents and Settings\\HZ\\Desktop\\_19122013_1504Step1Output\\judgments\\");
		File newFolder = new File("C:\\Documents and Settings\\HZ\\Desktop\\newAnno\\");
		for(File f:judgFolder.listFiles()){
			if (f.getAbsolutePath().endsWith(".dataGroups")){
				String firstLine = null;
				BufferedReader reader = new BufferedReader(new FileReader(f));
				String line = reader.readLine();
				if(line != null)
					firstLine = line;
				reader.close();
				File annoFile = new File(annoFolder+"\\"+f.getName());
				if (!annoFile.exists())
					System.out.println("problem with file " + f.getName());
				else {
					BufferedWriter writer = new BufferedWriter(new FileWriter(newFolder+"\\"+f.getName()));
					reader = new BufferedReader(new FileReader(annoFile));
					writer.write("\n");
					writer.write(firstLine+"\n");
					reader.readLine(); // skip first empty row
					line = reader.readLine();
					while (line!=null){
						writer.write(line + "\n");
						line = reader.readLine();
					}
					reader.close();
					writer.close();
				}
			}
		}
	}

}
