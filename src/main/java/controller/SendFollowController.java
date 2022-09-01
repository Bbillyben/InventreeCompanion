/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import events.iEvent;
import listeners.ConnectionListener;
import listeners.InfoListener;
import listeners.NavigationListener;
import listeners.SendListener;
import model.Model;
import view.iView;

/**
 *
 * @author blegendre
 */
public class SendFollowController extends iController {
    
    public SendFollowController(Model md){
        super(md);
       
    }
    
    public void stopSendingCall(){
        model.stopSending();
    }
    @Override
    public void setView(iView vi){
        super.setView(vi);
        model.addEventListener(view, SendListener.class);
    }

}
