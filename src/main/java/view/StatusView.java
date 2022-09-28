/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.InfoController;
import events.ConnectionEvent;
import events.InfoEvent;
import events.iEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import listeners.ConnectionListener;
import listeners.InfoListener;

/**
 *
 * @author blegendre
 */
public class StatusView extends JPanel implements iView, ConnectionListener, InfoListener {
    
    InfoController controller;
    private JLabel statusTxt;
    
    public StatusView(InfoController ctrl){
        super(new BorderLayout());
        controller = ctrl;
        buildView();
    }
    private void buildView(){
        statusTxt = new JLabel();
        Font newLabelFont=new Font(statusTxt.getFont().getName(),Font.ITALIC,10);
        statusTxt.setFont(newLabelFont);
        
        this.add(statusTxt, BorderLayout.LINE_START);
        this.setBorder(BorderFactory.createEtchedBorder());
        statusTxt.setText("status bar");
        
    }
    @Override
    public void eventRecept(iEvent e) {
        
        switch(e.type){
            case ConnectionEvent.CONNECTION_ERROR:
                statusTxt.setText("<html><font style='color:red'>Error</font> connection to server :" +((ConnectionEvent) e).reason +"</html>");
                break;
            case ConnectionEvent.CONNECTION_SUCCESS :
                statusTxt.setText("<html>connection to server <font style='color:green'>success</font></html>");
                break;
            case InfoEvent.INI_FILE_ERROR:
                 statusTxt.setText("<html>Parameters not found, check  " + ((InfoEvent) e).info + "</html>");
                break;
            case InfoEvent.INFO_NORM:
                 statusTxt.setText("<html>"+((InfoEvent) e).info +"</html>");
                break;
            case InfoEvent.CONNECTION_ERROR:
                 statusTxt.setText("<html><font style='color:red'>"+((InfoEvent) e).info+"</font></html>" );
                break;
            case InfoEvent.SAVE_SUCESS:
                statusTxt.setText("<html>Item List <font style='color:green'>saved</font></html>" );
                break;
            case InfoEvent.SAVE_ERROR:
                statusTxt.setText("<html><font style='color:red'>Error </font>saving Item list</html>" );
                break;
            case InfoEvent.GENERIC_ERROR:
                statusTxt.setText("<html><font style='color:red'>"+((InfoEvent) e).info +"</font></html>");
            case InfoEvent.GENERIC_DISPLAY_ERROR:
                JOptionPane.showMessageDialog(this, "Error : \n    - "+ ((InfoEvent) e).info );
                break;
            case InfoEvent.GENERIC_OK:
                statusTxt.setText("<html><font style='color:green'>"+((InfoEvent) e).info +"</font></html>");
                break; 
            
        }
    }
    
}
