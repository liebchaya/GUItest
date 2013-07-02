package code;

import java.util.Calendar;
import java.util.Date;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;



public class ZipUtils {

	/**
	 * Unzip a folder
	 * @param source
	 * @param destination
	 */
	public static void unzip(String source, String destination){ 
		 String password = "password";  
		 try {  
		   ZipFile zipFile = new ZipFile(source);  
		   if (zipFile.isEncrypted()) {  
		     zipFile.setPassword(password);  
		   }  
		   zipFile.extractAll(destination);  
		 } catch (ZipException e) {  
		   e.printStackTrace();  
		 }  
	    }
	
	/**
	 * Create a compressed archive of a folder, adding a timestamp in milliseconds to the archive name.  
	 * This allows your application to create multiple archives without overwriting previous archives.
	 * @param destination
	 * @return zip file path
	 */
	public static String zip(String destination){
		try {
			  Calendar calendar = Calendar.getInstance();
			  Date time = calendar.getTime();
			  long milliseconds = time.getTime();

			  // Initiate ZipFile object with the path/name of the zip file.
			  String zipPath = destination+"_"+milliseconds+".zip";
			  ZipFile zipFile = new ZipFile(zipPath);

			  // Folder to add
			  String folderToAdd = destination;

			  // Initiate Zip Parameters which define various properties such
			  // as compression method, etc.
			  ZipParameters parameters = new ZipParameters();

			  // set compression method to store compression
			  parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

			  // Set the compression level
			  parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

			  // Add folder to the zip file
			  zipFile.addFolder(folderToAdd, parameters);
			  
			  return zipPath;

			 } catch (ZipException e) {
			  e.printStackTrace();
			  return null;
			 }
	}
	
}
