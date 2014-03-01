package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {
    /**
     * By default File#delete fails for non-empty directories, it works like "rm". 
     * We need something a little more brutual - this does the equivalent of "rm -r"
     * @param path Root File Path
     * @return true iff the file and all sub files/directories have been removed
     * @throws FileNotFoundException
     */
    public static boolean deleteRecursive(File path) throws FileNotFoundException{
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
                ret = ret && FileUtils.deleteRecursive(f);
            }
        }
        return ret && path.delete();
    }
    
    public static void copyFileUsingFileStreams(File source, File dest) throws IOException {
    	InputStream input = null;
    	OutputStream output = null;
    	try {
    		input = new FileInputStream(source);
    		output = new FileOutputStream(dest);
    		byte[] buf = new byte[1024];
    		int bytesRead;
    		while ((bytesRead = input.read(buf)) > 0) {
    			output.write(buf, 0, bytesRead);
    		}
    	} finally {
    		input.close();
    		output.close();
    	}
    }
    
    /**
     * Read a file to String
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
}
