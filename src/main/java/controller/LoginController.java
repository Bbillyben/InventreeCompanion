/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import data.CONSTANT;
import events.NavEvent;
import events.iEvent;
import listeners.ConnectionListener;
import listeners.ListenerI;
import listeners.NavigationListener;
import model.Model;
import view.LoginView;

/**
 *
 * @author blegendre
 */
public class LoginController extends iController implements ConnectionListener, NavigationListener{

    public LoginController(Model m){
        super(m);
        m.addEventListener(this, NavigationListener.class);
    }
    
    
    public void setView(LoginView vi){
        super.setView(vi);
        model.addEventListener(view, ConnectionListener.class);
        model.addEventListener(view, ListenerI.class);
        
    }
    public void login(String url, String user, String pass, String forceHTTP){
        model.registerLogin(url, user, pass, forceHTTP);
    }
    
    public void close() {
    }    
    
    
    @Override
    public void eventRecept(iEvent e) {
        
        switch(e.type){
            case NavEvent.NAVIGATE :
                //System.out.println("Nav Log Cont : "+((NavEvent) e).destPage);
                this.display(((NavEvent) e).destPage.equals(CONSTANT.PAGE_LOGIN) || ((NavEvent) e).destPage.equals(CONSTANT.ACTION_LOGOUT));
                if (((NavEvent) e).destPage.equals(CONSTANT.ACTION_LOGOUT))
                    ((LoginView) view).logout();
                break;
        }
    }
    
}
