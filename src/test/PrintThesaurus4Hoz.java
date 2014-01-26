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

public class PrintThesaurus4Hoz {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String inputFile = "C:\\Documents and Settings\\HZ\\Desktop\\_home_ir_liebesc_QEsys__03012014_0204\\input\\targetTerms_orig.txt";
		String outputFile = "C:\\Documents and Settings\\HZ\\Desktop\\_home_ir_liebesc_QEsys__03012014_0204\\input\\output.txt";
		File thesaurusDir = new File("C:\\Documents and Settings\\HZ\\Desktop\\_home_ir_liebesc_QEsys__03012014_0204\\thesaurus");
		HashMap<Integer,String> target2name = new HashMap<Integer, String>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
			    new FileInputStream(inputFile), "UTF-8"));
		String line = reader.readLine();
		while (line!=null){
			target2name.put(Integer.parseInt(line.split("\t")[0]), line.split("\t")[1]);
			line = reader.readLine();
		}
		reader.close();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		for(File f:thesaurusDir.listFiles()){
			if (f.getAbsolutePath().endsWith(".thes")){
			reader = new BufferedReader(new FileReader(f));
			String targetTerm = f.getName().substring(0,f.getName().indexOf("."));
			line = reader.readLine();
			while (line!=null){
				String val = NgramViewer.mergeNgrams(StringUtils.convertStringToList("["+line.split("\t")[0]+"]"));
				writer.write(target2name.get(Integer.parseInt(targetTerm)) + "\t"+ val + "\n");
				line = reader.readLine();
			}
			reader.close();
			}
		}
		writer.close();

	}

}
