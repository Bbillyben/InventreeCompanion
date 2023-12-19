/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package ben.inventreecompanion;

import controller.AboutController;
import controller.CreatePartController;
import controller.ExportController;
import controller.InfoController;
import controller.LoginController;
import controller.MenuController;
import controller.ParamController;
import controller.ScanController;
import controller.SendController;
import controller.SendFollowController;
import controller.SoundController;
import data.CONSTANT;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import model.Model;
import view.AboutDialogView;
import view.CreatePartView;
import view.ExportView;
import view.LoginView;
import view.MenuView;
import view.ParamView;
import view.ScanView;
import view.SendFollowView;
import view.SendView;
import view.SoundView;
import view.StatusView;

/**
 *
 * @author blegendre
 */
public class InventreeCompanion {
    
    static Model model;
    static JPanel midPanel;
    
    public static void main(String[] args) {
        
       model = new Model();
        
       // la page des parametres
        ParamController pmC = new ParamController(model);
        ParamView pmV = new ParamView(pmC);
        pmC.setView(pmV);
                
        // le menu
        MenuController bcM = new MenuController(model);
        MenuView mv =new MenuView(bcM);
        bcM.setView(mv);
        
        // page de login
        LoginController lc = new LoginController(model);
        LoginView lv = new LoginView(lc);
        lc.setView(lv);
        
        // page de scan
        ScanController scC =new ScanController(model);
        ScanView scV = new ScanView(scC);
        scC.setView(scV);
        
        // page de send
        SendController seC = new SendController(model);
        SendView seV = new SendView(seC);
        seC.setView(seV);
        
         // le panel Statys
        InfoController bcC =new InfoController(model);
        StatusView sV = new StatusView(bcC);
        bcC.setView(sV);
        
        // sound view
        SoundController scc = new SoundController(model);
        SoundView sv = new SoundView(scc);
        scc.setView(sv);
        
        // le panel conteneur des view 
        midPanel = new JPanel(new CardLayout());//new BorderLayout(1,1));
        midPanel.add(lv);// la vue login
        midPanel.add(scV);// la view scan
        midPanel.add(pmV);// la view parameter
        midPanel.add(seV);// la view send
        
        
        // Main Frame
        JFrame frame = new JFrame("Inventree Companion - Scan Utils");
        frame.setSize(1200, 500);
        
        // pour la fenetre de suivi des envoi
        SendFollowController sfC =new SendFollowController(model);
        SendFollowView sfV =new SendFollowView(sfC, frame);
        sfC.setView(sfV);
        
        
        // pour la creation des parts
        CreatePartController cpC = new CreatePartController(model);
        CreatePartView cpV = new CreatePartView(cpC, frame);
        cpC.setView(cpV);
        
        // pour l'export
        ExportController exC = new ExportController(model);
        ExportView exV = new ExportView(exC, frame);
        exC.setView(exV);
        
        // For about Modal
        AboutController abC = new AboutController(model);
        AboutDialogView about = new AboutDialogView(frame);
        abC.setView(about);
        
        // ajout des elements dans la frame
        frame.add(mv, BorderLayout.PAGE_START);
        frame.add(midPanel);//, BorderLayout.LINE_START);
        frame.add(sV,BorderLayout.PAGE_END);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        
        
        
        model.setMainFrame(frame);
        
        // ajout du save avant exit
         Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("In shutdown hook");
                    boolean saveOnExit = Boolean.valueOf(model.getIniValue(CONSTANT.SCAN_FILE_HEAD, CONSTANT.SCAN_FILE_SAVEONEXIT, "true"));
                    if(saveOnExit)
                        model.saveScanList();
                }
            }, "Shutdown-thread"));
        
        //model.navigate(CONSTANT.PAGE_SEND);
        model.navigate(CONSTANT.PAGE_NULL);
        model.initialize();
    
        
        
    }
}
