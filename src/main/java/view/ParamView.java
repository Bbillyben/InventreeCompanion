/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view;

import controller.ParamController;
import data.CONSTANT;
import data.IniStruct;
import events.IniEvent;
import events.iEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import listeners.ListenerI;

/**
 *
 * @author blegendre
 */
public class ParamView extends JPanel implements iView, ActionListener, ListenerI {
    
    private final ParamController cont;
    
    private JSpinner secEAN;
    private JCheckBox cbLoc;
        
    private JCheckBox cbPass;
    private JCheckBox cbAuto;
    private JButton buttonSave;
    
    private ButtonGroup group;
    
    private JCheckBox cbSave;
    
     public ParamView(ParamController lc){
        super(new GridBagLayout());
        cont = lc;
        
        buildView();
    }
    private void buildView(){
        
        
        //  -----------------------  Paramètres de Scan
        JLabel labelURL = new JLabel("Second to wait before scan ending if EAN detected : ");
        SpinnerNumberModel secEANMod =new SpinnerNumberModel(CONSTANT.SCAN_SEC_EAN, 0.4,3.0,0.2);
        secEAN = new JSpinner(secEANMod);
        
        //JLabel labelLOC= new JLabel("Force Stock Location Matching : ");
        //cbLoc = new JCheckBox();
        
        group = new ButtonGroup();
        JRadioButton syncDistant = new JRadioButton("Force Distant Location");
        syncDistant.setActionCommand(CONSTANT.SCAN_FORCE_LOC_DISTANT);
        JRadioButton syncLocal = new JRadioButton("Force Local Location");
        syncLocal.setActionCommand(CONSTANT.SCAN_FORCE_LOC_LOCAL);
        
        group.add(syncDistant);
        group.add(syncLocal);
        
         // conteneur pour les paramètre du scan, scindé en 
        JPanel scanCont = new JPanel(new GridBagLayout());
        GridBagConstraints cstSc = new GridBagConstraints();
        cstSc.anchor = GridBagConstraints.NORTH;
        cstSc.insets = new Insets(0, 0, 10, 10);
        cstSc.gridx = 0;
        cstSc.gridy = 0;
        
        scanCont.add(labelURL, cstSc);
        cstSc.gridx = 1;
        scanCont.add(secEAN, cstSc);
        /*cstSc.gridx = 0;
        cstSc.gridy = 1;
        scanCont.add(labelLOC, cstSc);
        cstSc.gridx = 1;
        scanCont.add(cbLoc, cstSc);*/
        
        cstSc.anchor = GridBagConstraints.WEST;
        cstSc.insets = new Insets(0, 150, 10, 10);
        cstSc.gridx = 0;
        cstSc.gridy = 2;
        scanCont.add(syncDistant, cstSc);
        cstSc.gridy = 3;
        scanCont.add(syncLocal, cstSc);
        
        scanCont.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "SCAN Parameters"));
        
        //  -----------------------  Paramètres APP
        JPanel appCont = new JPanel(new GridBagLayout());
        JLabel saveListL = new JLabel("Automatic Save List on exit : ");
        cbSave = new JCheckBox();
        
        GridBagConstraints saCst = new GridBagConstraints();
        saCst.anchor = GridBagConstraints.NORTH;
        saCst.insets = new Insets(0, 0, 10, 10);
        saCst.gridx = 0;
        saCst.gridy = 0; 
        appCont.add(saveListL, saCst);
        saCst.gridx = 1;
        appCont.add(cbSave, saCst);
        
        appCont.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "APP Parameters"));

        //  -----------------------  Paramètres USER
        JLabel labelSavePass = new JLabel("Save Password : ");
        cbPass = new JCheckBox();
        
        JLabel labelAuto= new JLabel("Auto Login : ");
        cbAuto = new JCheckBox();
        
        
        GridBagConstraints cst = new GridBagConstraints();
        cst.anchor = GridBagConstraints.NORTH;
        cst.insets = new Insets(0, 0, 10, 10);
        cst.gridx = 0;
        cst.gridy = 0; 
        //cst.fill= GridBagConstraints.HORIZONTAL;
        
        JPanel userCont = new JPanel(new GridBagLayout());
        userCont.add(labelSavePass, cst);
        cst.gridx = 1;
        userCont.add(cbPass, cst);
        cst.gridx = 0;
        cst.gridy = 1; 
        userCont.add(labelAuto, cst);
        cst.gridx = 1;
        userCont.add(cbAuto, cst);
        userCont.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "USER Parameters"));
        
        
        // --------------------------------   Buttons
        
        buttonSave = new JButton("SAVE");
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 0, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 0; 
        constraints.fill= GridBagConstraints.HORIZONTAL;
        
        this.add(scanCont, constraints);
        constraints.gridy = 1;
        this.add(appCont,constraints);
        constraints.gridy = 2;
                
        this.add(userCont,constraints);
        
        constraints.anchor = GridBagConstraints.SOUTHEAST;
        constraints.gridy = 3;
        this.add(buttonSave,constraints);
        
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Parameters"));
        
        // listener
        buttonSave.addActionListener(this);
    }
    
    
    
    private void saveParams(){
        IniStruct tmp = new IniStruct();
        tmp.setValue(CONSTANT.PARAM_HEAD,CONSTANT.PARAM_secEAN,String.valueOf(secEAN.getValue()));
        tmp.setValue(CONSTANT.PARAM_HEAD,CONSTANT.PARAM_SAVE_PASS,String.valueOf(cbPass.isSelected()));
        tmp.setValue(CONSTANT.PARAM_HEAD,CONSTANT.PARAM_AUTO_LOG,String.valueOf(cbAuto.isSelected()));
        tmp.setValue(CONSTANT.SCAN_FILE_HEAD,CONSTANT.SCAN_FILE_SAVEONEXIT,String.valueOf(cbSave.isSelected()));
        //tmp.setValue(CONSTANT.PARAM_HEAD,CONSTANT.PARAM_FORCE_LOCATION,String.valueOf(cbLoc.isSelected()));
        tmp.setValue(CONSTANT.PARAM_HEAD,CONSTANT.PARAM_FORCE_LOCATION,group.getSelection().getActionCommand());
        cont.saveParams(tmp);
        
    }
    
    private void updateFromIni(IniStruct is){
        String ean=is.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_secEAN, String.valueOf(CONSTANT.SCAN_SEC_EAN));
        String pass = is.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_SAVE_PASS, "false");
        String auto = is.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_AUTO_LOG, "false");
        String forceLoc= is.getValue(CONSTANT.PARAM_HEAD, CONSTANT.PARAM_FORCE_LOCATION, CONSTANT.SCAN_FORCE_LOC_LOCAL);
        String saveOnExit = is.getValue(CONSTANT.SCAN_FILE_HEAD,CONSTANT.SCAN_FILE_SAVEONEXIT,"true");
        secEAN.setValue(Double.valueOf(ean));
        cbPass.setSelected(Boolean.valueOf(pass));
        cbAuto.setSelected(Boolean.valueOf(auto));
        cbSave.setSelected(Boolean.valueOf(saveOnExit));
        //cbLoc.setSelected(Boolean.valueOf(forceLoc));
        List<AbstractButton> btnGrp = Collections.list(group.getElements());
        for (AbstractButton abb : btnGrp){
             if(abb.getActionCommand().equals(forceLoc)){
                abb.setSelected(true);
                break;
            }
        }
        
    }
    
    //  ================= GESTION DES EVENTS 
    @Override
    public void eventRecept(iEvent e) {
        switch(e.type){
            case IniEvent.INI_LOADED:
                updateFromIni(((IniEvent) e).ini);
                break;
           
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.saveParams();
    }
    
}
