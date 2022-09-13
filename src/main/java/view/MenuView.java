/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.MenuController;
import data.CONSTANT;
import events.iEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import listeners.ListenerI;

/**
 *
 * @author legen
 */
public class MenuView extends JMenuBar implements ListenerI, ActionListener, iView {
     MenuController controller;
     
      public MenuView(MenuController ctrl){
        super();
        controller = ctrl;
        buildView();
    }
    private void buildView() {
      // les menus 
        JMenuBar menu = new JMenuBar();
        JMenu panel = new JMenu("Panels");
        panel.setMnemonic( 'P' );
        
        // Définir le sous-menu pour Fichier
        JMenuItem param = new JMenuItem(CONSTANT.PAGE_PARAM);
        param.setMnemonic( 'A' );
        JMenuItem scan = new JMenuItem(CONSTANT.PAGE_SCAN);
        scan.setMnemonic( 'S' );
        
        JMenuItem send = new JMenuItem(CONSTANT.PAGE_SEND);
        scan.setMnemonic( 'D' );
        panel.add(scan);
        panel.add(send);
        panel.add(param);
        
        
        // menu User
        
        JMenu User = new JMenu("User");
        User.setMnemonic( 'U' );
        
        JMenuItem logout = new JMenuItem(CONSTANT.ACTION_LOGOUT);
        logout.setMnemonic( 'L' );
        User.add(logout);
        
        // menu action
        JMenu action = new JMenu("Action");
        action.setMnemonic( 'A' );
        JMenuItem updateParam = new JMenuItem("Update Params");
        updateParam.setActionCommand(CONSTANT.ACTION_UPDATE_PARAM);
        updateParam.setMnemonic( 'U' );
        
        JMenuItem checkAll = new JMenuItem("Check All Items");
        checkAll.setActionCommand(CONSTANT.ACTION_CHECK_ALL);
        
        JMenuItem removeDel = new JMenuItem("Remove Sended, error and Deleted");
        removeDel.setActionCommand(CONSTANT.ACTION_REMOVE_DELETED);
        
        JMenuItem cleanParam = new JMenuItem("Clean Stock List");
        cleanParam.setActionCommand(CONSTANT.ACTION_CLEAN_LIST);
        
        action.add(updateParam);
        action.add(new JSeparator());
        action.add(checkAll);
        
        action.add(new JSeparator());
        action.add(removeDel);
        action.add(cleanParam);
        
        
        // Menu file
        JMenu file = new JMenu("File");
        file.setMnemonic( 'F' );
        
        JMenuItem save = new JMenuItem("Save List");
        save.setActionCommand(CONSTANT.ACTION_SAVE);
        save.setMnemonic( 'S' );
        
        
        JMenu expM = new JMenu("Export");
        expM.setMnemonic( 'F' );
        
        JMenuItem expCSV = new JMenuItem("Export CSV");
        expCSV.setActionCommand(CONSTANT.ACTION_EXPORT_CSV);
        expCSV.setMnemonic( 'C' );
        
        JMenuItem expEXC = new JMenuItem("Export Excel");
        expEXC.setActionCommand(CONSTANT.ACTION_EXPORT_EXCEL);
        expEXC.setMnemonic( 'C' );
        
        expM.add(expCSV);
        expM.add(expEXC);
        
        file.add(save);
        file.add(expM);
        
        
        
        // ajout
        
        this.add(User);
        this.add(panel); 
        
        this.add(action);
        this.add(file);
        // listener
        param.addActionListener(this);
        scan.addActionListener(this);
        logout.addActionListener(this);
        updateParam.addActionListener(this);
        cleanParam.addActionListener(this);
        send.addActionListener(this);
        checkAll.addActionListener(this);
        removeDel.addActionListener(this);
        save.addActionListener(this);
        expCSV.addActionListener(this);
        expEXC.addActionListener(this);
    }

    @Override
    public void eventRecept(iEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    /* ------------------------ Gestion des évènements ------------------- */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == CONSTANT.ACTION_CLEAN_LIST){
            int result = JOptionPane.showConfirmDialog(this.getComponent(),"Are you sure you want clean all scan jobs?", "Clean Scan Table",
               JOptionPane.YES_NO_OPTION,
               JOptionPane.QUESTION_MESSAGE);
            
            if(result == 0)
                controller.setPage(e.getActionCommand());   
        }else{
            controller.setPage(e.getActionCommand());        
        }
        
        
    }

    
}
