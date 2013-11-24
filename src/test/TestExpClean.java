package test;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import code.Constants;
import code.StringUtils;

public class TestExpClean {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File file = new File(Constants.workingDir+Constants.inputDir+"\\"+Constants.expFileName);
	    FileInputStream fis = new FileInputStream(file);
	    byte[] data = new byte[(int)file.length()];
	    fis.read(data);
	    fis.close();
	    //
	    String s = new String(data);
//	    System.out.println(s);
//	    System.out.println(StringUtils.cleanString(s));
	    StringUtils.cleanString(s);

	}

}
