package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

public class FrmChange extends JFrame{

private JPanel treePanel;
private JPanel judgePane;
private JPanel emptyPanel = new JPanel();


public FrmChange(){
	UIManager.put("OptionPane.yesButtonText", "כן");  
	UIManager.put("OptionPane.noButtonText", "לא");  
	UIManager.put("OptionPane.cancelButtonText", "ביטול");
	UIManager.put("OptionPane.okButtonText", "אישור");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    treePanel = TreePanel.getTreePanel();
    judgePane = JudgePanel.createMainView();
    initMenu();
//    panel2.setBackground(Color.RED);
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
      	   SelectTargetTermGui t = new SelectTargetTermGui();
      	   if(t.getTargetTerm()!= null)
      		 changePanel(judgePane);
      	   
         }
     });
     judgeMenu.add(judgeItem1);
     judgeMenu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
     JMenuItem targetItem1 = new JMenuItem("הצג תזארוס");
     targetItem1.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
      	   SelectTargetTermGui t = new SelectTargetTermGui();
      	   if(t.getTargetTerm()!= null)
      		 changePanel(treePanel);
      	   
         }
     });
     targetMenu.add(targetItem1);
     JMenuItem targetItem2 = new JMenuItem("הוסף מונח חדש");
     targetItem2.addActionListener(new ActionListener(){
         @Override
         public void actionPerformed(ActionEvent ae) {
        	 changePanel(emptyPanel);
      	   AddTargetTermGui t = new AddTargetTermGui();
      	   t.getTargetTerm();
      	   
         }
     });
     targetMenu.add(targetItem2);
     menubar.add(targetMenu);
     menubar.add(judgeMenu);
     this.setJMenuBar(menubar);
//    JMenuBar menubar = new JMenuBar();
//    JMenu menu = new JMenu("Menu");
//    JMenuItem menuItem1 = new JMenuItem("Panel1");
//    JMenuItem menuItem2 = new JMenuItem("Panel2");
//    menubar.add(menu);
//    menu.add(menuItem1);
//    menu.add(menuItem2);
//    setJMenuBar(menubar);
//    menuItem1.addActionListener(new MenuAction(treePanel));
//    menuItem2.addActionListener(new MenuAction(emptyPanel));

}

private void changePanel(JPanel panel) {
    getContentPane().removeAll();
    getContentPane().add(panel, BorderLayout.CENTER);
//    getContentPane().doLayout();
    this.validate();
    update(getGraphics());
}

private void changePane(JTabbedPane judgePane2) {
    getContentPane().removeAll();
    getContentPane().add(judgePane2, BorderLayout.CENTER);
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
