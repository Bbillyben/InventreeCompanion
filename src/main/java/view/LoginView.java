/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view;

import controller.LoginController;
import data.CONSTANT;
import data.IniStruct;
import data.UTILS;
import events.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import listeners.ConnectionListener;
import listeners.ListenerI;

/**
 *
 * @author blegendre
 */
public class LoginView extends JPanel implements iView, ConnectionListener, ActionListener, ListenerI, KeyListener{

    private final LoginController cont;
    private JTextField serveurURL;
    private JTextField userName;
    private JTextField userPass;
    private JCheckBox forceHTTP;
    private JButton buttonSave;
    
    
    public LoginView(LoginController lc){
        super(new GridBagLayout());
        cont = lc;
        
        buildView();
    }
     private void buildView(){
         JLabel labelURL = new JLabel("Server URL :");
         serveurURL= new JTextField(30);
         JLabel labelUSER = new JLabel("User Name :");
         userName= new JTextField(30);
         JLabel labelPass = new JLabel("Password :");
         userPass= new JPasswordField(30);
         JLabel forceLabel = new JLabel("Force Insecure connection (http) :");
         forceHTTP = new JCheckBox();
         
         buttonSave = new JButton("LOGIN");
         
         
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.insets = new Insets(0, 0, 10, 10);
        
        constraints.gridx = 0;
        constraints.gridy = 0; 
        this.add(labelURL, constraints);
        constraints.gridx = 1;
        this.add(serveurURL, constraints);
        
        constraints.insets = new Insets(50, 0, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(labelUSER, constraints);
        constraints.gridx = 1;
        this.add(userName, constraints);
        constraints.insets = new Insets(0, 0, 10, 10);
        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(labelPass, constraints);
        constraints.gridx = 1;
        this.add(userPass, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(forceLabel, constraints);
        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        this.add(forceHTTP, constraints);
        
        
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(50, 50, 10, 10);
        this.add(buttonSave, constraints);
         this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "LOGIN"));
         
         // listener 
        buttonSave.addActionListener(this);
        serveurURL.addKeyListener(this);
        userName.addKeyListener(this);
        userPass.addKeyListener(this);
     }
    
    private void updateFromIni(IniStruct is){
        serveurURL.setText(is.getValue(CONSTANT.SERVEUR_PARAM_HEAD, CONSTANT.SERVEUR_PARAM_URL));
        userName.setText(is.getValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_NAME));
        String pass = is.getValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_PASS, true);
        
        userPass.setText("");
        if(pass != null)
            userPass.setText(pass);
        
        String forceInsec= is.getValue(CONSTANT.USER_PARAM_HEAD, CONSTANT.USER_PARAM_FORCEINSECURE, "false");
        forceHTTP.setSelected(Boolean.valueOf(forceInsec));

       
    }
    
    public void logout(){
        userPass.setText("");
    }
    
    
    // ===================================== GESTION DU LOGIN
    protected void registerLogin(){
        String url = serveurURL.getText();
        String user = userName.getText();
        String pass = userPass.getText();
        String force = String.valueOf(forceHTTP.isSelected());
        if(UTILS.isNullOrEmpty(url) || UTILS.isNullOrEmpty(user) ||UTILS.isNullOrEmpty(pass) ){
            return;
        }
        cont.login(url, user, pass, force);
    }
    
    
    // ===================================== GESTION DES EVENT
    @Override
    public void eventRecept(iEvent e) {
        //System.out.println("loginView event :"+e);
        switch(e.type){
            case ConnectionEvent.CONNECTION_ERROR:
                JOptionPane.showMessageDialog(this, "Connection Error : \n    - "+ ((ConnectionEvent) e).reason );
                break;
            case ConnectionEvent.CONNECTION_SUCCESS:
                break;
            case IniEvent.INI_LOADED:
                updateFromIni(((IniEvent) e).ini);
                break;
           
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       registerLogin();
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
           registerLogin();
         } 
    }
    
    
    ////////// UNUSED event handler
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
}
