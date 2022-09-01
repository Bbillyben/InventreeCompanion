/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package view;

import Inventree.item.StockItem;
import controller.SendFollowController;
import data.UTILS;
import events.SendEvent;
import events.iEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import listeners.SendListener;
import view.element.SendOutputDialog;

/**
 *
 * @author blegendre
 */
public class SendFollowView implements iView, SendListener, WindowListener {

    SendFollowController controller;
    private SendOutputDialog dialog;
    private JFrame mainFrame;
    
    public SendFollowView(SendFollowController ctrl, JFrame f){
        controller = ctrl;
        mainFrame = f;
        buildView();
    }
    private void buildView(){
        dialog = new SendOutputDialog(mainFrame, "Current Sending process", false);
        
    }
    @Override
    public void setVisible(boolean state) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void startSending(){
        dialog.allowClosing(false);
        dialog.init();
        dialog.setVisible(true);
        dialog.addWindowListener(this);
        dialog.appendLine("Start Sending process .... ");
        mainFrame.setEnabled(false);
    }
    public void stopSending(){
        dialog.allowClosing(true);
 
    }
    public void addSending(StockItem si){
        String str=UTILS.getSIInfo(si);
        dialog.appendLine(str);
        
    }
    @Override
    public void eventRecept(iEvent e) {
        switch (e.type) {
            case SendEvent.START_SENDING:
                startSending();
                break;
            case SendEvent.END_SENDING:
                stopSending();
                break;
            case SendEvent.ITEM_SENDING:
                addSending(((SendEvent) e).item);
                break;    
        }
    }

    

    @Override
    public void windowClosed(WindowEvent e) {
       controller.stopSendingCall();
        mainFrame.setEnabled(true);
        dialog.removeWindowListener(this);
    }

    
    
    // unused event listening
    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {}
    
}
