package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import code.Constants;
import code.FileUtils;
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
//	change message box interface to Hebrew
	UIManager.put("OptionPane.yesButtonText", "כן");  
	UIManager.put("OptionPane.noButtonText", "לא");  
	UIManager.put("OptionPane.cancelButtonText", "ביטול");
	UIManager.put("OptionPane.okButtonText", "אישור");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    initMenu();
    setLayout(new BorderLayout());
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
	      	 String targetTerm = t.getTargetTerm(Constants.workingDir+Constants.judgmentsDir,Constants.judgmentFileSufix);
	      	 if(targetTerm != null){
	      	 try {
					judgePane = JudgePanel.createJudgementView(targetTerm);
				 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
	      		 changePanel(judgePane);
	      	  } 
	         }
     });
     judgeMenu.add(judgeItem1);
     JMenuItem judgeItem2 = new JMenuItem("שליחה");
     judgeItem2.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
        	 File judgmentDir = new File(Constants.workingDir+Constants.judgmentsDir);
        	 if (judgmentDir.listFiles() != null && judgmentDir.listFiles().length > 0){
        		 JOptionPane.showMessageDialog(null, "נותרו ערכים שלא נשפטו", "שגיאה בשליחת הנתונים", 0);
        	 } else {
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
						try {
							if(workingDir.exists())
								FileUtils.deleteRecursive(workingDir);
							ZipUtils.unzip(file.getAbsolutePath(), workingDir.getAbsolutePath().substring(0,workingDir.getAbsolutePath().lastIndexOf(File.separatorChar)));
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(null, "בעיה במחיקת או פרישת קבצים", "שגיאה בקבלת הנתונים", 0);
						}
	                 
	             } 
        
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
      	 String targetTerm = t.getTargetTerm(Constants.workingDir+Constants.thesaurusDir,Constants.thesaurusFileSufix);
      	 if(targetTerm != null){
      		 treePanel = TreePanel.getTreePanel(targetTerm);
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
      	   String selection = t.getTargetTerm(Constants.workingDir+Constants.thesaurusDir,Constants.thesaurusFileSufix);
      	   if (selection != null){
      		   File input = new File(Constants.workingDir+Constants.inputDir+"/"+Constants.inputFileName);
      		   FileWriter out;
			try {
				out = new FileWriter(input,true);
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

private void changePanel(JPanel panel) {
    getContentPane().removeAll();
    getContentPane().add(panel, BorderLayout.CENTER);
//    getContentPane().doLayout();
    this.validate();
    update(getGraphics());
}


public static void main(String[] args) {
    FrmChange frame = new FrmChange();
    frame.setBounds(200, 200, 300, 200);
    frame.setVisible(true);

}

}
