package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import code.Constants;
import code.FileUtils;
import code.StringUtils;
import code.ZipFilter;
import code.ZipUtils;

/**
 * Main class that manages the menu and switches windows
 * @author HZ
 *
 */
public class FrmChange extends JFrame{

private JPanel treePanel;
private JPanel judgePane;
private JPanel emptyPanel = new JPanel();


public FrmChange(){
	FontUIResource font = new FontUIResource("Dialog", Font.PLAIN, 20);
	UIManager.put("Table.font", font); 
	UIManager.put("Panel.font", font);
	UIManager.put("Button.font", font);
	UIManager.put("Menu.font", font);
	UIManager.put("Label.font", font);
	UIManager.put("ComboBox.font", font);
	UIManager.put("MenuItem.font", font);
	UIManager.put("TabbedPane.font", font);
	UIManager.put("Tree.font", font);
	
	
	
//	UIManager.put("swing.plaf.metal.controlFont", font);
	UIManager.getLookAndFeelDefaults().put("defaultFont",new Font("Dialog", Font.PLAIN, 20));
//	-Dswing.plaf.metal.controlFont=Dialog-20
//	change message box interface to Hebrew
	UIManager.put("OptionPane.yesButtonText", "כן");  
	UIManager.put("OptionPane.noButtonText", "לא");  
	UIManager.put("OptionPane.cancelButtonText", "ביטול");
	UIManager.put("OptionPane.okButtonText", "אישור");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    initMenu();
    setLayout(new BorderLayout());
    System.out.println(Constants.workingDir);
}

private class MenuAction implements ActionListener {

