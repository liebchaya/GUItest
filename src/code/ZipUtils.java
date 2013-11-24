package code;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.UnzipParameters;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.unzip.UnzipUtil;
import net.lingala.zip4j.util.Zip4jConstants;



public class ZipUtils {
	
	 private final static int BUFF_SIZE = 4096;

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
			 JOptionPane.showMessageDialog(null, e.getMessage(), "תיקיה חדשה", 1);
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
			  long milliseconds = calendar.getTimeInMillis();
			  
//			  JOptionPane.showMessageDialog(null, destination, "תיקיה לכיווץ", 0);
			  // Convert from millisecs to a String with a defined format
		      SimpleDateFormat date_format = new SimpleDateFormat("ddMMyyyy_HHmm");
		      Date resultdate = new Date(milliseconds);
		     
			  // Initiate ZipFile object with the path/name of the zip file.
			  String zipPath = destination+"_"+date_format.format(resultdate)+".zip";
			  
//			  JOptionPane.showMessageDialog(null, zipPath, "שגיאה ביצירת קובץ zip", 0);
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
//				 JOptionPane.showMessageDialog(null, e.getMessage(), "שגיאה ביצירת קובץ zip", 0);
			  e.printStackTrace();
			  return null;
			 }
	}
	

	   

	    public static void ExtractAllFilesWithInputStreams(String source, String destinationPath) {

	        ZipInputStream is = null;
	        OutputStream os = null;

	        try {
	            // Initiate the ZipFile
	            ZipFile zipFile = new ZipFile(source);

	            // If zip file is password protected then set the password
	            if (zipFile.isEncrypted()) {
	                zipFile.setPassword("password");
	            }

	            //Get a list of FileHeader. FileHeader is the header information for all the
	            //files in the ZipFile
	            List fileHeaderList = zipFile.getFileHeaders();

	            // Loop through all the fileHeaders
	            for (int i = 0; i < fileHeaderList.size(); i++) {
	                FileHeader fileHeader = (FileHeader)fileHeaderList.get(i);
	                if (fileHeader != null) {

	                    //Build the output file
	                    String outFilePath = destinationPath + System.getProperty("file.separator") + fileHeader.getFileName();
	                    File outFile = new File(outFilePath);

	                    //Checks if the file is a directory
	                    if (fileHeader.isDirectory()) {
	                        //This functionality is up to your requirements
	                        //For now I create the directory
	                        outFile.mkdirs();
	                        continue;
	                    }

	                    //Check if the directories(including parent directories)
	                    //in the output file path exists
	                    File parentDir = outFile.getParentFile();
	                    if (!parentDir.exists()) {
	                        parentDir.mkdirs();
	                    }

	                    //Get the InputStream from the ZipFile
	                    is = zipFile.getInputStream(fileHeader);
	                    //Initialize the output stream
	                    os = new FileOutputStream(outFile);

	                    int readLen = -1;
	                    byte[] buff = new byte[BUFF_SIZE];

	                    //Loop until End of File and write the contents to the output stream
	                    while ((readLen = is.read(buff)) != -1) {
	                        os.write(buff, 0, readLen);
	                    }

	                    //Please have a look into this method for some important comments
	                    closeFileHandlers(is, os);

	                    //To restore File attributes (ex: last modified file time, 
	                    //read only flag, etc) of the extracted file, a utility class
	                    //can be used as shown below
	                    UnzipUtil.applyFileAttributes(fileHeader, outFile);

	                    System.out.println("Done extracting: " + fileHeader.getFileName());
	                } else {
	                    System.err.println("fileheader is null. Shouldn't be here");
	                }
	            }
	        } catch (ZipException e) {
	            e.printStackTrace();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                closeFileHandlers(is, os);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    private static void closeFileHandlers(ZipInputStream is, OutputStream os) throws IOException{
	        //Close output stream
	        if (os != null) {
	            os.close();
	            os = null;
	        }

	        //Closing inputstream also checks for CRC of the the just extracted file.
	        //If CRC check has to be skipped (for ex: to cancel the unzip operation, etc)
	        //use method is.close(boolean skipCRCCheck) and set the flag,
	        //skipCRCCheck to false
	        //NOTE: It is recommended to close outputStream first because Zip4j throws 
	        //an exception if CRC check fails
	        if (is != null) {
	            is.close();
	            is = null;
	        }
	    }


	
}
