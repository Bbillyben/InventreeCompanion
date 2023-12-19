/*
 * Release under GNU General Public Licence v3 https://www.gnu.org/licenses/gpl-3.0.en.html
 */
package controller;

import data.CONSTANT;
import events.NavEvent;
import events.iEvent;
import listeners.NavigationListener;
import model.Model;

/**
 *
 * @author blegendre
 */
public class AboutController extends iController implements NavigationListener {
    
    public AboutController(Model m){
        super(m);
        m.addEventListener(this, NavigationListener.class);
    }
     @Override
    public void eventRecept(iEvent e) {
        switch(e.type){
            case NavEvent.NAVIGATE :
                //System.out.println("Nav Log Cont : "+((NavEvent) e).destPage);
                this.display(((NavEvent) e).destPage.equals(CONSTANT.ACTION_SHOW_ABOUT));
                break;
        }
    }
}