    private JPanel panel;
    private MenuAction(JPanel pnl) {
        this.panel = pnl;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        changePanel(panel);

    }

}

/**
 * Initialize the menu and its events
 */
private void initMenu() {
	 JMenuBar menubar = new JMenuBar();
     menubar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
     JMenu targetMenu = new JMenu("תזארוס");
     targetMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
     JMenu judgeMenu = new JMenu("שיפוטים");
     JMenuItem judgeItem1 = new JMenuItem("בצע שיפוט");
     judgeItem1.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
        	 // message box for target term selection
	      	 SelectTargetTermGui t = new SelectTargetTermGui();
	      	 String targetTerm = null;
			try {
				targetTerm = t.getTargetTerm(Constants.workingDir+Constants.inputDir+"/"+Constants.inputFileName, Constants.workingDir+Constants.judgmentsDir,Constants.judgmentFileSufix);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      	 if(targetTerm != null){
	      	 try {
					judgePane = JudgePanel.createJudgementView(targetTerm,t.getM_targetTerm());
				 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
	      		 changePanel(judgePane);
	      	  } 
	         }
     });
     judgeMenu.add(judgeItem1);
     JMenuItem judgeItem12 = new JMenuItem("ערוך שיפוט");
     judgeItem12.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
        	 // message box for target term selection
	      	 SelectTargetTermGui t = new SelectTargetTermGui();
	      	 String targetTerm = null;
			try {
				targetTerm = t.getTargetTerm(Constants.workingDir+Constants.inputDir+"/"+Constants.inputFileName, Constants.workingDir+Constants.annotatedDir,Constants.judgmentFileSufix);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	      	 if(targetTerm != null){
	      	 try {
					judgePane = EditJudgePanel.createJudgementView(targetTerm,t.getM_targetTerm());
				 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
	      		 changePanel(judgePane);
	      	  } 
	         }
     });
     judgeMenu.add(judgeItem12);
     JMenuItem judgeItem2 = new JMenuItem("שליחה");
     judgeItem2.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
        	 File judgmentDir = new File(Constants.workingDir+Constants.judgmentsDir);
        	 if (judgmentDir.listFiles() != null && judgmentDir.listFiles().length > 0){
        		 JOptionPane.showMessageDialog(null, "נותרו ערכים שלא נשפטו", "שגיאה בשליחת הנתונים", 0);
        	 } else {
        		 try {
	        		 // clean exapnsions file
	        		 File file = new File(Constants.workingDir+Constants.inputDir+"\\"+Constants.expFileName);
	    		     FileInputStream fis;
					 fis = new FileInputStream(file);
					 byte[] data = new byte[(int)file.length()];
	    		     fis.read(data);
	    		     fis.close();
	    		     
	    		     String content = new String(data);
	    		     content = StringUtils.cleanString(content);
	    		     BufferedWriter writer= new BufferedWriter(new FileWriter(file));
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
	    				writer.write(line+"\n");
	    				startPos = matcher.end();
	    			}
	    			writer.write(content.substring(startPos)+"\n");		
					writer.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		    
    		        
        		 String zipPath = ZipUtils.zip(Constants.workingDir);
        		 if (zipPath == null)
        			 JOptionPane.showMessageDialog(null, "בעיה בקיבוץ התיקיה", "שגיאה בשליחת הנתונים", 0);
        		 else
        			 JOptionPane.showMessageDialog(null, "הקובץ הנוצר הוא\n"+zipPath, "נתונים נשלחו בהצלחה", 1);
        	 } 
         }
     });
     judgeMenu.add(judgeItem2);
     JMenuItem judgeItem3 = new JMenuItem("הזנת קבצים");
     judgeItem3.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
        	 File judgmentDir = new File(Constants.workingDir+Constants.judgmentsDir);
        	 if (judgmentDir.listFiles()!= null && judgmentDir.listFiles().length > 0){
        		 JOptionPane.showMessageDialog(null, "נותרו ערכים שלא נשפטו", "שגיאה בשליחת הנתונים", 0);
        	 } else {
	        	 final JFileChooser fc = new JFileChooser();
	        	 fc.addChoosableFileFilter(new ZipFilter());
	        	 int returnVal = fc.showDialog(null,"פתיחה");
	             if (returnVal == JFileChooser.APPROVE_OPTION) {
	                 File file = fc.getSelectedFile();
	                 //This is where a real application would open the file.
	                 System.out.println(file.getName());
	                 File workingDir = new File(Constants.workingDir);
						if(workingDir.exists())
							try {
								FileUtils.deleteRecursive(workingDir);
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						ZipUtils.unzip(file.getAbsolutePath(), workingDir.getAbsolutePath());
	                 
	             } 
	             File prevTargetTerms = new File(Constants.workingDir+Constants.inputDir+"/"+Constants.newInputFileName);
	             if (prevTargetTerms.exists())
	            	 prevTargetTerms.delete();
        
        	 } 
         }
     });
     judgeMenu.add(judgeItem3);
     judgeMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
     JMenuItem targetItem1 = new JMenuItem("הצג תזארוס");
     targetItem1.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
      	   SelectTargetTermGui t = new SelectTargetTermGui();
      	 String targetTerm=null;
		try {
			targetTerm = t.getTargetTerm(Constants.workingDir+Constants.thesaurusDir+"/"+Constants.theasaurusTermsFileName,Constants.workingDir+Constants.thesaurusDir,Constants.thesaurusFileSufix);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	 if(targetTerm != null){
      		 treePanel = TreePanel.getTreePanel(targetTerm, t.getM_targetTerm());
      		 changePanel(treePanel);
      	   
         }
         }
     }
     );
     targetMenu.add(targetItem1);
     JMenuItem targetItem2 = new JMenuItem("הוסף מונח חדש");
     targetItem2.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
      	   AddTargetTermGui t = new AddTargetTermGui();
      	   String selection=null;
		try {
			selection = t.getTargetTerm(Constants.workingDir+Constants.thesaurusDir+"/"+Constants.theasaurusTermsFileName,Constants.workingDir+Constants.thesaurusDir,Constants.thesaurusFileSufix);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      	   if (selection != null){
      		   File input = new File(Constants.workingDir+Constants.inputDir+"/"+Constants.newInputFileName);
      		   OutputStreamWriter out;
			try {
				FileOutputStream fileStream = new FileOutputStream(input);
				out = new OutputStreamWriter(fileStream, "UTF-8");
				out.write(selection + "\n");
		      	out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}   
      	   }
      	   
         }
     });
     targetMenu.add(targetItem2);
     menubar.add(targetMenu);
     menubar.add(judgeMenu);
     this.setJMenuBar(menubar);

}

public void changePanel(JPanel panel) {
    getContentPane().removeAll();
    getContentPane().add(panel, BorderLayout.CENTER);
//    getContentPane().doLayout();
    this.validate();
    update(getGraphics());
}


public static void main(String[] args) {
    FrmChange frame = new FrmChange();
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setTitle("מערכת לבניית תזארוס");
    frame.setBounds(200, 200, 300, 200);
    frame.setVisible(true);
    

}


}
