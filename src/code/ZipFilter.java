package code;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ZipFilter extends FileFilter {
	 
    //Accept all directories and all zip, rar, tar, or gzip files.
	@Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("zip") ||
                extension.equals("rar") ||
                extension.equals("tar") ||
                extension.equals("gzip") ) {
                    return true;
            } else {
                return false;
            }
        }
 
        return false;
    }
 
    //The description of this filter
    public String getDescription() {
        return "Zip files";
    }
    
    /*
     * Get the extension of a file.
     */  
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
